package com.miguel_santos.notinstagram.main.profile.datasource;

import com.miguel_santos.notinstagram.common.model.UserProfile;
import com.miguel_santos.notinstagram.common.presenter.Presenter;

public interface ProfileDataSource {
    void findUser(String user, Presenter<UserProfile> presenter);

    void followOrUnfollow(boolean follow, String user);
}
