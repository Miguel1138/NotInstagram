package com.miguel_santos.notinstagram.register.presentation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.components.MediaHelper;
import com.miguel_santos.notinstagram.common.view.AbstractActivity;
import com.miguel_santos.notinstagram.main.presentation.MainActivity;
import com.miguel_santos.notinstagram.register.datasource.RegisterDataSource;
import com.miguel_santos.notinstagram.register.datasource.RegisterFireDataSource;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends AbstractActivity implements RegisterView, MediaHelper.onImageCroppedListener {

    private RegisterPresenter presenter;
    private MediaHelper mediaHelper;

    @BindView(R.id.register_root_container)
    FrameLayout rootContainer;
    @BindView(R.id.register_scroll_view)
    ScrollView scrollView;
    @BindView(R.id.register_image_crop)
    CropImageView cropImageView;
    @BindView(R.id.register_btn_crop)
    Button btnCrop;

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDark();

        mediaHelper = MediaHelper.getInstance(this)
                .cropView(cropImageView)
                .listener(this);
    }

    @Override
    protected void onInject() {
        RegisterDataSource dataSource = new RegisterFireDataSource();
        presenter = new RegisterPresenter(dataSource);
        presenter.setRegisterView(this);

        showNextView(RegisterSteps.EMAIL);
    }

    @Override
    public void showNextView(RegisterSteps step) {
        Fragment fragment = null;

        switch (step) {
            case EMAIL:
                fragment = RegisterEmailFragment.newInstance(presenter);
                break;
            case NAME_PASSWORD:
                fragment = RegisterNamePasswordFragment.newInstance(presenter);
                break;
            case WELCOME:
                fragment = RegisterWelcomeFragment.newInstance(presenter);
                break;
            case PHOTO:
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) scrollView.getLayoutParams();
                layoutParams.gravity = Gravity.TOP;
                scrollView.setLayoutParams(layoutParams);
                fragment = RegisterPhotoFragment.newInstance(presenter);
                break;
        }
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropViewImageEnabled(true);
        MediaHelper mediaHelper = MediaHelper.getInstance(this);
        mediaHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void cropViewImageEnabled(boolean enabled) {
        cropImageView.setVisibility(enabled ? View.VISIBLE : View.GONE);
        scrollView.setVisibility(enabled ? View.GONE : View.VISIBLE);
        btnCrop.setVisibility(enabled ? View.VISIBLE : View.GONE);
        rootContainer.setBackgroundColor(enabled ? findColor(R.color.black) : findColor(R.color.white));
    }

    @Override
    public void onImageCropped(Uri uri) {
        presenter.setUri(uri);
    }

    @Override
    public void onImagePicked(Uri uri) {
        cropImageView.setImageUriAsync(uri);
    }

    @Override
    public void onUserCreated() {
        MainActivity.launch(this, MainActivity.REGISTER_ACTIVITY);
    }

    @Override
    public void showCamera() {
        mediaHelper.chooseCamera();
    }

    @Override
    public void showGallery() {
        mediaHelper.chooseGallery();
    }

    @OnClick(R.id.register_btn_crop)
    public void onCropButtonClick() {
        cropViewImageEnabled(false);
        MediaHelper.getInstance(this).cropImage(cropImageView);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

}