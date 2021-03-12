package com.miguel_santos.notinstagram.register.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.components.LoadingButton;
import com.miguel_santos.notinstagram.common.view.AbstractFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterWelcomeFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.WelcomeView {

    @BindView(R.id.register_tev_welcome)
    TextView tevWelcome;
    @BindView(R.id.register_btn_next)
    LoadingButton btnNext;

    public RegisterWelcomeFragment() {

    }

    public static RegisterWelcomeFragment newInstance(RegisterPresenter presenter) {
        RegisterWelcomeFragment fragment = new RegisterWelcomeFragment();
        fragment.setPresenter(presenter);
        presenter.setWelcomeView(fragment);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext.setEnabled(true);
        tevWelcome.setText(getString(R.string.welcome_to_instagram, presenter.getName()));
    }

    @OnClick(R.id.register_btn_next)
    public void onButtonNextClick() {
        presenter.showPhotoView();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_welcome;
    }
}
