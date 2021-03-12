package com.miguel_santos.notinstagram.register.presentation;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.components.LoadingButton;
import com.miguel_santos.notinstagram.common.view.AbstractFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisterNamePasswordFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.NamePasswordView {

    @BindView(R.id.register_edt_name)
    EditText edtName;
    @BindView(R.id.register_input_edt_name)
    TextInputLayout inputLayoutName;
    @BindView(R.id.register_edt_password)
    EditText edtPassword;
    @BindView(R.id.register_input_edt_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.register_edt_password_confirm)
    EditText edtConfirmPassword;
    @BindView(R.id.register_input_edt_password_confirm)
    TextInputLayout inputLayoutConfirmPasswrod;

    @Override
    public void onFailureCreateUser(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @BindView(R.id.register_name_btn_next)
    LoadingButton btnNext;

    public RegisterNamePasswordFragment() {

    }

    public static RegisterNamePasswordFragment newInstance(RegisterPresenter presenter) {
        RegisterNamePasswordFragment fragment = new RegisterNamePasswordFragment();
        fragment.setPresenter(presenter);
        presenter.setNamePasswordView(fragment);
        return fragment;
    }

    @Override
    public void showProgressBar() {
        btnNext.showProgress(true);
    }

    @Override
    public void hideProgressBar() {
        btnNext.showProgress(false);
    }

    @Override
    public void onFailureForm(String nameError, String passwordError) {
        if (nameError != null) {
            inputLayoutName.setError(nameError);
            inputLayoutName.setBackground(findDrawable(R.drawable.edit_text_background_error));
        }
        if (passwordError != null) {
            inputLayoutPassword.setError(passwordError);
            inputLayoutPassword.setBackground(findDrawable(R.drawable.edit_text_background_error));
        }
    }

    @OnClick(R.id.register_tev_login)
    public void onTextViewLoginClick() {
        if (isAdded() && getActivity() != null) {
            getActivity().finish();
        }
    }

    @OnClick(R.id.register_name_btn_next)
    public void onButtonNextClick() {
        presenter.setNameAndPassword(edtName.getText().toString(),
                edtPassword.getText().toString(), edtConfirmPassword.getText().toString());
    }

    @OnTextChanged({
            R.id.register_edt_name,
            R.id.register_edt_password,
            R.id.register_edt_password_confirm
    })
    public void onTextChanged(CharSequence s) {
        btnNext.setEnabled(!edtName.getText().toString().isEmpty() &&
                !edtPassword.getText().toString().isEmpty() &&
                !edtConfirmPassword.getText().toString().isEmpty());

        edtName.setBackground(findDrawable(R.drawable.edit_text_background));
        inputLayoutName.setError(null);
        inputLayoutName.setErrorEnabled(false);

        edtPassword.setBackground(findDrawable(R.drawable.edit_text_background));
        inputLayoutPassword.setError(null);
        inputLayoutPassword.setErrorEnabled(false);

        edtConfirmPassword.setBackground(findDrawable(R.drawable.edit_text_background));
        inputLayoutConfirmPasswrod.setError(null);
        inputLayoutConfirmPasswrod.setErrorEnabled(false);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_name_password;
    }

}
