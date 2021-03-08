package com.miguel_santos.notinstagram.register.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.view.AbstractActivity;
import com.miguel_santos.notinstagram.main.presentation.MainActivity;
import com.miguel_santos.notinstagram.register.datasource.RegisterLocalDataSource;

import butterknife.BindView;

public class RegisterActivity extends AbstractActivity implements RegisterView {

    private RegisterPresenter presenter;
    @BindView(R.id.register_scroll_view)
    ScrollView scrollView;

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDark();
    }

    @Override
    protected void onInject() {
        RegisterLocalDataSource dataSource = new RegisterLocalDataSource();
        presenter = new RegisterPresenter(dataSource);
        presenter.setRegisterView(this);

        showNextView(RegisterSteps.EMAIL);
    }

    @Override
    public void showNextView(RegisterSteps step) {
        Fragment fragment = null;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) scrollView.getLayoutParams();

        switch (step) {
            case EMAIL:
                layoutParams.gravity = Gravity.BOTTOM;
                fragment = RegisterEmailFragment.newInstance(presenter);
                break;
            case NAME_PASSWORD:
                layoutParams.gravity = Gravity.BOTTOM;
                fragment = RegisterNamePasswordFragment.newInstance(presenter);
                break;
            case WELCOME:
                layoutParams.gravity = Gravity.BOTTOM;
                fragment = RegisterWelcomeFragment.newInstance(presenter);
                break;
            case PHOTO:
                layoutParams.gravity = Gravity.TOP;
                fragment = RegisterPhotoFragment.newInstance(presenter);
                break;
        }
        scrollView.setLayoutParams(layoutParams);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (manager.findFragmentById(R.id.register_fragment) == null) {
            transaction.add(R.id.register_fragment, fragment, step.name());
        } else {
            transaction.replace(R.id.register_fragment, fragment, step.name());
            transaction.addToBackStack(step.name());
        }

        transaction.commit();
    }

    @Override
    public void onUserCreated() {
        MainActivity.launch(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

}