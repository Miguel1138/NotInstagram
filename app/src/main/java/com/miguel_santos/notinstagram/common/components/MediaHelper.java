package com.miguel_santos.notinstagram.common.components;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class MediaHelper {

    public interface onImageCroppedListener {

        void onImageCropped(Uri uri);

        void onImagePicked(Uri uri);

    }

    private static final int CAMERA_CODE = 1;
    private static final int GALLERY_CODE = 2;
    private static WeakReference<MediaHelper> INSTANCE;
    private Activity activity;

    private Fragment fragment;
    private Uri mCropImageUri;

    private onImageCroppedListener listener;
    private Uri mSavedImageUri;

    //Padrão singleton. para activities
    public static MediaHelper getInstance(Activity activity) {
        if (INSTANCE == null) {
            MediaHelper mediaHelper = new MediaHelper();
            //Corrigido o problema de Memory Leaking de ambas instâncias
            INSTANCE = new WeakReference<>(mediaHelper);
            INSTANCE.get().setActivity(activity);
        }
        return INSTANCE.get();
    }

    private void setActivity(Activity activity) {
        this.activity = activity;
    }

    // Padrão singleton para fragments
    public static MediaHelper getInstance(Fragment fragment) {
        if (INSTANCE == null) {
            MediaHelper mediaHelper = new MediaHelper();
            INSTANCE = new WeakReference<>(mediaHelper);
            INSTANCE.get().setFragment(fragment);
        }
        return INSTANCE.get();
    }

    private void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    private Context getContext() {
        if (fragment != null && fragment.getActivity() != null) {
            return fragment.getActivity();
        }
        return activity;
    }

    public void chooseGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, GALLERY_CODE);
    }

    public void chooseCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager = getContext().getPackageManager();
        if (i.resolveActivity(packageManager) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                mCropImageUri = FileProvider.getUriForFile(getContext(), "com.miguel_santos.notinstagram.fileprovider", photoFile);

                SharedPreferences myPrefs = getContext().getSharedPreferences("camera_image", 0);
                SharedPreferences.Editor edit = myPrefs.edit();
                edit.putString("url", mCropImageUri.toString());
                edit.apply();

                i.putExtra(MediaStore.EXTRA_OUTPUT, mCropImageUri);
                activity.startActivityForResult(i, CAMERA_CODE);
            }
        }
    }

    // Builders
    public MediaHelper listener(onImageCroppedListener listener) {
        this.listener = listener;
        return this;
    }

    public MediaHelper cropView(CropImageView cropImageView) {
        cropImageView.setAspectRatio(1, 1);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            @Override
            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                Uri uri = result.getUri();
                if (uri != null && listener != null) {
                    listener.onImageCropped(uri);
                }
            }
        });
        return this;
    }
    // </Builder>

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        SharedPreferences myPrefs = getContext().getSharedPreferences("camera_image", 0);
        String url = myPrefs.getString("url", null);

        if (mCropImageUri == null && url != null) {
            mCropImageUri = Uri.parse(url);
        }

        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
            if (CropImage.isReadExternalStoragePermissionsRequired(getContext(), mCropImageUri)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (activity != null) {
                        activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                    } else {
                        fragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                    }
                }
            } else {
                listener.onImagePicked(mCropImageUri);
            }
        } else if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            mCropImageUri = CropImage.getPickImageResultUri(getContext(), data);
            listener.onImagePicked(mCropImageUri);
        }
    }

    public void cropImage(CropImageView cropImageView) {
        File getImage = getContext().getExternalCacheDir();
        if (getImage != null) {
            mSavedImageUri = Uri.fromFile(new File(getImage.getPath(), System.currentTimeMillis() + ".jpeg"));
        }
        cropImageView.saveCroppedImageAsync(mSavedImageUri);
    }

    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timestamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpeg", storageDir);
    }

    public boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public Camera getCameraInstance() {
        Camera camera = null;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && getContext() != null
                    && getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (activity != null)
                    activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, 300);
                else
                    fragment.requestPermissions(new String[]{Manifest.permission.CAMERA}, 300);
            }
            camera = camera.open();
        } catch (Exception e) {

        }
        return camera;
    }

    public void saveCameraFile(byte[] data) {
        File pictureFile = createCameraFile(true);
    }

    private File createCameraFile(boolean temp) {
        if(getContext() == null) return null;

        File mediaStorageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (mediaStorageDir != null && !mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("TESTE", "failed to create Directory");
                return null;
            }
        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator + (temp ? "TEMP_" : "IMG_") + timestamp + ".jpg");
    }

}
