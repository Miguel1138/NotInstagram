package com.miguel_santos.notinstagram.common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.miguel_santos.notinstagram.common.util.Colors;
import com.miguel_santos.notinstagram.common.util.Drawables;

import butterknife.ButterKnife;

public abstract class AbstractFragment<P> extends Fragment implements com.miguel_santos.notinstagram.common.view.View {

    // tipagem do presenter.
    protected P presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {
    }

    @Override
    public void setStatusBarDark(boolean enable) {
    }

    public Drawable findDrawable(@DrawableRes int drawableId) {
        return Drawables.getDrawable(getContext(), drawableId);
    }

    public int findColor(@ColorRes int colorId) {
        return Colors.getColor(getContext(), colorId);
    }

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    protected abstract @LayoutRes
    int getLayout();

}
