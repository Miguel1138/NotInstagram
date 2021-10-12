package com.miguel_santos.notinstagram.main.profile.presentation;

import android.net.Uri;

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

    private MainView mainView;
    private Uri uri;

    public ProfilePresenter(ProfileDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(MainView.ProfileView view) {
        this.view = view;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public void findUser() {
        view.showProgressBar();
        dataSource.findUser(this);
    }

    @Override
    public void onSuccess(UserProfile userProfile) {
        User user = userProfile.getUser();
        List<Post> postList = userProfile.getPosts();

        view.showData(
                user.getName(),
                String.valueOf(user.getFollowers()),
                String.valueOf(user.getFollowing()),
                String.valueOf(user.getPosts())
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

    public void setUri(Uri uri) {
        this.uri = uri;
        if (view != null) {
            view.showPhoto(uri);
            view.showProgressBar();

            // TODO: 12/10/2021 testando sem callback.
            dataSource.changeProfilePhoto(uri, new UpdatePhotoCallback());
        }
    }

    public void showCamera() {
        mainView.showCamera();
    }

    public void showGallery() {
        mainView.showGallery();
    }

    private class UpdatePhotoCallback implements Presenter<Boolean> {
        @Override
        public void onSuccess(Boolean response) {
            // if succeed starts the same activity with the new picture.
            mainView.onPhotoChanged();
        }

        @Override
        public void onError(String message) {

        }

        @Override
        public void onComplete() {

        }
    }

}
