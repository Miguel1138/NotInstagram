package com.miguel_santos.notinstagram.main.camera.datasource;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.miguel_santos.notinstagram.common.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class GalleryLocalDataSource implements GalleryDataSource {

    @Override
    public void findPictures(Context context, Presenter presenter) {
        List<String> images = null;

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.MediaColumns.DATE_ADDED
        };

        if (context != null && context.getContentResolver() != null) {
            // Creates query sorting by it's date.
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED);
            if (cursor != null) {
                images = new ArrayList<>();

                // get the Indexes of every field
                int columnDataIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                int columnFolderIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                int columnDateAddedIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);

                while (cursor.moveToNext()) {
                    // Convert the data to string
                    String absoluteImagePath = cursor.getString(columnDataIndex);
                    String folder = cursor.getString(columnFolderIndex);
                    String dateAdded = cursor.getString(columnDateAddedIndex);

                    // So far I'm just adding the image.
                    images.add(absoluteImagePath);
                }
            }
        }

        if (images != null && !images.isEmpty()) presenter.onSuccess(images);
    }

}
