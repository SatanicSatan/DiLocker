package com.example.dilocker;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class PostVideoAdapter extends FirestoreRecyclerAdapter<PostVideo,PostVideoAdapter.PostHolder> {

    public PostVideoAdapter(@NonNull FirestoreRecyclerOptions<PostVideo> options) {
        super(options);
    }

    @NonNull
    @Override
    public PostVideoAdapter.PostHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_play_main,
                viewGroup, false);
        return new PostVideoAdapter.PostHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostHolder holder, int position, @NonNull PostVideo model) {
        //Uri VideoURI = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dilocker-da889.appspot.com/o/postVideo%2F407315271?alt=media&token=a4823d47-41c1-41d5-a41e-0442c537aac8");
        if (model.getPostVideo() == null){
            //holder.postVideo.setImageResource(R.drawable.play512);
        }else {
            //Picasso.get().load(model.getPostVideo()).placeholder(R.drawable.play512).centerCrop().fit().into(holder.postVideo);
        }
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        VideoView postVideo;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            postVideo = itemView.findViewById(R.id.post_video);
            postVideo.setVideoURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/dilocker-da889.appspot.com/o/postVideo%2F1766285039?alt=media&token=f5bb3034-73d9-46b6-b1ff-dab165a8e3ad"));
            postVideo.seekTo( 1 );                 // 1 millisecond (0.001 s) into the clip.

        }
    }
}
