package com.miguel_santos.notinstagram.main.profile.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.model.Post;
import com.miguel_santos.notinstagram.common.view.AbstractFragment;
import com.miguel_santos.notinstagram.main.presentation.MainView;

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
    @BindView(R.id.profile_btn_edit_profile)
    Button btnEditProfile;

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // Set the back function in the action bar.
            case android.R.id.home:
                if (!presenter.getUser().equals(FirebaseAuth.getInstance().getUid()))
                    mainView.disposeProfileDetail();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.findUser();
    }

    @Override
    public void showPhoto(String photo) {
        Glide.with(getContext()).load(photo).into(imageViewProfile);
    }

    @Override
    public void showData(String name, String follower, String following, String posts, boolean editProfile, boolean isFollowing) {
        tevUsername.setText(name);
        tevFollowerCount.setText(follower);
        tevFollowingCount.setText(following);
        tevPostsCount.setText(posts);

        // If it's your profile set the button text to edit_profile otherwise check if the user you clicked are already following
        // set the text to unfollow.
        if (editProfile) {
            btnEditProfile.setText(R.string.edit_profile);
            btnEditProfile.setTag(null);
        } else if (isFollowing) {
            btnEditProfile.setText(R.string.unfollow);
            btnEditProfile.setTag(false);
        } else {
            btnEditProfile.setText(R.string.follow);
            btnEditProfile.setTag(true);
        }
    }

    @OnClick(R.id.profile_btn_edit_profile)
    public void onButtonProfileClick() {
        if (btnEditProfile.getTag() != null) {
            Boolean follow = (Boolean) btnEditProfile.getTag();
            // Change text after click
            btnEditProfile.setText(follow ? R.string.unfollow : R.string.follow);
            btnEditProfile.setTag(!follow);

            presenter.followOrUnfollow(follow);
        } else {
            // This is where the open edit profile comes.
            Toast.makeText(getContext(), "TODO", Toast.LENGTH_LONG).show();
        }
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
