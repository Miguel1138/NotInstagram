package com.miguel_santos.notinstagram.main.home.presentation;

import com.miguel_santos.notinstagram.common.model.Feed;
import com.miguel_santos.notinstagram.common.presenter.Presenter;
import com.miguel_santos.notinstagram.main.home.datasource.HomeDataSource;
import com.miguel_santos.notinstagram.main.presentation.MainView;

import java.util.List;

public class HomePresenter implements Presenter<List<Feed>> {

    private HomeDataSource dataSource;
    private MainView.HomeView view;

    public HomePresenter(HomeDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(MainView.HomeView view) {
        this.view = view;
    }

    public void findFeed() {
        view.showProgressBar();
        dataSource.findFeed(this);
    }

    @Override
    public void onSuccess(List<Feed> response) {
        view.showFeed(response);
    }

    @Override
    public void onError(String message) {
        // TODO: 07/04/2021
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }

}
