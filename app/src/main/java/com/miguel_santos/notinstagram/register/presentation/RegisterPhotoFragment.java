package com.miguel_santos.notinstagram.register.presentation;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.components.CustomDialog;
import com.miguel_santos.notinstagram.common.components.LoadingButton;
import com.miguel_santos.notinstagram.common.view.AbstractFragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterPhotoFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.PhotoView {

    @BindView(R.id.register_btn_next)
    LoadingButton btnNext;
    @BindView(R.id.register_camera_icon)
    ImageView imageViewCropped;

    public RegisterPhotoFragment() {

    }

    public static RegisterPhotoFragment newInstance(RegisterPresenter presenter) {
        RegisterPhotoFragment fragment = new RegisterPhotoFragment();
        presenter.setPhotoView(fragment);
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext.setEnabled(true);
    }

    @Override
    public void onImageCropped(Uri uri) {
        try {
            if (getContext() != null && getContext().getContentResolver() != null) {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                imageViewCropped.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            Log.e("ErrorTest", e.getMessage(), e);
            // TODO: 19/03/2021 Implementar ação em caso de erro.
        }
    }

    @OnClick(R.id.register_btn_next)
    public void onButtonNextClick() {
        CustomDialog customDialog = new CustomDialog.Builder(getContext())
                .setTitle(R.string.define_photo_profile)
                .addButton((v) -> {
                    switch (v.getId()) {
                        case R.string.take_picture:
                            presenter.showCamera();
                            break;
                        case R.string.search_gallery:
                            presenter.showGallery();
                            break;
                    }
                }, R.string.import_from_facebook, R.string.take_picture, R.string.search_gallery)
                .build();
        customDialog.show();
    }

    @OnClick(R.id.register_btn_jump)
    public void onButtonJumpClick() {
        presenter.jumpRegistration();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_photo;
    }
}
