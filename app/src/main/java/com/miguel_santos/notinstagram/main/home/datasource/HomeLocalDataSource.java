package com.miguel_santos.notinstagram.main.home.datasource;

import com.miguel_santos.notinstagram.common.model.Database;
import com.miguel_santos.notinstagram.common.model.Feed;
import com.miguel_santos.notinstagram.common.presenter.Presenter;

import java.util.List;

public class HomeLocalDataSource implements HomeDataSource {

    @Override
    public void findFeed(Presenter<List<Feed>> presenter) {
        Database db = Database.getInstance();
        db.findFeed(db.getUser().getUUID())
                .addOnSuccessListener(presenter::onSuccess)
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }

}
