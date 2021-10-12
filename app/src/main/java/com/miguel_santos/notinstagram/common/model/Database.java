package com.miguel_santos.notinstagram.common.model;

import android.net.Uri;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Database {

    private static Database INSTANCE;
    private static Set<UserAuth> usersAuth;
    private static Set<User> users;
    private static Set<Uri> storages;
    private static HashMap<String, HashSet<Post>> posts;
    private static HashMap<String, HashSet<Feed>> feed;
    private static HashMap<String, HashSet<String>> followers;

    private OnSuccessListener onSuccessListener;
    private OnFailureListener onFailureListener;
    private OnCompleteListener onCompleteListener;
    private static UserAuth userAuth;

    static {
        usersAuth = new HashSet<>();
        users = new HashSet<>();
        storages = new HashSet<>();
        posts = new HashMap<>();
        feed = new HashMap<>();
        followers = new HashMap<>();

//        init();
    }

    // Padrão Singleton
    public static Database getInstance() {
        return new Database();
    }

    public static void init() {
        String email = "user1@gmail.com";
        String password = "123";
        String name = "Miguel";

        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(email);
        userAuth.setPassword(password);

        usersAuth.add(userAuth);

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setUuid(userAuth.getUUID());

        users.add(user);
        Database.userAuth = userAuth;
    }

    public Database addPhoto(String uuid, Uri uri) {
        timeout(() -> {
            Set<User> users = Database.users;
            for (User user : users) {
                if (user.getUuid().equals(uuid))
                    user.setUri(uri);
            }
            storages.add(uri);
            onSuccessListener.onSuccess(true);
        });
        return this;
    }

    public Database createPost(String uuid, Uri uri, String caption) {
        timeout(() -> {
            HashMap<String, HashSet<Post>> postMap = Database.posts;
            HashSet<Post> posts = postMap.get(uuid);

            if (posts == null) {
                // Cria nova instancia de post caso não exista
                posts = new HashSet<>();
                postMap.put(uuid, posts);
            }

            Post post = new Post();
            post.setUri(uri);
            post.setCaption(caption);
            post.setTimestamp(System.currentTimeMillis());
            // id único do post
            post.setUuid(String.valueOf(post.hashCode()));
            posts.add(post);

            HashMap<String, HashSet<String>> followersMap = Database.followers;
            HashSet<String> followers = followersMap.get(uuid);

            if (followers == null) {
                followers = new HashSet<>();
                followersMap.put(uuid, followers);
            } else {
                HashMap<String, HashSet<Feed>> feedMap = Database.feed;

                // Passando o post para o feed de cada seguidor do usuário.
                for (String follower : followers) {
                    HashSet<Feed> feeds = feedMap.get(follower);

                    if (feeds != null) {
                        Feed feed = new Feed();
                        feed.setUri(post.getUri());
                        feed.setCaption(post.getCaption());
                        feed.setTimestamp(post.getTimestamp());

                        feeds.add(feed);
                    }
                }

                HashSet<Feed> myFeed = feedMap.get(uuid);
                if (myFeed != null) {
                    Feed feed = new Feed();
                    feed.setUri(post.getUri());
                    feed.setCaption(post.getCaption());
                    feed.setTimestamp(post.getTimestamp());

                    myFeed.add(feed);
                }
            }

            if (onSuccessListener != null)
                onSuccessListener.onSuccess(null);

            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });

        return this;
    }

    public Database createUser(String name, String email, String password) {
        timeout(() -> {
            UserAuth userAuth = new UserAuth();
            userAuth.setEmail(email);
            userAuth.setPassword(password);

            usersAuth.add(userAuth);

            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setUuid(userAuth.getUUID());

            boolean added = users.add(user);
            if (added) {
                Database.userAuth = userAuth;

                HashMap<String, HashSet<String>> followersMap = Database.followers;
                followersMap.put(userAuth.getUUID(), new HashSet<>());

                HashMap<String, HashSet<Feed>> feedMap = Database.feed;
                feedMap.put(userAuth.getUUID(), new HashSet<>());

                if (onSuccessListener != null)
                    onSuccessListener.onSuccess(userAuth);
            } else {
                Database.userAuth = null;
                if (onFailureListener != null)
                    onFailureListener.onFailure(new IllegalArgumentException("Usuário já cadastrado!"));
            }
            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });
        return this;
    }

    public Database login(String email, String password) {
        timeout(() -> {
            UserAuth userAuth = new UserAuth();
            userAuth.setEmail(email);
            userAuth.setPassword(password);
            if (usersAuth.contains(userAuth)) {
                Database.userAuth = userAuth;
                onSuccessListener.onSuccess(userAuth);
            } else {
                Database.userAuth = null;
                onFailureListener.onFailure(new IllegalArgumentException("Usuário não encontrado! "));
            }
            onCompleteListener.onComplete();
        });
        return this;
    }

    public UserAuth getUser() {
        return userAuth;
    }

    // Simulando latencia de servidor
    private void timeout(Runnable r) {
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

    public Database findFeed(String uuid) {
        timeout(() -> {
            HashMap<String, HashSet<Feed>> feed = Database.feed;
            HashSet<Feed> res = feed.get(uuid);

            if (res == null)
                res = new HashSet<>();

            if (onSuccessListener != null)
                onSuccessListener.onSuccess(new ArrayList<>(res));
            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });
        return this;
    }

    // Simulando comando:
    // SELECT * FROM posts p INNER JOIN users u ON p.user_id = u.id WHERE u.uuid = ?
    public Database findPosts(String uuid) {
        timeout(() -> {
            HashMap<String, HashSet<Post>> posts = Database.posts;
            HashSet<Post> res = posts.get(uuid);

            if (res == null)
                res = new HashSet<>();
            if (onSuccessListener != null)
                onSuccessListener.onSuccess(new ArrayList<>(res));
            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });
        return this;
    }

    // SIMULANDO BUSCA PELO BANCO DE DADOS
    // SELECT * FROM users WHERE uuid = ?
    public Database findUsers(String uuid) {
        timeout(() -> {
            Set<User> users = Database.users;
            User res = null;
            for (User user : users) {
                if (user.getUuid().equals(uuid)) {
                    res = user;
                    break;
                }
            }

            if (onSuccessListener != null && res != null) {
                onSuccessListener.onSuccess(res);
            } else if (onFailureListener != null) {
                onFailureListener.onFailure(new IllegalArgumentException("Usuário não encontrado"));
            }

            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });
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
