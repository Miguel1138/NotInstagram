package com.miguel_santos.notinstagram.main.profile.datasource;

import android.net.Uri;

import com.miguel_santos.notinstagram.common.model.UserProfile;
import com.miguel_santos.notinstagram.common.presenter.Presenter;

public interface ProfileDataSource {
    void findUser(Presenter<UserProfile> presenter);

    void changeProfilePhoto(Uri photo, Presenter presenter);
}
