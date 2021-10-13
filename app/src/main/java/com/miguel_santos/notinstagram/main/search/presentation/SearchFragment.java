package com.miguel_santos.notinstagram.main.search.presentation;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.model.User;
import com.miguel_santos.notinstagram.common.view.AbstractFragment;
import com.miguel_santos.notinstagram.main.presentation.MainView;

import java.util.List;

import butterknife.BindView;

public class SearchFragment extends AbstractFragment<SearchPresenter> implements MainView.SearchView {

    private MainView mainView;
    private UserAdapter adapter;

    @BindView(R.id.search_recycler)
    RecyclerView recyclerView;

    public SearchFragment() {
    }

    public static SearchFragment newInstance(MainView mainView, SearchPresenter presenter) {
        SearchFragment searchFragment = new SearchFragment();

        searchFragment.setMainView(mainView);
        searchFragment.setPresenter(presenter);
        presenter.setView(searchFragment);

        return searchFragment;
    }

    private void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        adapter = new UserAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void showUsers(List<User> users) {
        adapter.setUser(users, user -> mainView.showProfile(user.getUuid()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) searchView = (SearchView) searchItem.getActionView();

        if (searchView != null) {
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(((AppCompatActivity) getContext()).getComponentName())
            );

            searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.isEmpty()) presenter.findUsers(newText);
                    return false;
                }
            });

            searchItem.expandActionView();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_search;
    }

}
