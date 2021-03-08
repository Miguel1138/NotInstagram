package com.miguel_santos.notinstagram.common.model;

import android.os.Handler;

import java.util.HashSet;
import java.util.Set;

public class Database {

    private static Database INSTANCE;
    private static Set<UserAuth> usersAuth;
    private static Set<User> users;

    private OnSuccessListener onSuccessListener;
    private OnFailureListener onFailureListener;
    private OnCompleteListener onCompleteListener;
    private UserAuth userAuth;

    static {
        usersAuth = new HashSet<>();
        users = new HashSet<>();

        //usersAuth.add(new UserAuth("user1@gmail.com", "1234"));
        //usersAuth.add(new UserAuth("user2@gmail.com", "12345"));
        //usersAuth.add(new UserAuth("user3@gmail.com", "123456"));
        //usersAuth.add(new UserAuth("user4@gmail.com", "1234567"));
        //usersAuth.add(new UserAuth("user5@gmail.com", "12345678"));
        //usersAuth.add(new UserAuth("user6@gmail.com", "123456789"));
    }

    // Padrão Singleton
    public static Database getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

    public Database createUser(String name, String email, String password) {
        timeOut(() -> {
            UserAuth userAuth = new UserAuth();
            userAuth.setEmail(email);
            userAuth.setPassword(password);

            usersAuth.add(userAuth);

            User user = new User();
            user.setEmail(email);
            user.setName(name);

            boolean added = users.add(user);
            if (added) {
                this.userAuth = userAuth;
                onSuccessListener.onSuccess(userAuth);
            } else {
                this.userAuth = null;
                onFailureListener.onFailure(new IllegalArgumentException("Usuário já cadastrado!"));
            }
            onCompleteListener.onComplete();
        });
        return this;
    }

    public Database login(String email, String password) {
        timeOut(() -> {
            UserAuth userAuth = new UserAuth();
            userAuth.setEmail(email);
            userAuth.setPassword(password);
            if (usersAuth.contains(userAuth)) {
                this.userAuth = userAuth;
                onSuccessListener.onSuccess(userAuth);
            } else {
                this.userAuth = null;
                onFailureListener.onFailure(new IllegalArgumentException("Usuário não encontrado! "));
            }
            onCompleteListener.onComplete();
        });
        return this;
    }

    // Simulando latencia de servidor
    private void timeOut(Runnable r) {
        new Handler().postDelayed(r, 2000);
    }

    public <T> Database addOnSuccessListener(OnSuccessListener<T> listener) {
        this.onSuccessListener = listener;
        return this;
    }

    public Database addOnFailureListener(OnFailureListener lIstener) {
        this.onFailureListener = lIstener;
        return this;
    }

    public Database addOnCompleteListener(OnCompleteListener listener) {
        this.onCompleteListener = listener;
        return this;
    }

    public interface OnSuccessListener<T> {
        void onSuccess(T response);
    }

    public interface OnFailureListener {
        void onFailure(Exception e);
    }

    public interface OnCompleteListener {
        void onComplete();
    }

}
