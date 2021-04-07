package com.miguel_santos.notinstagram.main.presentation;

import android.net.Uri;

import com.miguel_santos.notinstagram.common.model.Feed;
import com.miguel_santos.notinstagram.common.model.Post;
import com.miguel_santos.notinstagram.common.view.View;

import java.util.List;

public interface MainView extends View {

    void scrollToolbarEnabled(boolean enabled);

    interface ProfileView extends View {

        void showPhoto(Uri photo);

        void showData(String name, String follower, String following, String posts);

        void showPosts(List<Post> posts);
    }

    interface HomeView extends View {

        void showFeed(List<Feed> response);
    }

}
