package com.miguel_santos.notinstagram.main.search.datasource;

import com.miguel_santos.notinstagram.common.model.User;
import com.miguel_santos.notinstagram.common.presenter.Presenter;

import java.util.List;

public interface SearchDataSource {
    void findUsers(String query, Presenter<List<User>> presenter);
}
