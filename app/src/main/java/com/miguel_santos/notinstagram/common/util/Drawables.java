package com.miguel_santos.notinstagram.common.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

public final class Drawables {

    public static Drawable getDrawable(Context context, @DrawableRes int drawableId) {
        return ContextCompat.getDrawable(context, drawableId);
    }

}
