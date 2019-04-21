package com.example.dilocker;

public class Post {

    String postImage;

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }


    public Post(){

    }


    public Post(String downloadUrl) {
        this.postImage = downloadUrl;
    }

}
