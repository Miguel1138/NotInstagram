package com.miguel_santos.notinstagram.register.datasource;

import com.miguel_santos.notinstagram.common.model.Database;
import com.miguel_santos.notinstagram.common.model.UserAuth;
import com.miguel_santos.notinstagram.common.presenter.Presenter;

public class RegisterLocalDataSource implements RegisterDataSource {

    @Override
    public void createUser(String name, String email, String password, Presenter presenter) {
        Database.getInstance().createUser(name, email, password)
                .addOnSuccessListener((Database.OnSuccessListener<UserAuth>) presenter::onSuccess)
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }

}
