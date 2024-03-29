package com.miguel_santos.notinstagram.main.presentation;

import com.miguel_santos.notinstagram.common.model.Feed;
import com.miguel_santos.notinstagram.common.model.Post;
import com.miguel_santos.notinstagram.common.model.User;
import com.miguel_santos.notinstagram.common.view.View;

import java.util.List;

public interface MainView extends View {

    void scrollToolbarEnabled(boolean enabled);

    void showProfile(String user);

    void disposeProfileDetail();

    void logout();

    interface ProfileView extends View {
        void showPhoto(String photo);

        void showData(String name, String follower, String following, String posts, boolean editProfile, boolean follow);

        void showPosts(List<Post> posts);
    }

    interface HomeView extends View {
        void showFeed(List<Feed> response);
    }

    interface SearchView {
        void showUsers(List<User> users);
    }

}
