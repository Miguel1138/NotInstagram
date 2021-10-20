package com.miguel_santos.notinstagram.main.camera.datasource;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.miguel_santos.notinstagram.common.model.Feed;
import com.miguel_santos.notinstagram.common.model.Post;
import com.miguel_santos.notinstagram.common.model.User;
import com.miguel_santos.notinstagram.common.presenter.Presenter;

import java.io.File;
import java.util.List;

public class AddFireDataSource implements AddDataSource {

    @Override
    public void savePost(Uri uri, String caption, Presenter presenter) {
        String host = uri.getHost();
        // No caso de ser galeria
        if (host == null) uri = Uri.fromFile(new File(uri.toString()));

        String uid = FirebaseAuth.getInstance().getUid();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imgRef = storageRef.child("images/")
                .child(uid)
                .child(uri.getLastPathSegment());

        // Inserting the new image in the imageReference in the Firebase Storage and sharing to the followers feed.
        imgRef.putFile(uri)
                .addOnSuccessListener(taskSnapshot ->
                        imgRef.getDownloadUrl()
                                .addOnSuccessListener(uriResponse -> {
                                    Post post = new Post();
                                    post.setPhotoUrl(uriResponse.toString());
                                    post.setCaption(caption);
                                    post.setTimestamp(System.currentTimeMillis());

                                    DocumentReference postRef = FirebaseFirestore.getInstance()
                                            .collection("posts")
                                            .document(uid)
                                            .collection("posts")
                                            .document();

                                    postRef.set(post)
                                            .addOnSuccessListener(unused -> {
                                                // Set the proper publisher in the feed.
                                                DocumentReference userRef = FirebaseFirestore.getInstance()
                                                        .collection("user")
                                                        .document(uid);

                                                userRef.get().addOnCompleteListener(t -> {
                                                    if (t.isSuccessful()) {
                                                        User me = t.getResult().toObject(User.class);

                                                        // passing this post to the followers.
                                                        FirebaseFirestore.getInstance()
                                                                .collection("followers")
                                                                .get()
                                                                .addOnCompleteListener(task -> {
                                                                    if (!task.isSuccessful())
                                                                        return;

                                                                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                                                    for (DocumentSnapshot doc : documents) {
                                                                        User user = doc.toObject(User.class);

                                                                        Feed feed = new Feed();
                                                                        feed.setPublisher(me);
                                                                        feed.setUuid(postRef.getId());
                                                                        feed.setPhotoUrl(post.getPhotoUrl());
                                                                        feed.setTimestamp(post.getTimestamp());
                                                                        feed.setCaption(post.getCaption());

                                                                        // Add post of the user in the feed.
                                                                        FirebaseFirestore.getInstance()
                                                                                .collection("feed")
                                                                                .document(user.getUuid())
                                                                                .collection("posts")
                                                                                .document(postRef.getId())
                                                                                .set(feed);
                                                                    }
                                                                });
                                                    }
                                                });
                                            })
                                            .addOnFailureListener(e -> presenter.onError(e.getMessage()));

                                    String id = postRef.getId();
                                    Feed feed = new Feed();
                                    feed.setCaption(post.getCaption());
                                    feed.setPhotoUrl(post.getPhotoUrl());
                                    feed.setTimestamp(post.getTimestamp());
                                    feed.setUuid(id);

                                    FirebaseFirestore.getInstance()
                                            .collection("user")
                                            .document(uid)
                                            .get()
                                            .addOnCompleteListener(task -> {
                                                User user = task.getResult().toObject(User.class);
                                                feed.setPublisher(user);

                                                FirebaseFirestore.getInstance()
                                                        .collection("feed")
                                                        .document(uid)
                                                        .collection("posts")
                                                        .document(id)
                                                        .set(feed);
                                            });

                                    presenter.onSuccess(null);
                                    presenter.onComplete();
                                })
                                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                )
                .addOnFailureListener(e -> presenter.onError(e.getMessage()));

    }

}
