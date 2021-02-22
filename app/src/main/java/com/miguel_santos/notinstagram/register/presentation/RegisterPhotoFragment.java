package com.miguel_santos.notinstagram.register.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.view.CustomDialog;

public class RegisterPhotoFragment extends Fragment {

    public RegisterPhotoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_photo, container, false);
        //TODO: 18/02/2021 scroll gravity Top
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CustomDialog customDialog = new CustomDialog.Builder(getContext())
                .setTitle(R.string.define_photo_profile)
                .addButton((v) -> {
                    switch (v.getId()) {
                        case R.string.take_picture:
                            Log.i("TESTE", "Taking picture");
                            break;
                        case R.string.search_gallery:
                            Log.i("TESTE", "Procurando a galerinha");
                    }
                }, R.string.import_from_facebook,R.string.take_picture, R.string.search_gallery)
                .build();
        customDialog.show();
    }

}
