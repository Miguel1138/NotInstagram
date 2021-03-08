package com.miguel_santos.notinstagram.register.presentation;

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

    private String email;
    private String name;
    private String password;
    private final RegisterLocalDataSource dataSource;

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

    public String getName() {
        return name;
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
        // Guardando os dados para futura inserção no banco de dados
        this.name = name;
        this.password = password;

        namePasswordView.showProgressBar();
        dataSource.createUser(this.name, this.email, this.password, this);
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

    public void jumpRegistration() {
        registerView.onUserCreated();
    }

}
