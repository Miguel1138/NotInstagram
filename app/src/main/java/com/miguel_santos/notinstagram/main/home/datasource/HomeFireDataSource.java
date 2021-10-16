package com.miguel_santos.notinstagram.main.home.datasource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.miguel_santos.notinstagram.common.model.Feed;
import com.miguel_santos.notinstagram.common.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class HomeFireDataSource implements HomeDataSource {

    @Override
    public void findFeed(Presenter<List<Feed>> presenter) {
        String uid = FirebaseAuth.getInstance().getUid();

        // Get the reference of the collections of posts in the feed
        FirebaseFirestore.getInstance().collection("feed")
                .document(uid)
                .collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Feed> feedList = new ArrayList<>();
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                    // Convert every doc in a feed object and add to feedList
                    for (DocumentSnapshot doc : documents) {
                        Feed feed = doc.toObject(Feed.class);
                        feedList.add(feed);
                    }
                    presenter.onSuccess(feedList);
                })
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(task -> presenter.onComplete());
    }

}
