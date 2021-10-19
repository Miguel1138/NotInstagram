package com.miguel_santos.notinstagram.main.camera.presentation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.view.AbstractFragment;

import java.util.List;

import butterknife.BindView;

public class GalleryFragment extends AbstractFragment<GalleryPresenter> implements GalleryView {

    @BindView(R.id.main_gallery_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.main_gallery_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.main_gallery_camera_image_grid)
    ImageView imageView;

    private PictureAdapter pictureAdapter;
    private AddView addView;
    private Uri uri;

    public static GalleryFragment newInstance(AddView addView, GalleryPresenter galleryPresenter) {
        GalleryFragment fragment = new GalleryFragment();
        galleryPresenter.setView(fragment);
        fragment.setPresenter(galleryPresenter);
        fragment.addView(addView);

        return fragment;
    }

    private void addView(AddView addView) {
        this.addView = addView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        pictureAdapter = new PictureAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(pictureAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getContext() != null
                && getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {
            presenter.findPictures(getContext());
        }
    }

    @Override
    public void onPicturesLoaded(List<Uri> uriList) {
        // Verifies if the uriList has at least one item.
        if (uriList.size() > 0) {
            imageView.setImageURI(uriList.get(0));
            this.uri = uriList.get(0);
        }

        pictureAdapter.setPictures(uriList, uri1 -> {
            this.uri = uri1;
            imageView.setImageURI(uri1);
            nestedScrollView.smoothScrollTo(0, 0);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_gallery, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_go) {
            // Call the AddCaptionActivity layout.
            addView.onImageLoaded(uri);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_gallery;
    }

}
