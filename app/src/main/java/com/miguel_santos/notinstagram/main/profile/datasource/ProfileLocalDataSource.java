package com.miguel_santos.notinstagram.main.profile.datasource;

import android.net.Uri;

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
    public void findUser(Presenter<UserProfile> presenter) {
        Database db = Database.getInstance();
        db.findUsers(db.getUser().getUUID())
                .addOnSuccessListener((Database.OnSuccessListener<User>) user -> db.findPosts(user.getUuid())
                        .addOnSuccessListener((Database.OnSuccessListener<List<Post>>) posts -> {
                            presenter.onSuccess(new UserProfile(user, posts));
                            presenter.onComplete();
                        }));
    }

    @Override
    public void changeProfilePhoto(Uri photo, Presenter presenter) {
        Database db = Database.getInstance();
        db.addPhoto(db.getUser().getUUID(), photo)
                .addOnSuccessListener((Database.OnSuccessListener<Boolean>) presenter::onSuccess);
    }
}
