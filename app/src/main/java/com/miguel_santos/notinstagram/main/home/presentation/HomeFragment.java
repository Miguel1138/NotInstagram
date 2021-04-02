package com.miguel_santos.notinstagram.main.home.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.main.presentation.MainView;

public class HomeFragment extends Fragment {

    private MainView mainView;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(MainView mainView) {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setMainView(mainView);
        return  homeFragment;
    }

    private void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO: 22/02/2021 app:layout_scroll_flags="scroll" at toolbar
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.home_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(new PostAdapter());

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

    private class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

        private int[] fakeImages = new int[]{
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add
        };

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PostViewHolder(getLayoutInflater().inflate(R.layout.item_post_list, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
            holder.bind(fakeImages[position]);
        }

        @Override
        public int getItemCount() {
            return fakeImages.length;
        }
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imagePost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePost = itemView.findViewById(R.id.profile_image_grid);
        }

        public void bind(int image) {
            this.imagePost.setImageResource(image);
        }
    }

}
