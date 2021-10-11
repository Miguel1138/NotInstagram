package com.miguel_santos.notinstagram.main.camera.presentation;

import android.net.Uri;

import com.miguel_santos.notinstagram.common.view.View;

import java.util.List;

public interface GalleryView extends View {
    void onPicturesLoaded(List<Uri> uriList);
}
