package com.miguel_santos.notinstagram.main.search.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList = new ArrayList<>();
    private ItemClickListener listener;

    // Method call by the showUsers method in SearchFragment.
    public void setUser(List<User> userList, ItemClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_user_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(userList.get(position));
        holder.itemView.setOnClickListener(v -> listener.onClick(userList.get(position)));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    protected static class UserViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageUser;
        private final TextView txtUsername;
        private final TextView txtName;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imageUser = itemView.findViewById(R.id.main_search_image_user);
            txtUsername = itemView.findViewById(R.id.main_search_tev_username);
            txtName = itemView.findViewById(R.id.main_search_tev_name);
        }

        public void bind(User user) {
            this.imageUser.setImageURI(user.getUri());
            this.txtUsername.setText(user.getName());
            this.txtName.setText(user.getName());
        }

    }

    interface ItemClickListener {
        void onClick(User user);
    }

}
