package com.miguel_santos.notinstagram.main.profile.presentation;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.components.CustomDialog;
import com.miguel_santos.notinstagram.common.model.Post;
import com.miguel_santos.notinstagram.common.view.AbstractFragment;
import com.miguel_santos.notinstagram.main.presentation.MainView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends AbstractFragment<ProfilePresenter> implements MainView.ProfileView {

    private PostAdapter postAdapter;
    private MainView mainView;

    @BindView(R.id.profile_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.profile_image_icon)
    CircleImageView imageViewProfile;
    @BindView(R.id.profile_tev_username)
    TextView tevUsername;
    @BindView(R.id.profile_tev_followers_count)
    TextView tevFollowerCount;
    @BindView(R.id.profile_tev_following_count)
    TextView tevFollowingCount;
    @BindView(R.id.profile_tev_post_count)
    TextView tevPostsCount;
    @BindView(R.id.profile_navigation_tabs)
    BottomNavigationView bottomNavigationView;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(MainView mainView, ProfilePresenter profilePresenter) {
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setPresenter(profilePresenter);
        profileFragment.setMainView(mainView);
        profilePresenter.setView(profileFragment);
        return profileFragment;
    }

    private void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        postAdapter = new PostAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(postAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_profile_grid:
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    return true;
                case R.id.menu_profile_list:
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    return true;
            }

            return false;
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.findUser();
    }

    @Override
    public void showPhoto(Uri photo) {
        try {
            if (getContext() != null && getContext().getContentResolver() != null) {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photo);

                imageViewProfile.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showData(String name, String follower, String following, String posts) {
        tevUsername.setText(name);
        tevFollowerCount.setText(follower);
        tevFollowingCount.setText(following);
        tevPostsCount.setText(posts);
    }

    @Override
    public void showPosts(List<Post> posts) {
        postAdapter.setPosts(posts);
        postAdapter.notifyDataSetChanged();
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
    protected int getLayout() {
        return R.layout.fragment_main_profile;
    }
}
