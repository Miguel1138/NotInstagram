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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.view.LoadingButton;

public class LoginActivity extends AppCompatActivity {

    private LoadingButton buttonEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }

        final EditText edtEmail = findViewById(R.id.login_edt_email);
        edtEmail.addTextChangedListener(watcher);

        final EditText edtPassword = findViewById(R.id.login_edt_password);
        edtPassword.addTextChangedListener(watcher);

        buttonEnter = findViewById(R.id.login_btn_enter);
        buttonEnter.setOnClickListener(v -> {
            buttonEnter.showProgress(true);

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                buttonEnter.showProgress(false);

                TextInputLayout textInputEmail = findViewById(R.id.login_input_edt_email);
                textInputEmail.setError("Esse email não é válido");
                textInputEmail.setErrorIconDrawable(null);
                edtEmail.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.edit_text_background_error));

                TextInputLayout textInputPassword = findViewById(R.id.login_input_edt_password);
                textInputPassword.setError("Senha incorreta!");
                textInputPassword.setErrorIconDrawable(null);
                edtPassword.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.edit_text_background_error));
            }, 4000);
        });
    }

    private final TextWatcher watcher = new TextWatcher() {
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
    };

}