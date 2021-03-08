package com.miguel_santos.notinstagram.register.datasource;

import com.miguel_santos.notinstagram.common.presenter.Presenter;

public interface RegisterDataSource {

    void createUser(String name, String email, String password, Presenter presenter);

}
