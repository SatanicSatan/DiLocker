package com.example.dilocker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
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


public class FragmentPlay extends Fragment implements View.OnClickListener{
    Button add_btn;
    View view;
    RecyclerView recycler_view;
    StorageReference mStorage;
    DatabaseReference databaseReference;
    ArrayList<RestModel> dataModelArrayList;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String fuser,downloadUrl;
    PostVideoAdapter postVideoAdapter;
    PostAdapter postAdapter;
    GridView gridPlayView;
    private ProgressDialog pd;
    private CollectionReference postRef = FirebaseFirestore.getInstance().collection("Post Video");
    private static final int SELECT_FILE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser().getUid();
        mStorage = FirebaseStorage.getInstance().getReference().child("postVideo");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_play, container, false);
        gridPlayView = view.findViewById(R.id.grid_playview);
        add_btn = view.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("PostVideo").child(fuser);
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

        MyPlayBaseAadpter myPlayBaseAadpter  = new MyPlayBaseAadpter(getContext(),dataModelArrayList);
        gridPlayView.setAdapter(myPlayBaseAadpter);
    }

//    private void loadData(View view) {
//        com.google.firebase.firestore.Query query = postRef.orderBy("postVideo");
//
//        FirestoreRecyclerOptions<PostVideo> options = new FirestoreRecyclerOptions.Builder<PostVideo>()
//                .setQuery(query, PostVideo.class)
//                .build();
//
//        postVideoAdapter = new PostVideoAdapter(options);
//
//        RecyclerView recyclerView = view.findViewById(R.id.recycle_video);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
//        recyclerView.setAdapter(postVideoAdapter);
//
//    }
    @Override
    public void onStart() {
        super.onStart();
//        postVideoAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
//        postVideoAdapter.stopListening();
    }
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(intent, SELECT_FILE);

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


            final StorageReference filePath1 = mStorage.child(VideoURI.getLastPathSegment());
            filePath1.putFile(VideoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            downloadUrl = uri.toString();
                            RestModel restModel = new RestModel();
                            String strRe_Key = databaseReference.push().getKey();
                            restModel.setPostVideo(downloadUrl);
                            databaseReference.child(strRe_Key).setValue(restModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(), "Video Added..", Toast.LENGTH_SHORT).show();
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
