package com.miguel_santos.notinstagram.main.search.presentation;

import com.miguel_santos.notinstagram.common.model.User;
import com.miguel_santos.notinstagram.common.presenter.Presenter;
import com.miguel_santos.notinstagram.main.presentation.MainView;
import com.miguel_santos.notinstagram.main.search.datasource.SearchLocalDataSource;

import java.util.List;

public class SearchPresenter implements Presenter<List<User>> {

    private final SearchLocalDataSource dataSource;
    private MainView.SearchView view;

    public SearchPresenter(SearchLocalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(MainView.SearchView view) {
        this.view = view;
    }

    public void findUsers(String user) {
        dataSource.findUsers(user, this);
    }

    @Override
    public void onSuccess(List<User> response) {
        view.showUsers(response);
    }

    @Override
    public void onError(String message) {
        // TODO: 13/10/2021
    }

    @Override
    public void onComplete() {
    }
}
