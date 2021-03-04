package com.miguel_santos.notinstagram.register.presentation;

import android.content.Context;

public interface RegisterView {

    interface EmailView {
        Context getContext();

        void onFailureForm(String emailError);

        void showNextView();
    }

}
