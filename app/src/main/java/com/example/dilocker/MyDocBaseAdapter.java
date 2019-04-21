package com.example.dilocker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class MyDocBaseAdapter extends BaseAdapter {
    Context context;
    ArrayList<RestModel> dataModelArrayList;

    public MyDocBaseAdapter(Context context , ArrayList<RestModel> dataModelArrayList) {

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


        convertView = layoutInflater.inflate(R.layout.item_doc_main,null);

        ImageView postDocView = convertView.findViewById(R.id.post_Doc);
        final String pdfUri = dataModelArrayList.get(position).getPostDoc();

        postDocView.setImageResource(R.drawable.doc512);

//        postDocView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,PdfViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("PDF_URI",pdfUri);
//                context.startActivity(intent);
//            }
//        });

        return convertView;
    }
}
