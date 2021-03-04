package com.miguel_santos.notinstagram.common.view;

import android.content.Context;

public interface View {

    void showProgressBar();

    void hideProgressBar();

    Context getContext();

    void setStatusBarDark();
}
