package com.example.dilocker;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class FragmentPdf extends Fragment implements View.OnClickListener {

    Button add_btn;
    View view;
    RecyclerView recycler_view;
    StorageReference mStorage;
    DatabaseReference databaseReference;
    ArrayList<RestModel> dataModelArrayList;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String fuser;
    GridView gridPdfView;
    private ProgressDialog pd;
    private static final int SELECT_FILE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser().getUid();
        mStorage = FirebaseStorage.getInstance().getReference().child("postPdf");
        view = inflater.inflate(R.layout.fragment_pdf, container, false);

        gridPdfView = view.findViewById(R.id.grid_pdfview);
        add_btn = view.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("PostPdf").child(fuser);
        pd = new ProgressDialog(getActivity());
//        loadData(view);

        dataModelArrayList = new ArrayList<RestModel>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        RestModel restModel = dataSnapshot1.getValue(RestModel.class);
                        dataModelArrayList.add(restModel);

                    }
                    setdata(dataModelArrayList);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void setdata(ArrayList<RestModel> dataModelArrayList) {

        MyPdfBaseAdapter myPdfBaseAadpter  = new MyPdfBaseAdapter(getContext(),dataModelArrayList);
        gridPdfView.setAdapter(myPdfBaseAadpter);
    }

    @Override
    public void onClick(View view) {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, SELECT_FILE);
        } else {
            Toast.makeText(getActivity(), "Please enable permission", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //SAVE URI FROM GALLERY
        if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            Uri VideoURI = data.getData();

            pd.setMessage("Uploading...");
            pd.show();
//            imageHoldUri = result.getUri();


            final StorageReference filePath1 = mStorage.child(System.currentTimeMillis() + ".pdf" );
            filePath1.putFile(VideoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String downloadUrl = uri.toString();
                            RestModel restModel = new RestModel();
                            String strRe_Key = databaseReference.push().getKey();
                            restModel.setPostPdf(downloadUrl);
                            databaseReference.child(strRe_Key).setValue(restModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(), "Pdf Added..", Toast.LENGTH_SHORT).show();
                                }
                            });
//                            CollectionReference postRef = FirebaseFirestore.getInstance().collection("Post Video");
//                            postRef.add(new PostVideo(downloadUrl));
//                            Toast.makeText(getActivity(), "Added....", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getActivity(), downloadUrl, Toast.LENGTH_SHORT).show();
                            pd.dismiss();


                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    pd.setMessage(((int) progress) + "% Uploading..");
                }
            });

//            CropImage.activity(imageUri)
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(1,1)
//                    .start(this);

        }
    }
}
