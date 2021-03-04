package com.miguel_santos.notinstagram.login.datasource;

import com.miguel_santos.notinstagram.common.model.Database;
import com.miguel_santos.notinstagram.common.model.UserAuth;
import com.miguel_santos.notinstagram.common.presenter.Presenter;

public class LoginLocalDataSource implements LoginDataSource {

    @Override
    public void login(String email, String password, Presenter presenter) {
        Database.getInstance().login(email, password)
                .addOnSuccessListener(
                        (Database.OnSuccessListener<UserAuth>) response -> presenter.onSuccess(response))
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(() -> presenter.onCompleted());
    }

}
