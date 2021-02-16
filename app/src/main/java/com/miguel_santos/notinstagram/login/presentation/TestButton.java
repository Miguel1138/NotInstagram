package com.miguel_santos.notinstagram.login.presentation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.miguel_santos.notinstagram.R;

public class TestButton extends FrameLayout {

    private AppCompatButton button;
    private ProgressBar progressBar;
    private String text;

    public TestButton(@NonNull Context context) {
        super(context);
        setup(context, null);
    }

    public TestButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public TestButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.button_loading, this, true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestButton, 0, 0);
        text = typedArray.getString(R.styleable.TestButton_text);
        typedArray.recycle();

        button = (AppCompatButton) getChildAt(0);
        button.setText(text);
        button.setEnabled(false);

        progressBar = (ProgressBar) getChildAt(1);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable wrap = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrap, ContextCompat.getColor(context, R.color.white));
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrap));
        } else {
            PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(
                    ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
            progressBar.getIndeterminateDrawable().setColorFilter(colorFilter);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        button.setOnClickListener(l);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        button.setEnabled(enabled);
    }

    public void showProgress(boolean enabled) {
        progressBar.setVisibility(enabled ? VISIBLE : GONE);
        button.setText(enabled ? "" : text);
    }

}
