package com.miguel_santos.notinstagram.main.camera.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.main.presentation.MainView;

public class CameraFragment extends Fragment {

    private MainView mainView;

    public CameraFragment() {
    }

    public static CameraFragment newInstance(MainView mainView) {
        CameraFragment cameraFragment = new CameraFragment();
        cameraFragment.setMainView(mainView);
        return cameraFragment;
    }

    private void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_gallery, container, false);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

}
