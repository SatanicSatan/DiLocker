package com.example.dilocker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyBaseAdapter extends BaseAdapter {
    Context context;
    ArrayList<RestModel> dataModelArrayList;

    public MyBaseAdapter(Context context , ArrayList<RestModel> dataModelArrayList) {

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


        convertView = layoutInflater.inflate(R.layout.item_main,null);

        ImageView postView = convertView.findViewById(R.id.post_Image);
        final String imageUri = dataModelArrayList.get(position).getPostImage().toString();

        if (dataModelArrayList.get(position).getPostImage() == null){
            postView.setImageResource(R.drawable.pic512);
        }else {
            Picasso.get().load(dataModelArrayList.get(position).getPostImage()).placeholder(R.drawable.pic512).centerCrop().fit().into(postView);
        }

        postView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,FullScreenImage.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("downloadUrl",imageUri);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
