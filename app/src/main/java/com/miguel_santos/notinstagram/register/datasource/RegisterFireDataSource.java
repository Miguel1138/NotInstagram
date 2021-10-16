package com.miguel_santos.notinstagram.register.datasource;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.miguel_santos.notinstagram.common.model.User;
import com.miguel_santos.notinstagram.common.presenter.Presenter;

public class RegisterFireDataSource implements RegisterDataSource {

    @Override
    public void createUser(String name, String email, String password, Presenter presenter) {
        // Adding to the firebaseAuth
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    User user = new User();
                    user.setEmail(email);
                    user.setName(name);
                    user.setUuid(authResult.getUser().getUid());

                    // Adding the user to the FirebaseDatabase in a collection of user.
                    FirebaseFirestore.getInstance().collection("user")
                            .document(authResult.getUser().getUid())
                            .set(user)
                            .addOnSuccessListener(unused -> presenter.onSuccess(authResult.getUser()))
                            .addOnCompleteListener(task -> presenter.onComplete());
                })
                .addOnFailureListener(e -> {
                    presenter.onError(e.getMessage());
                    presenter.onComplete();
                });
    }

    @Override
    public void addPhoto(Uri uri, Presenter presenter) {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uri == null || uid == null || uri.getLastPathSegment() == null) return;

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        // Setting the reference where the image will be saved.
        StorageReference imgRef = storageRef.child("images/")
                .child(FirebaseAuth.getInstance().getUid())
                .child(uri.getLastPathSegment());
        // Inserting the image
        imgRef.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    imgRef.getDownloadUrl()
                            .addOnSuccessListener(uriResponse -> {
                                DocumentReference userDoc = FirebaseFirestore.getInstance().collection("user").document(uid);
                                userDoc.get()
                                        .addOnSuccessListener(documentSnapshot -> {
                                            User user = documentSnapshot.toObject(User.class);
                                            user.setPhotoUrl(uriResponse.toString());

                                            userDoc.set(user)
                                                    .addOnSuccessListener(unused -> {
                                                        presenter.onSuccess(true);
                                                        presenter.onComplete();
                                                    });
                                        });
                            });
                });
    }

}
