package com.miguel_santos.notinstagram.main.home.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.model.Feed;
import com.miguel_santos.notinstagram.common.model.User;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private List<Feed> feed = new ArrayList<>();

    public void setFeed(List<Feed> feed) {
        this.feed = feed;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeedViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_post_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.bind(feed.get(position));
    }

    @Override
    public int getItemCount() {
        return feed.size();
    }

    protected static class FeedViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imagePost;
        private final ImageView imageUser;
        private final TextView txtCaption;
        private final TextView txtUsername;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePost = itemView.findViewById(R.id.profile_image_grid);
            imageUser = itemView.findViewById(R.id.home_container_user_photo);
            txtCaption = itemView.findViewById(R.id.home_container_user_caption);
            txtUsername = itemView.findViewById(R.id.home_container_user_username);
        }

        // Get the posts in the homeFragment feed.
        public void bind(Feed feed) {
            this.imagePost.setImageURI(feed.getUri());
            this.txtCaption.setText(feed.getCaption());

            User user = feed.getPublisher();
            if (user != null) {
                this.imageUser.setImageURI(user.getUri());
                this.txtUsername.setText(user.getName());
            }
        }

    }

}


