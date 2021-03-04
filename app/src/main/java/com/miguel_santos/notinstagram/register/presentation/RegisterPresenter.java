package com.miguel_santos.notinstagram.register.presentation;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.util.Strings;

public class RegisterPresenter {

    private RegisterView.EmailView emailView;
    private String email;

    public void setEmailView(RegisterView.EmailView emailView) {
        this.emailView = emailView;
    }

    public void setEmail(String email) {
        if (!Strings.emailValid(email)) {
            emailView.onFailureForm(emailView.getContext().getString(R.string.invalid_email));
            return;
        }
        this.email = email;
    }
    // TODO: 04/03/2021 criar os setters de senha e usuário


}
