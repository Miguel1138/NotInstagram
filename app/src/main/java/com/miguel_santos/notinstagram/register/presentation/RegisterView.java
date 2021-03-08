package com.miguel_santos.notinstagram.register.presentation;

import android.content.Context;

import com.miguel_santos.notinstagram.common.view.View;

public interface RegisterView {

    void showNextView(RegisterSteps step);

    void onUserCreated();

    interface EmailView {
        Context getContext();

        void onFailureForm(String emailError);
    }

    interface NamePasswordView extends View {
        Context getContext();

        void onFailureForm(String emailError, String passwordError);

        void onFailureCreateUser(String message);
    }


    public interface WelcomeView {
    }

    public interface PhotoView {
    }

}
