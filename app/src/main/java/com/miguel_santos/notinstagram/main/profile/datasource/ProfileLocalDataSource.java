package com.miguel_santos.notinstagram.main.profile.datasource;

import com.miguel_santos.notinstagram.common.model.Database;
import com.miguel_santos.notinstagram.common.model.Post;
import com.miguel_santos.notinstagram.common.model.User;
import com.miguel_santos.notinstagram.common.model.UserProfile;
import com.miguel_santos.notinstagram.common.presenter.Presenter;

import java.util.List;

public class ProfileLocalDataSource implements ProfileDataSource {

    // Padrão DTO
    // Buscando o usuário e o seus posts (dois objetos) relacionados encapsulado no UserProfile (terceiro objeto).
    @Override
    public void findUser(String uuid, Presenter<UserProfile> presenter) {
        Database db = Database.getInstance();
        db.findUser(uuid)
                .addOnSuccessListener((Database.OnSuccessListener<User>) user1 -> {
                    db.findPosts(user1.getUuid())
                            .addOnSuccessListener((Database.OnSuccessListener<List<Post>>) posts -> {
                                db.getFollowers(db.getUser().getUUID(), uuid)
                                        .addOnSuccessListener((Database.OnSuccessListener<Boolean>) isFollowing -> {
                                            presenter.onSuccess(new UserProfile(user1, posts, isFollowing));
                                            presenter.onComplete();
                                        });
                            });
                });
    }

    @Override
    public void followOrUnfollow(boolean follow, String user) {
        Database db = Database.getInstance();
        if (follow) db.followUser(db.getUser().getUUID(), user);
        else db.unfollowUser(db.getUser().getUUID(), user);
    }

}
