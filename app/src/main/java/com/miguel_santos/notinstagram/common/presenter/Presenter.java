package com.miguel_santos.notinstagram.common.presenter;

import com.miguel_santos.notinstagram.common.model.UserAuth;

public interface Presenter {

    void onSuccess(UserAuth response);

    void onError(String message);

    void onCompleted();

}
