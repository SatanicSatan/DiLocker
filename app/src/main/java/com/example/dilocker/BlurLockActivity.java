package com.example.dilocker;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Directions.HideType;
import com.nightonke.blurlockview.Directions.ShowType;
import com.nightonke.blurlockview.Eases.EaseType;
import com.nightonke.blurlockview.Password;

public class BlurLockActivity extends AppCompatActivity {

    private BlurLockView blurLockView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_lock);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        blurLockView = (BlurLockView)findViewById(R.id.blurlockview);
        blurLockView.setBlurredView(imageView);

        imageView = (ImageView)findViewById(R.id.image);

        blurLockView.setCorrectPassword("7777");
        blurLockView.setLeftButton("LEFT");
        blurLockView.setRightButton("RIGHT");
        blurLockView.setTypeface(Typeface.DEFAULT);
        blurLockView.setType(Password.NUMBER,false);

        blurLockView.setOnLeftButtonClickListener(new BlurLockView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(BlurLockActivity.this, "LEFT CLICKED", Toast.LENGTH_SHORT).show();
            }
        });

        blurLockView.setOnPasswordInputListener(new BlurLockView.OnPasswordInputListener() {
            @Override
            public void correct(String inputPassword) {
                Toast.makeText(BlurLockActivity.this, "PASSWORD CORRECT", Toast.LENGTH_SHORT).show();
                blurLockView.hide(1000,HideType.FADE_OUT, EaseType.EaseInBounce);
            }

            @Override
            public void incorrect(String inputPassword) {
                Toast.makeText(BlurLockActivity.this, "PASSWORD INCORRECT", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void input(String inputPassword) {
                Toast.makeText(BlurLockActivity.this, inputPassword, Toast.LENGTH_SHORT).show();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blurLockView.show(1000,ShowType.FADE_IN, EaseType.EaseInOutBack);
            }
        });
    }
}
