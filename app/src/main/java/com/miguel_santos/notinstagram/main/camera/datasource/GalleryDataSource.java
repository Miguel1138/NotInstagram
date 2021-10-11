package com.miguel_santos.notinstagram.main.camera.datasource;

import android.content.Context;

import com.miguel_santos.notinstagram.common.presenter.Presenter;

public interface GalleryDataSource {
    void findPictures(Context context, Presenter presenter);
}
