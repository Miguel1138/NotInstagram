package com.miguel_santos.notinstagram.common.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class MediaHelper {

    private static final int CAMERA_CODE = 1;
    private static final int GALLERY_CODE = 2;
    private static MediaHelper INSTANCE;

    private Activity activity;
    private Fragment fragment;

    private Uri mCropImageUri;
    private CropImageView cropImageView;
    private onImageCroppedListener listener;
    private Uri mSavedImageUri;

    //Padrão singleton. para activities
    public static MediaHelper getInstance(Activity activity) {
        if (INSTANCE == null) {
            INSTANCE = new MediaHelper();
        }
        INSTANCE.setActivity(activity);
        return INSTANCE;
    }

    // Padrão singleton para fragments
    public static MediaHelper getInstance(Fragment fragment) {
        if (INSTANCE == null) {
            INSTANCE = new MediaHelper();
        }
        INSTANCE.setFragment(fragment);
        return INSTANCE;
    }

    public void chooseGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, GALLERY_CODE);
    }

    public void chooseCamera() {

    }

    private void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    private void setActivity(Activity activity) {
        this.activity = activity;
    }

    private Context getContext() {
        if (fragment != null && fragment.getActivity() != null) {
            return fragment.getActivity();
        }
        return activity;
    }

    public MediaHelper listener(onImageCroppedListener listener) {
        this.listener = listener;
        return this;
    }

    public MediaHelper cropView(CropImageView cropImageView) {
        this.cropImageView = cropImageView;
        cropImageView.setAspectRatio(1, 1);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            @Override
            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                Uri uri = result.getUri();
                if (uri != null && listener != null) {
                    listener.onImageCropped(uri);
                    cropImageView.setVisibility(View.GONE);
                }
            }
        });

        return this;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {

        } else if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            mCropImageUri = CropImage.getPickImageResultUri(getContext(), data);
            startCropImageActivity();
        }
    }

    private void startCropImageActivity() {
        cropImageView.setImageUriAsync(mCropImageUri);
    }

    public void cropImage() {
        File getImage = getContext().getExternalCacheDir();
        if (getImage != null) {
            mSavedImageUri = Uri.fromFile(new File(getImage.getPath(), System.currentTimeMillis() + ".jpeg"));
        }
        cropImageView.saveCroppedImageAsync(mSavedImageUri);
    }

    public interface onImageCroppedListener {
        void onImageCropped(Uri uri);
    }
}
