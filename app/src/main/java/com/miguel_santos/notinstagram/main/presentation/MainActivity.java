package com.miguel_santos.notinstagram.main.presentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.view.AbstractActivity;
import com.miguel_santos.notinstagram.main.camera.presentation.AddActivity;
import com.miguel_santos.notinstagram.main.home.datasource.HomeDataSource;
import com.miguel_santos.notinstagram.main.home.datasource.HomeLocalDataSource;
import com.miguel_santos.notinstagram.main.home.presentation.HomeFragment;
import com.miguel_santos.notinstagram.main.home.presentation.HomePresenter;
import com.miguel_santos.notinstagram.main.profile.datasource.ProfileDataSource;
import com.miguel_santos.notinstagram.main.profile.datasource.ProfileLocalDataSource;
import com.miguel_santos.notinstagram.main.profile.presentation.ProfileFragment;
import com.miguel_santos.notinstagram.main.profile.presentation.ProfilePresenter;
import com.miguel_santos.notinstagram.main.search.presentation.SearchFragment;

public class MainActivity extends AbstractActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainView {

    public static final String ACT_SOURCE = "act_source";
    public static final int LOGIN_ACTIVITY = 0;
    public static final int REGISTER_ACTIVITY = 1;

    private ProfilePresenter profilePresenter;
    private HomePresenter homePresenter;

    Fragment homeFragment;
    Fragment profileFragment;
    Fragment searchFragment;
    // TODO 02/04/2021 Implementar o fragment de favoritos
    Fragment active;

    public static void launch(Context context, int source) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ACT_SOURCE, source);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            Drawable drawable = getResources().getDrawable(R.drawable.ic_insta_camera);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        BottomNavigationView bottomNav = findViewById(R.id.main_bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int source = extras.getInt(ACT_SOURCE);
            if (source == REGISTER_ACTIVITY) {
                getSupportFragmentManager().beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                scrollToolbarEnabled(true);
            }
        }
    }

    @Override
    public void scrollToolbarEnabled(boolean enabled) {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.main_appbar);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();

        if (enabled) {
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
            appBarLayout.setLayoutParams(appBarLayoutParams);
        } else {
            params.setScrollFlags(0);
            appBarLayoutParams.setBehavior(null);
            appBarLayout.setLayoutParams(appBarLayoutParams);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager manager = getSupportFragmentManager();
        if (item.getItemId() != R.id.menu_bottom_home) {
            scrollToolbarEnabled(true);
        } else {
            scrollToolbarEnabled(false);
        }

        switch (item.getItemId()) {
            case R.id.menu_bottom_home:
                manager.beginTransaction().hide(active).show(homeFragment).commit();
                active = homeFragment;
                homePresenter.findFeed();
                return true;
            case R.id.menu_bottom_search:
                manager.beginTransaction().hide(active).show(searchFragment).commit();
                active = searchFragment;
                return true;
            case R.id.menu_bottom_add:
                AddActivity.launch(this);
                return true;
            case R.id.menu_bottom_profile:
                manager.beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                profilePresenter.findUser();
                return true;
        }
        return false;
    }

    @Override
    public void showProgressBar() {
        findViewById(R.id.main_progress_bar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        findViewById(R.id.main_progress_bar).setVisibility(View.GONE);
    }

    @Override
    protected void onInject() {
        ProfileDataSource profileDataSource = new ProfileLocalDataSource();
        HomeDataSource homeDataSource = new HomeLocalDataSource();

        profilePresenter = new ProfilePresenter(profileDataSource);
        homePresenter = new HomePresenter(homeDataSource);

        homeFragment = HomeFragment.newInstance(this, homePresenter);
        profileFragment = ProfileFragment.newInstance(this, profilePresenter);
        searchFragment = SearchFragment.newInstance(this);

        active = homeFragment;

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.main_fragment, profileFragment).hide(profileFragment).commit();
        manager.beginTransaction().add(R.id.main_fragment, searchFragment).hide(searchFragment).commit();
        manager.beginTransaction().add(R.id.main_fragment, homeFragment).hide(homeFragment).commit();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

}