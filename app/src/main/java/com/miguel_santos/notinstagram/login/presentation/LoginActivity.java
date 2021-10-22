package com.miguel_santos.notinstagram.login.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.components.LoadingButton;
import com.miguel_santos.notinstagram.common.view.AbstractActivity;
import com.miguel_santos.notinstagram.login.datasource.LoginDataSource;
import com.miguel_santos.notinstagram.login.datasource.LoginFireDataSource;
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

    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDark();

        String user = FirebaseAuth.getInstance().getUid();
        if (user != null) onUserLogged();
    }

    @Override
    protected void onInject() {
        LoginDataSource dataSource = new LoginFireDataSource();
        presenter = new LoginPresenter(this, dataSource);
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
        MainActivity.launch(this, MainActivity.LOGIN_ACTIVITY);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void showProgressBar() {
        buttonEnter.showProgress(true);
    }

    @Override
    public void hideProgressBar() {
        buttonEnter.showProgress(false);
    }

    @OnClick(R.id.login_btn_enter)
    public void onButtonEnterClick() {
        presenter.login(edtEmail.getText().toString(), edtPassword.getText().toString());
    }

    @OnClick(R.id.login_tev_register)
    public void onTextViewRegisterClick() {
        RegisterActivity.launch(this);
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