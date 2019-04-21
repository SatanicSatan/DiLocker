package com.example.dilocker;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends FirestoreRecyclerAdapter<Post,PostAdapter.PostHolder> {

    public OnItemClickListener listener;


    public PostAdapter(@NonNull FirestoreRecyclerOptions<Post> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostHolder holder, int position, @NonNull Post model) {

        if (model.getPostImage() == null){
            holder.postView.setImageResource(R.drawable.pic512);
        }else {
            Picasso.get().load(model.getPostImage()).placeholder(R.drawable.pic512).centerCrop().fit().into(holder.postView);
        }


    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main,
                viewGroup, false);
        return new PostHolder(v);
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        ImageView postView;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            postView = itemView.findViewById(R.id.post_Image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION &&  listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });

        }
    }

    interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}


