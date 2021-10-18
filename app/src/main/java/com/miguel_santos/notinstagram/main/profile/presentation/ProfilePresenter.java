package com.miguel_santos.notinstagram.main.profile.presentation;

import com.google.firebase.auth.FirebaseAuth;
import com.miguel_santos.notinstagram.common.model.Post;
import com.miguel_santos.notinstagram.common.model.User;
import com.miguel_santos.notinstagram.common.model.UserProfile;
import com.miguel_santos.notinstagram.common.presenter.Presenter;
import com.miguel_santos.notinstagram.main.presentation.MainView;
import com.miguel_santos.notinstagram.main.profile.datasource.ProfileDataSource;

import java.util.List;

public class ProfilePresenter implements Presenter<UserProfile> {

    private final ProfileDataSource dataSource;
    private final String user;
    private MainView.ProfileView view;

    public ProfilePresenter(ProfileDataSource dataSource) {
        this(dataSource, FirebaseAuth.getInstance().getUid());
    }

    public ProfilePresenter(ProfileDataSource dataSource, String user) {
        this.dataSource = dataSource;
        this.user = user;
    }

    public void setView(MainView.ProfileView view) {
        this.view = view;
    }

    public String getUser() {
        return user;
    }

    public void findUser() {
        view.showProgressBar();
        dataSource.findUser(user, this);
    }

    public void followOrUnfollow(boolean follow) {
        dataSource.followOrUnfollow(follow, getUser());
    }

    @Override
    public void onSuccess(UserProfile userProfile) {
        User user = userProfile.getUser();
        List<Post> postList = userProfile.getPosts();

        // Verify if the profile page belongs to himself or other user.
        boolean editProfile = user.getUuid().equals(FirebaseAuth.getInstance().getUid());

        view.showData(
                user.getName(),
                String.valueOf(user.getFollowers()),
                String.valueOf(user.getFollowing()),
                String.valueOf(user.getPosts()),
                editProfile,
                userProfile.isFollowing()
        );

        view.showPosts(postList);

        if (user.getPhotoUrl() != null)
            view.showPhoto(user.getPhotoUrl());
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
