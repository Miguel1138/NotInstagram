package com.miguel_santos.notinstagram.login.presentation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.miguel_santos.notinstagram.R;

public class LoginActivity extends AppCompatActivity {

    private TestButton buttonEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
            findViewById(R.id.login_btn_enter).setEnabled(!s.toString().isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

}