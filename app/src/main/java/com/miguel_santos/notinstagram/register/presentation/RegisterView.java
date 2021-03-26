package com.miguel_santos.notinstagram.register.presentation;

import android.content.Context;
import android.net.Uri;

import com.miguel_santos.notinstagram.common.view.View;

public interface RegisterView {

    void showNextView(RegisterSteps step);

    void onUserCreated();

    void showCamera();

    void showGallery();

    interface EmailView {
        Context getContext();

        void onFailureForm(String emailError);
    }

    interface NamePasswordView extends View {
        Context getContext();

        void onFailureForm(String emailError, String passwordError);

        void onFailureCreateUser(String message);
    }


    interface WelcomeView {
    }

    interface PhotoView extends View{

        void onImageCropped(Uri uri);
    }

}
