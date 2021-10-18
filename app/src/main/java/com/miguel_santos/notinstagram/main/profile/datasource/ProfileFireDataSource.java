package com.miguel_santos.notinstagram.main.profile.datasource;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.miguel_santos.notinstagram.common.model.Feed;
import com.miguel_santos.notinstagram.common.model.Post;
import com.miguel_santos.notinstagram.common.model.User;
import com.miguel_santos.notinstagram.common.model.UserProfile;
import com.miguel_santos.notinstagram.common.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class ProfileFireDataSource implements ProfileDataSource {

    @Override
    public void findUser(String uid, Presenter<UserProfile> presenter) {
        FirebaseFirestore.getInstance()
                .collection("user")
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);

                    // Get all the user's posts.
                    FirebaseFirestore.getInstance()
                            .collection("posts")
                            .document(uid)
                            .collection("posts")
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                List<Post> posts = new ArrayList<>();
                                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot doc : documents) {
                                    Post post = doc.toObject(Post.class);
                                    posts.add(post);
                                }

                                // Check for followers and if the user is following them back.
                                FirebaseFirestore.getInstance()
                                        .collection("followers")
                                        .document(uid)
                                        .collection("followers")
                                        .document(FirebaseAuth.getInstance().getUid())
                                        .get()
                                        .addOnSuccessListener(documentSnapshot1 -> {
                                            boolean isFollowing = documentSnapshot1.exists();

                                            presenter.onSuccess(new UserProfile(user, posts, isFollowing));
                                            presenter.onComplete();
                                        })
                                        .addOnFailureListener(e -> presenter.onError(e.getMessage()));
                            })
                            .addOnFailureListener(e -> presenter.onError(e.getMessage()));
                })
                .addOnFailureListener(e -> presenter.onError(e.getMessage()));
    }

    @Override
    public void followOrUnfollow(boolean follow, String uid) {
        Task<DocumentSnapshot> firestore = FirebaseFirestore.getInstance()
                .collection("user")
                .document(uid)
                .get();

        if (follow) {
            firestore.addOnCompleteListener(task -> {
                User user = task.getResult().toObject(User.class);

                FirebaseFirestore.getInstance()
                        .collection("user")
                        .document(FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(task1 -> {
                            User me = task1.getResult().toObject(User.class);

                            FirebaseFirestore.getInstance()
                                    .collection("followers")
                                    .document(uid)
                                    .collection("followers")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .set(me);
                        });

                // pegando referencia dos posts do usuário seguido.
                FirebaseFirestore.getInstance()
                        .collection("posts")
                        .document(uid)
                        .collection("posts")
                        .orderBy("timestamp", Query.Direction.DESCENDING)
                        .limit(10)
                        .get()
                        .addOnCompleteListener(taskRes -> {
                            if (taskRes.isSuccessful()) {
                                // adicionando posts ao feed.
                                List<DocumentSnapshot> documents = taskRes.getResult().getDocuments();
                                for (DocumentSnapshot doc : documents) {
                                    Post post = doc.toObject(Post.class);

                                    Feed feed = new Feed();
                                    feed.setUuid(doc.getId());
                                    feed.setPhotoUrl(post.getPhotoUrl());
                                    feed.setCaption(post.getCaption());
                                    feed.setTimestamp(post.getTimestamp());
                                    feed.setPublisher(user);

                                    FirebaseFirestore.getInstance()
                                            .collection("feed")
                                            .document(FirebaseAuth.getInstance().getUid())
                                            .collection("posts")
                                            .document(doc.getId())
                                            .set(feed);
                                }
                            }
                        });
            });
        } else { // Unfollow
            firestore.addOnCompleteListener(task -> {
                User user = task.getResult().toObject(User.class);

                FirebaseFirestore.getInstance()
                        .collection("followers")
                        .document(uid)
                        .collection("followers")
                        .document(FirebaseAuth.getInstance().getUid())
                        .delete();

                FirebaseFirestore.getInstance()
                        .collection("feed")
                        .document(FirebaseAuth.getInstance().getUid())
                        .collection("posts")
                        .whereEqualTo("publisher.uuid", uid)
                        .get()
                        .addOnCompleteListener(taskRes -> {
                            // removendo posts
                            if (taskRes.isSuccessful()) {
                                List<DocumentSnapshot> documents = taskRes.getResult().getDocuments();
                                for (DocumentSnapshot doc : documents) {
                                    DocumentReference reference = doc.getReference();
                                    reference.delete();
                                }
                            }
                        });
            });
        }
    }

}
