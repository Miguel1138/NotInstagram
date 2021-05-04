package com.miguel_santos.notinstagram.main.camera.datasource;

import android.net.Uri;

import com.miguel_santos.notinstagram.common.model.Database;
import com.miguel_santos.notinstagram.common.presenter.Presenter;

public class AddLocalDataSource implements AddDataSource {

    @Override
    public void savePost(Uri uri, String caption, Presenter presenter) {
        Database db = Database.getInstance();
        db.createPost(db.getUser().getUUID(), uri, caption)
                .addOnSuccessListener((Database.OnSuccessListener<Void>) presenter::onSuccess)
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }

}
