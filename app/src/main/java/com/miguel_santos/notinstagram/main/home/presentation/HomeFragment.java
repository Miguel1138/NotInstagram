package com.miguel_santos.notinstagram.main.home.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.model.Feed;
import com.miguel_santos.notinstagram.common.view.AbstractFragment;
import com.miguel_santos.notinstagram.main.presentation.MainView;

import java.util.List;

import butterknife.BindView;

public class HomeFragment extends AbstractFragment<HomePresenter> implements MainView.HomeView {

    private MainView mainView;
    private FeedAdapter adapter;

    @BindView(R.id.home_recycler)
    RecyclerView recyclerView;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(MainView mainView, HomePresenter homePresenter) {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setPresenter(homePresenter);
        homeFragment.setMainView(mainView);
        homePresenter.setView(homeFragment);
        return homeFragment;
    }

    private void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        adapter = new FeedAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.findFeed();
    }

    @Override
    public void showProgressBar() {
        mainView.showProgressBar();
    }

    @Override
    public void hideProgressBar() {
        mainView.hideProgressBar();
    }

    @Override
    public void showFeed(List<Feed> response) {
        adapter.setFeed(response);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_home;
    }

}
