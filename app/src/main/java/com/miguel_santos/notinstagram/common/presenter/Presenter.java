package com.miguel_santos.notinstagram.common.presenter;

import com.miguel_santos.notinstagram.common.model.UserAuth;

public interface Presenter<T> {
    //Tipagem genperica de dados
    void onSuccess(T response);

    void onError(String message);

    void onComplete();

}
