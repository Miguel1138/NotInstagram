package com.miguel_santos.notinstagram.main.camera.datasource;

import android.net.Uri;

import com.miguel_santos.notinstagram.common.presenter.Presenter;

public interface AddDataSource {

    void savePost(Uri uri, String caption, Presenter presenter);
}
