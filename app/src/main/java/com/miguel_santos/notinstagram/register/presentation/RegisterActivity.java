package com.miguel_santos.notinstagram.register.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.view.AbstractActivity;

public class RegisterActivity extends AbstractActivity implements RegisterView {

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
        RegisterEmailFragment fragment = new RegisterEmailFragment();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.register_fragment, fragment);

        transaction.commit();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

}