package com.miguel_santos.notinstagram.main.camera.presentation;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miguel_santos.notinstagram.R;

import java.util.ArrayList;
import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PostViewHolder> {

    private GalleryItemClickListener listener;
    private List<Uri> uris = new ArrayList<>();

    public void setPictures(List<Uri> uris, GalleryItemClickListener listener) {
        this.uris = uris;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_grid, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(uris.get(position));
        holder.imgPost.setOnClickListener(v -> {
            Uri uri = uris.get(position);
            listener.onClick(uri);
        });
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    interface GalleryItemClickListener {
        void onClick(Uri uri);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgPost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPost = itemView.findViewById(R.id.profile_image_grid);
        }

        public void bind(Uri uri) {
            this.imgPost.setImageURI(uri);
        }

    }

}
