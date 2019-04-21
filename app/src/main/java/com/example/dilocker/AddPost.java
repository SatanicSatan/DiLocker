//package com.example.dilocker;
//
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.OnProgressListener;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class AddPost extends AppCompatActivity implements View.OnClickListener {
//
//    EditText title, price, description;
//    Spinner post_crop, post_district, post_taluka;
//    String crop, district, taluka;
//    Button add, cancel;
//    boolean checked;
//    FirebaseAuth mAuth;
//    String fuser,postRandomName;
//    String downloadUrl,sName;
//    LinearLayout chooseImg;
//    StorageReference mStorage;
//    DatabaseReference dbRef;
//    FirebaseFirestore db;
//    private ProgressDialog pd;
//    private static final int REQUEST_CAMERA = 3;
//    private static final int SELECT_FILE = 2;
//    private String saveCurrentDate, saveCurrentTime,profileImage;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_post);
//        title = findViewById(R.id.title);
//        price = findViewById(R.id.price);
//        description = findViewById(R.id.description);
//        post_crop = findViewById(R.id.post_crop);
//        post_district = findViewById(R.id.post_district);
//        post_taluka = findViewById(R.id.post_taluka);
//        add = findViewById(R.id.Add_Post);
//        cancel = findViewById(R.id.cancle);
//        chooseImg = findViewById(R.id.select_img);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//
//        mAuth = FirebaseAuth.getInstance();
//        fuser = mAuth.getCurrentUser().getUid();
//        mStorage = FirebaseStorage.getInstance().getReference().child("Post");
//        dbRef = FirebaseDatabase.getInstance().getReference().child("Profile").child(fuser);
//        pd = new ProgressDialog(this);
//
//
//        chooseImg.setOnClickListener(this);
//
//
//        final ArrayAdapter<String> adapter21 = new ArrayAdapter<String>(AddPost.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.productName));
//        adapter21.setDropDownViewResource(android.R.layout.simple_list_item_checked);
//        post_crop.setAdapter(adapter21);
//
//        post_crop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (adapterView.getItemAtPosition(i).equals("Select crop..")) {
//                    crop = "";
//                } else {
//                    crop = (String) adapterView.getItemAtPosition(i);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        ArrayAdapter<String> adapter22 = new ArrayAdapter<String>(AddPost.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.districList));
//        adapter22.setDropDownViewResource(android.R.layout.simple_list_item_checked);
//        post_district.setAdapter(adapter22);
//
//        post_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (adapterView.getItemAtPosition(i).equals("Select District")) {
//                    district = "";
//                } else {
//                    district = adapterView.getItemAtPosition(i).toString();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        ArrayAdapter<String> adapter23 = new ArrayAdapter<String>(AddPost.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.talukaList));
//        adapter23.setDropDownViewResource(android.R.layout.simple_list_item_checked);
//        post_taluka.setAdapter(adapter23);
//
//        post_taluka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (adapterView.getItemAtPosition(i).equals("Select Taluka")) {
//                    taluka = "";
//                } else {
//                    taluka = adapterView.getItemAtPosition(i).toString();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//            add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    dbRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            sName = dataSnapshot.child("Name").getValue().toString();
//                            profileImage = dataSnapshot.child("Image").getValue().toString();
//
//                            Calendar calFordDate = Calendar.getInstance();
//                            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
//                            saveCurrentDate = currentDate.format(calFordDate.getTime());
//
//                            Calendar calFordTime = Calendar.getInstance();
//                            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
//                            saveCurrentTime = currentTime.format(calFordTime.getTime());
//
//                            postRandomName = saveCurrentDate + saveCurrentTime;
//
//                            String sTitle = title.getText().toString().trim();
//                            String sPrice = price.getText().toString().trim();
//                            String sDescription = description.getText().toString();
//                            String radioBtn = getIntent().getStringExtra("utype");
//                            String uid = fuser;
//                            String phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().toString();
//
//                            if (sTitle.isEmpty() || sPrice.isEmpty() || crop.isEmpty() || district.isEmpty() || taluka.isEmpty()) {
////                                Snackbar.make(view, "Please fill all the data", Snackbar.LENGTH_SHORT).show();
//                                Toast.makeText(AddPost.this, "Please fill all the data..", Toast.LENGTH_SHORT).show();
//                            } else {
//                                CollectionReference postRef = FirebaseFirestore.getInstance().collection(radioBtn);
//                                postRef.add(new Post(sTitle, sPrice, sDescription, crop, district, taluka, radioBtn, uid, phone, downloadUrl, sName, saveCurrentDate, saveCurrentTime, profileImage));
//                                Toast.makeText(AddPost.this, "Post added", Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            });
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                startActivity(new Intent(AddPost.this, MainActivity.class));
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View view) {
//
//        final CharSequence[] items = {"Take Photo", "Choose from Library",
//                "Cancel"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(AddPost.this);
//        builder.setTitle("Add Photo!");
//
//        //SET ITEMS AND THERE LISTENERS
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (items[item].equals("Take Photo")) {
//                    cameraIntent();
//                } else if (items[item].equals("Choose from Library")) {
//                    galleryIntent();
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//
////        Intent intent = new Intent(Intent.ACTION_PICK);
////        intent.setType("image/*");
////        startActivityForResult(intent,GALLERY_INTENT);
//
//    }
//
//    private void cameraIntent() {
//
//        //CHOOSE CAMERA
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, REQUEST_CAMERA);
//    }
//
//    private void galleryIntent() {
//
//        //CHOOSE IMAGE FROM GALLERY
////        Log.d("gola", "entered here");
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, SELECT_FILE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        //SAVE URI FROM GALLERY
//        if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
//            Uri imageUri = data.getData();
//
//            pd.setMessage("Uploading...");
//            pd.show();
////            imageHoldUri = result.getUri();
//
//
//            final StorageReference filePath1 = mStorage.child(imageUri.getLastPathSegment());
//            filePath1.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    filePath1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//
//                            downloadUrl = uri.toString();
//                            pd.dismiss();
//
//
//                        }
//                    });
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                    pd.setMessage(((int) progress) + "% Uploading..");
//                }
//            });
//
////            CropImage.activity(imageUri)
////                    .setGuidelines(CropImageView.Guidelines.ON)
////                    .setAspectRatio(1,1)
////                    .start(this);
//
//        } else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
//            //SAVE URI FROM CAMERA
//
//            Uri imageUri = data.getData();
//
//            pd.setMessage("Uploading...");
//            pd.show();
////            imageHoldUri = result.getUri();
//
//
//            final StorageReference filePath1 = mStorage.child(mAuth.getCurrentUser().getUid() + ".jpg");
//            filePath1.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    filePath1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//
//                           downloadUrl = uri.toString();
//                        }
//                    });
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                    pd.setMessage(((int) progress) + "% Uploading..");
//                }
//            });
//
////            CropImage.activity(imageUri)
////                    .setGuidelines(CropImageView.Guidelines.ON)
////                    .setAspectRatio(1,1)
////                    .start(this);
//
//        }
//    }
//}
