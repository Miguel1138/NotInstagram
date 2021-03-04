package com.miguel_santos.notinstagram.register.presentation;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.view.AbstractFragment;
import com.miguel_santos.notinstagram.common.view.LoadingButton;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisterEmailFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.EmailView {

    @BindView(R.id.register_edt_email)
    EditText edtEmail;
    @BindView(R.id.register_input_edt_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.register_btn_next)
    LoadingButton btnNext;

    public RegisterEmailFragment() {

    }

    public static RegisterEmailFragment newInstance(RegisterPresenter presenter) {
        RegisterEmailFragment fragment = new RegisterEmailFragment();
        // O fragment tem visão do método setPresenter pois ele herdou da classe AbstractFragment
        fragment.setPresenter(presenter);
        presenter.setEmailView(fragment);

        return fragment;
    }

    @Override
    public void showNextView() {

    }

    @Override
    public void onFailureForm(String emailError) {
        inputLayoutEmail.setError(emailError);
        inputLayoutEmail.setBackground(findDrawable(R.drawable.edit_text_background_error));
    }

    @OnClick(R.id.register_btn_next)
    public void onButtonNextClick() {
        presenter.setEmail(edtEmail.getText().toString());
    }

    @OnClick(R.id.register_tev_login)
    public void onTextViewClick() {
        if (isAdded() && getActivity() != null) {
            getActivity().finish();
        }
    }

    @OnTextChanged(R.id.register_edt_email)
    public void onTextChanged(CharSequence s) {
        btnNext.setEnabled(!edtEmail.getText().toString().isEmpty());
        if (s.hashCode() == edtEmail.getText().hashCode()) {
            edtEmail.setBackground(findDrawable(R.drawable.edit_text_background));
            inputLayoutEmail.setError(null);
            inputLayoutEmail.setErrorEnabled(false);
        }
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
    protected int getLayout() {
        return R.layout.fragment_register_email;
    }

}