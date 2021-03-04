package com.miguel_santos.notinstagram.login.presentation;

import com.miguel_santos.notinstagram.common.view.View;

public interface LoginView extends View {

    void onFailureForm(String emailError, String passwordError);

    void onUserLogged();
}
