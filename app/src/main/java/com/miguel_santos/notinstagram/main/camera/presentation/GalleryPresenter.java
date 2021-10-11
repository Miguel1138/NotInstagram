package com.miguel_santos.notinstagram.main.camera.presentation;

import android.content.Context;
import android.net.Uri;

import com.miguel_santos.notinstagram.common.presenter.Presenter;
import com.miguel_santos.notinstagram.main.camera.datasource.GalleryLocalDataSource;

import java.util.ArrayList;
import java.util.List;

public class GalleryPresenter implements Presenter<List<String>> {

    private final GalleryLocalDataSource dataSource;
    private GalleryView view;

    public GalleryPresenter(GalleryLocalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(GalleryView view) {
        this.view = view;
    }

    public void findPictures(Context context) {
        view.showProgressBar();
        dataSource.findPictures(context, this);
    }

    @Override
    public void onSuccess(List<String> response) {
        ArrayList<Uri> uriArrayList = new ArrayList<>();
        // Parse every string in the list into a Uri object and add to the uriList.
        for (String res : response) {
            Uri uri = Uri.parse(res);
            uriArrayList.add(uri);
        }

        view.onPicturesLoaded(uriArrayList);
    }

    @Override
    public void onError(String message) {
        // TODO: 11/10/2021
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }

}
