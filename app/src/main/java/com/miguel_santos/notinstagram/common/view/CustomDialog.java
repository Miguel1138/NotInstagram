package com.miguel_santos.notinstagram.common.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import com.miguel_santos.notinstagram.R;

public class CustomDialog extends Dialog {

    private LinearLayout dialogLinearLayout;
    private LinearLayout.LayoutParams layoutParams;
    private TextView titleView;
    private TextView[] textViews;
    private int titleId;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);
        layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogLinearLayout = findViewById(R.id.dialog_container);
        titleView = findViewById(R.id.dialog_title);
        layoutParams.setMargins(30, 50, 30, 50);
    }

    @Override
    public void setTitle(int titleId) {
        this.titleId = titleId;
    }

    @Override
    public void show() {
        super.show();
        titleView.setText(titleId);

        for (TextView textView : textViews) {
            //Para cada novo textView criado ele setará as margens de acordo
            //com o que foi criado no layoutParams.setMargin
            dialogLinearLayout.addView(textView, layoutParams);
        }
    }

    private void addButton(final View.OnClickListener listener, @StringRes int... texts) {
        textViews = new TextView[texts.length];
        for (int i = 0; i < texts.length; i++) {
            TextView textView = new TextView(new ContextThemeWrapper(getContext(), R.style.InstaTextViewBaseDialog), null);
            textView.setId(texts[i]);
            textView.setText(texts[i]);
            textView.setOnClickListener(v -> {
                listener.onClick(v);
                dismiss();
            });
            textViews[i] = textView;
        }
    }

    public static class Builder {

        private final Context context;
        private int titleId;
        private View.OnClickListener listener;
        private int[] texts;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(@StringRes int titleId) {
            this.titleId = titleId;
            return this;
        }

        public Builder addButton(View.OnClickListener listener, @StringRes int... texts) {
            this.listener = listener;
            this.texts = texts;
            return this;
        }

        public CustomDialog build() {
            CustomDialog customDialog = new CustomDialog(context);
            customDialog.setTitle(titleId);
            customDialog.addButton(listener, texts);
            customDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_custom);
            return customDialog;
        }

    }

}
