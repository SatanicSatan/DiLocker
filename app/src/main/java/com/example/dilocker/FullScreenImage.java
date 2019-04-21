package com.example.dilocker;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FullScreenImage extends AppCompatActivity {

    ImageView fullImage;
    ProgressDialog pd;
    String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait");
        pd.show();

        downloadUrl = getIntent().getStringExtra("downloadUrl");
        fullImage = findViewById(R.id.fullimage);

        init();

    }

    private void init() {
        Picasso.get().load(downloadUrl).fit().into(fullImage);
        pd.dismiss();
    }
}
