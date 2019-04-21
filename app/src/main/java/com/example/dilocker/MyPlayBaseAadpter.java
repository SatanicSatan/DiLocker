package com.example.dilocker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyPlayBaseAadpter extends BaseAdapter {
    Context context;
    ArrayList<RestModel> dataModelArrayList;

    public MyPlayBaseAadpter(Context context , ArrayList<RestModel> dataModelArrayList) {

        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public int getCount() {
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        convertView = layoutInflater.inflate(R.layout.item_play_main,null);

        VideoView postPlayView = convertView.findViewById(R.id.post_video);
        final String videoUri = dataModelArrayList.get(position).getPostVideo().toString();
        postPlayView = convertView.findViewById(R.id.post_video);
        postPlayView.setVideoURI(Uri.parse(videoUri));
        postPlayView.seekTo( 1 );



//        postPlayView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,VideoViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("VIDEO_URI",videoUri);
//                context.startActivity(intent);
//            }
//        });

        return convertView;
    }
}
