package com.miguel_santos.notinstagram.main.camera.presentation;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.components.CameraPreview;
import com.miguel_santos.notinstagram.common.components.MediaHelper;
import com.miguel_santos.notinstagram.common.view.AbstractFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class CameraFragment extends AbstractFragment {

    @BindView(R.id.camera_surface)
    FrameLayout frameLayout;
    @BindView(R.id.camera_image_picture)
    Button cameraButton;
    @BindView(R.id.container_preview)
    LinearLayout containerPreview;
    @BindView(R.id.camera_progress)
    ProgressBar progressBar;

    private MediaHelper mediaHelper;
    private Camera camera;

    public CameraFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (getContext() != null) {
            mediaHelper = MediaHelper.getInstance(this);
            if (mediaHelper.checkCameraHardware(getContext())) {
                camera = mediaHelper.getCameraInstance();
                CameraPreview cameraPreview = new CameraPreview(getContext(), camera);
                frameLayout.addView(cameraPreview);
            }
        }

        return view;
    }

    @OnClick(R.id.camera_image_picture)
    public void onCameraButtonClick() {
        progressBar.setVisibility(View.VISIBLE);
        cameraButton.setVisibility(View.GONE);
        camera.takePicture(null, null, (data, camera) -> {
            mediaHelper.saveCameraFile(data);
            progressBar.setVisibility(View.GONE);
            cameraButton.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (camera != null)
            camera.release();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_camera;
    }
}
