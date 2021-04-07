package com.miguel_santos.notinstagram.main.home.datasource;

import com.miguel_santos.notinstagram.common.model.Feed;
import com.miguel_santos.notinstagram.common.presenter.Presenter;

import java.util.List;

public interface HomeDataSource {

    void findFeed(Presenter<List<Feed>> presenter);

}
