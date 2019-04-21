package com.example.dilocker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
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

import java.util.HashMap;
import java.util.Map;

public class setPinActivity extends AppCompatActivity {

    String setPinView;
    FirebaseAuth firebaseAuth;
    String fUser;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);
        final Pinview setpin = findViewById(R.id.setpin);

        firebaseAuth = FirebaseAuth.getInstance();
        fUser = firebaseAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference().child(fUser);



        setpin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                setPinView = pinview.getValue();
                final Map<String, Object> hopperUpdates = new HashMap<>();
                hopperUpdates.put("userPin", setPinView);
                db.setValue(hopperUpdates);
//                SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
//                editor.putString("pinValue", setPinView);
//                editor.apply();
                Intent intent = new Intent(setPinActivity.this,PinActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

