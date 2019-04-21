package com.example.dilocker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PinActivity extends AppCompatActivity {

    String pinView;
    TextView set_pin;
    FirebaseAuth firebaseAuth;
    String fUser;
    DatabaseReference db;
    String getPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        firebaseAuth = FirebaseAuth.getInstance();
        fUser = firebaseAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference().child(fUser);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    getPin = dataSnapshot.child("userPin").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final Pinview pinviewEdit = findViewById(R.id.pinview);
        set_pin = findViewById(R.id.set_pin);

//        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
//        final String restoredText = prefs.getString("pinValue", null);

        pinviewEdit.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                pinView = pinview.getValue();
                if (pinView.equals(getPin)){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(pinview.getWindowToken(), 0);
                    finish();
                    startActivity(new Intent(PinActivity.this,MainActivity.class));
                } else {
                    for (int i = 0;i < pinviewEdit.getPinLength();i++) {
                        pinviewEdit.onKey(pinviewEdit.getFocusedChild(), KeyEvent.KEYCODE_DEL, new KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_DEL));
                    }
                    Toast.makeText(PinActivity.this, "Please Enter valid Pin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        set_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PinActivity.this,setPinActivity.class));
            }
        });
    }
}