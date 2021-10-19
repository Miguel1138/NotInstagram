package com.miguel_santos.notinstagram.main.camera.presentation;

import android.net.Uri;

public interface AddView {

    void onImageLoaded(Uri uri);

    void dispose();
}
