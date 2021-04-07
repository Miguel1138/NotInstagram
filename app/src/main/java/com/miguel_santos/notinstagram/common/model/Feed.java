package com.miguel_santos.notinstagram.common.model;

public class Feed extends Post {

    private User publisher;

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

}
