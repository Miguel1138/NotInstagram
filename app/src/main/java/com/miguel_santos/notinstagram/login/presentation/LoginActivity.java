package com.miguel_santos.notinstagram.login.presentation;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.view.AbstractActivity;
import com.miguel_santos.notinstagram.common.view.LoadingButton;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends AbstractActivity implements LoginView, TextWatcher {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }

        edtEmail.addTextChangedListener(this);
        edtPassword.addTextChangedListener(this);
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
    public void onFailureForm(String emailError, String passwordError) {
        if (emailError != null) {
            textInputEmail.setError(emailError);
            edtEmail.setBackground(findDrawable(R.drawable.edit_text_background_error));
        }
        if (passwordError != null) {
            textInputPassword.setError(passwordError);
            edtPassword.setBackground(findDrawable(R.drawable.edit_text_background_error));
        }
    }

    @Override
    public void onUserLogged() {
        // TODO: 26/02/2021
    }

    @OnClick(R.id.login_btn_enter)
    public void onButtonClick() {
        // TODO: 27/02/2021 Criar camada de apresentação
        buttonEnter.showProgress(true);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            buttonEnter.showProgress(false);
        }, 4000);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().isEmpty()) {
            buttonEnter.setEnabled(true);
        } else {
            buttonEnter.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

}