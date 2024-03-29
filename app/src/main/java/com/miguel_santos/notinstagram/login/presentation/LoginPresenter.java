package com.miguel_santos.notinstagram.login.presentation;

import com.google.firebase.auth.FirebaseUser;
import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.presenter.Presenter;
import com.miguel_santos.notinstagram.common.util.Strings;
import com.miguel_santos.notinstagram.login.datasource.LoginDataSource;

public class LoginPresenter implements Presenter<FirebaseUser> {

    private final LoginView view;
    private final LoginDataSource dataSource;

    public LoginPresenter(LoginView view, LoginDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    public void login(String email, String password) {
        if (!Strings.emailValid(email)) {
            view.onFailureForm(view.getContext().getString(R.string.invalid_email), null);
            return;
        }
        view.showProgressBar();
        dataSource.login(email, password, this);
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        view.onUserLogged();
    }

    @Override
    public void onError(String message) {
        view.onFailureForm(null, message);
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }

}
