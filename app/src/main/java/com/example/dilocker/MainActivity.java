package com.example.dilocker;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView menu,pic,doc,play,pdf,note,logout;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActionBar actionBar = getSupportActionBar();
//        View viewActionBar = getLayoutInflater().inflate(R.layout.activity_main, null);
//        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
//                ActionBar.LayoutParams.WRAP_CONTENT,
//                ActionBar.LayoutParams.MATCH_PARENT,
//                Gravity.CENTER);
//        actionBar.setCustomView(viewActionBar,params);
        menu = findViewById(R.id.ic_menu);
        pic = findViewById(R.id.ic_pic);
        doc = findViewById(R.id.ic_doc);
        play = findViewById(R.id.ic_play);
        note = findViewById(R.id.ic_note);
        pdf = findViewById(R.id.ic_pdf);
        logout = findViewById(R.id.ic_logout);

        fAuth = FirebaseAuth.getInstance();

        menu.setOnClickListener(this);
        pic.setOnClickListener(this);
        doc.setOnClickListener(this);
        play.setOnClickListener(this);
        note.setOnClickListener(this);
        pdf.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;

        if (v == menu){
            fragment = new FragmentMenu();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment);
            fragmentTransaction.commit();

        } else if (v == pic){
            fragment = new FragmentPic();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment);
            fragmentTransaction.commit();

        } else if (v == doc){
            fragment = new FragmentDoc();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment);
            fragmentTransaction.commit();
        } else if (v == play){
            fragment = new FragmentPlay();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment);
            fragmentTransaction.commit();
        } else if (v == note){
            fragment = new FragmentNote();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment);
            fragmentTransaction.commit();
        } else if (v == pdf){
            fragment = new FragmentPdf();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment);
            fragmentTransaction.commit();
        } else if (v == logout){
            fAuth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this,OtpActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }
}
