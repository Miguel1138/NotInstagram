package com.miguel_santos.notinstagram.login.presentation;

public interface LoginView {

    void onFailureForm(String emailError, String passwordError);

    void onUserLogged();
}
