package com.miguel_santos.notinstagram.login.presentation;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.components.LoadingButton;
import com.miguel_santos.notinstagram.common.model.Database;
import com.miguel_santos.notinstagram.common.model.UserAuth;
import com.miguel_santos.notinstagram.common.view.AbstractActivity;
import com.miguel_santos.notinstagram.login.datasource.LoginDataSource;
import com.miguel_santos.notinstagram.login.datasource.LoginLocalDataSource;
import com.miguel_santos.notinstagram.main.presentation.MainActivity;
import com.miguel_santos.notinstagram.register.presentation.RegisterActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends AbstractActivity implements LoginView {

    @BindView(R.id.login_edt_email)
    EditText edtEmail;
    @BindView(R.id.login_edt_password)
    EditText edtPassword;
    @BindView(R.id.login_input_edt_email)
    TextInputLayout textInputEmail;
    @BindView(R.id.login_input_edt_password)
    TextInputLayout textInputPassword;
    @BindView(R.id.login_btn_enter)
    LoadingButton buttonEnter;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDark(true);

        UserAuth user = Database.getInstance().getUser();
        if (user != null)
            onUserLogged();
    }

    @Override
    public void showProgressBar() {
        buttonEnter.showProgress(true);
    }

    @Override
    public void hideProgressBar() {
        buttonEnter.showProgress(false);
    }

    @Override
    public void onFailureForm(String email, String password) {
        if (email != null) {
            textInputEmail.setError(email);
            textInputEmail.setErrorIconDrawable(null);
            edtEmail.setBackground(findDrawable(R.drawable.edit_text_background_error));
        }
        if (password != null) {
            textInputPassword.setError(password);
            textInputPassword.setErrorIconDrawable(null);
            edtPassword.setBackground(findDrawable(R.drawable.edit_text_background_error));
        }
    }

    @Override
    public void onUserLogged() {
        MainActivity.launch(this, MainActivity.REGISTER_ACTIVITY);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.login_btn_enter)
    public void onButtonEnterClick() {
        presenter.login(edtEmail.getText().toString(), edtPassword.getText().toString());
    }

    @OnClick(R.id.login_tev_register)
    public void onTextViewRegisterClick() {
        RegisterActivity.launch(this);
    }

    @Override
    protected void onInject() {
        LoginDataSource dataSource = new LoginLocalDataSource();
        presenter = new LoginPresenter(this, dataSource);
    }

    @OnTextChanged({R.id.login_edt_email, R.id.login_edt_password})
    public void onTextChanged(CharSequence s) {
        buttonEnter.setEnabled(!edtEmail.getText().toString().isEmpty() &&
                !edtPassword.getText().toString().isEmpty());
        if (s.hashCode() == edtEmail.getText().hashCode()) {
            edtEmail.setBackground(findDrawable(R.drawable.edit_text_background));
            textInputEmail.setError(null);
            textInputEmail.setErrorEnabled(false);
        } else if (s.hashCode() == edtPassword.getText().hashCode()) {
            edtPassword.setBackground(findDrawable(R.drawable.edit_text_background));
            textInputPassword.setError(null);
            textInputPassword.setErrorEnabled(false);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

}