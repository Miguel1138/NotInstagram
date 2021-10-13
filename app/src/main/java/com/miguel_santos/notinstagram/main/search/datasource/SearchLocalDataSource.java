package com.miguel_santos.notinstagram.main.search.datasource;

import com.miguel_santos.notinstagram.common.model.Database;
import com.miguel_santos.notinstagram.common.model.User;
import com.miguel_santos.notinstagram.common.presenter.Presenter;

import java.util.List;

public class SearchLocalDataSource implements SearchDataSource {

    @Override
    public void findUsers(String query, Presenter<List<User>> presenter) {
        Database db = Database.getInstance();
        db.findUsers(db.getUser().getUUID(), query)
                .addOnSuccessListener(presenter::onSuccess)
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }

}
