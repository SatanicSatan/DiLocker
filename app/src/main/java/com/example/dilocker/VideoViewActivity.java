package com.example.dilocker;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;
import bg.devlabs.fullscreenvideoview.orientation.LandscapeOrientation;
import bg.devlabs.fullscreenvideoview.orientation.PortraitOrientation;

public class VideoViewActivity extends AppCompatActivity {

    FullscreenVideoView fullscreenVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        String videoUrl = getIntent().getStringExtra("VIDEO_URI");
        Toast.makeText(this, videoUrl, Toast.LENGTH_SHORT).show();
        fullscreenVideoView.videoUrl(videoUrl);
    }
}
