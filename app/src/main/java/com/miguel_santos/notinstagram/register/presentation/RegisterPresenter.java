package com.miguel_santos.notinstagram.register.presentation;

import android.net.Uri;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.model.UserAuth;
import com.miguel_santos.notinstagram.common.presenter.Presenter;
import com.miguel_santos.notinstagram.common.util.Strings;
import com.miguel_santos.notinstagram.register.datasource.RegisterLocalDataSource;

public class RegisterPresenter implements Presenter<UserAuth> {

    private RegisterView registerView;
    private RegisterView.EmailView emailView;
    private RegisterView.NamePasswordView namePasswordView;
    private RegisterView.WelcomeView welcomeView;
    private RegisterView.PhotoView photoView;

    private final RegisterLocalDataSource dataSource;

    private String email;
    private String name;
    private Uri uri;

    public RegisterPresenter(RegisterLocalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setRegisterView(RegisterView registerView) {
        this.registerView = registerView;
    }

    public void setEmailView(RegisterView.EmailView emailView) {
        this.emailView = emailView;
    }

    public void setNamePasswordView(RegisterView.NamePasswordView namePasswordView) {
        this.namePasswordView = namePasswordView;
    }

    public void setWelcomeView(RegisterView.WelcomeView welcomeView) {
        this.welcomeView = welcomeView;
    }

    public void setPhotoView(RegisterView.PhotoView photoView) {
        this.photoView = photoView;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
        if (photoView != null) {
            photoView.onImageCropped(uri);
            photoView.showProgressBar();

            dataSource.addPhoto(uri, new UpdatePhotoCallback());
        }
    }

    public void setEmail(String email) {
        if (!Strings.emailValid(email)) {
            emailView.onFailureForm(emailView.getContext().getString(R.string.invalid_email));
            return;
        }
        this.email = email;
        registerView.showNextView(RegisterSteps.NAME_PASSWORD);
    }

    public void setNameAndPassword(String name, String password, String confirmPassword) {
        if (!confirmPassword.equals(password)) {
            namePasswordView.onFailureForm(null, namePasswordView.getContext().getString(R.string.password_not_equal));
            return;
        }
        // Guardando os dados para futura inser????o no banco de dados
        this.name = name;

        namePasswordView.showProgressBar();
        dataSource.createUser(name, email, password, this);
    }

    public String getName() {
        return name;
    }

    @Override
    public void onSuccess(UserAuth response) {
        registerView.showNextView(RegisterSteps.WELCOME);
    }

    @Override
    public void onError(String message) {
        namePasswordView.onFailureCreateUser(message);
    }

    @Override
    public void onComplete() {
        namePasswordView.hideProgressBar();
    }

    public void showPhotoView() {
        registerView.showNextView(RegisterSteps.PHOTO);
    }

    public void showCamera() {
        registerView.showCamera();
    }

    public void showGallery() {
        registerView.showGallery();
    }

    public void jumpRegistration() {
        registerView.onUserCreated();
    }


    private class UpdatePhotoCallback implements Presenter<Boolean> {

        @Override
        public void onSuccess(Boolean response) {
            registerView.onUserCreated();
        }

        @Override
        public void onError(String message) {

        }

        @Override
        public void onComplete() {

        }

    }

}
