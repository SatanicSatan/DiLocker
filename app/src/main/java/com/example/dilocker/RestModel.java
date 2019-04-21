package com.example.dilocker;

import android.net.Uri;

public class RestModel {

    String postImage;
    String postVideo;
    String postPdf;
    String postDoc;

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostVideo() {
        return postVideo;
    }

    public void setPostVideo(String postVideo) {
        this.postVideo = postVideo;
    }

    public void setPostPdf(String postPdf) {
        this.postPdf = postPdf;
    }

    public String getPostPdf() {
        return postPdf;
    }

    public String getPostDoc() {
        return postDoc;
    }

    public void setPostDoc(String postDoc) {
        this.postDoc = postDoc;
    }
}
