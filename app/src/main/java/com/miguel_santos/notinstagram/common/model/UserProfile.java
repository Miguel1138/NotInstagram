package com.miguel_santos.notinstagram.common.model;

import java.util.List;

public class UserProfile {

    private User user;
    private List<Post> posts;
    private boolean isFollowing;

    public UserProfile(User user, List<Post> posts, boolean isFollowing) {
        this.user = user;
        this.posts = posts;
        this.isFollowing = isFollowing;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}
