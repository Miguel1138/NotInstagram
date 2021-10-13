package com.miguel_santos.notinstagram.main.profile.presentation;

import com.miguel_santos.notinstagram.common.model.Database;
import com.miguel_santos.notinstagram.common.model.Post;
import com.miguel_santos.notinstagram.common.model.User;
import com.miguel_santos.notinstagram.common.model.UserProfile;
import com.miguel_santos.notinstagram.common.presenter.Presenter;
import com.miguel_santos.notinstagram.main.presentation.MainView;
import com.miguel_santos.notinstagram.main.profile.datasource.ProfileDataSource;

import java.util.List;

public class ProfilePresenter implements Presenter<UserProfile> {

    private final ProfileDataSource dataSource;
    private MainView.ProfileView view;

    public ProfilePresenter(ProfileDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(MainView.ProfileView view) {
        this.view = view;
    }

    public void findUser(String user) {
        view.showProgressBar();
        dataSource.findUser(user, this);
    }

    @Override
    public void onSuccess(UserProfile userProfile) {
        User user = userProfile.getUser();
        List<Post> postList = userProfile.getPosts();

        boolean editProfile = user.getUuid().equals(Database.getInstance().getUser().getUUID());

        view.showData(
                user.getName(),
                String.valueOf(user.getFollowers()),
                String.valueOf(user.getFollowing()),
                String.valueOf(user.getPosts()),
                editProfile
        );

        view.showPosts(postList);

        if (user.getUri() != null)
            view.showPhoto(user.getUri());
    }

    @Override
    public void onError(String message) {
        // TODO: 05/04/2021
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }
}
