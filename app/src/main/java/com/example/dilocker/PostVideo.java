package com.example.dilocker;

public class PostVideo {

    String postVideo;

    public String getPostVideo() {
        return postVideo;
    }

    public void setPostVideo(String postVideo) {
        this.postVideo = postVideo;
    }

    PostVideo(){

    }


    public PostVideo(String downloadUrl) {
        this.postVideo = downloadUrl;
    }
}
