package com.miguel_santos.notinstagram.common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.util.Colors;
import com.miguel_santos.notinstagram.common.util.Drawables;

import butterknife.ButterKnife;

public abstract class AbstractActivity extends AppCompatActivity implements View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);

        onInject();
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    public Drawable findDrawable(@DrawableRes int drawableId) {
        return Drawables.getDrawable(this, drawableId);
    }

    public int findColor(@ColorRes int colorId) {
        return Colors.getColor(this, colorId);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void setStatusBarDark(boolean enable) {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (enable) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(findColor(R.color.black));
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(findColor(R.color.white));
            }
        }
    }

    protected abstract @LayoutRes
    int getLayout();

    protected abstract void onInject();

}
