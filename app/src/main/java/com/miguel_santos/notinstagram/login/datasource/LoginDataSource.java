package com.miguel_santos.notinstagram.login.datasource;

import com.miguel_santos.notinstagram.common.presenter.Presenter;

public interface LoginDataSource {

    void login(String email, String password, Presenter presenter);

}
