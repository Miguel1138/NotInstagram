package com.miguel_santos.notinstagram.common.components;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private final Camera camera;
    private final SurfaceHolder holder;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface() == null) return;

        try {
            camera.stopPreview();
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);

            Camera.Parameters parameters = camera.getParameters();
            int w = width;
            int h = height;

            List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
            for (Camera.Size size : supportedPreviewSizes) {
                w = size.width;
                h = size.height;
                // Takes the first supportedPreviewSize
                break;
            }

            parameters.setPreviewSize(w, h);
            parameters.setPictureSize(w, h);
            camera.setParameters(parameters);

            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

}
