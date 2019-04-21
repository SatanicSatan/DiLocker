package com.example.dilocker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class FragmentPic extends Fragment implements View.OnClickListener {

    Button add_btn;
    View view;
    RecyclerView recycler_view;
    StorageReference mStorage;
    FirebaseFirestore db;
    DatabaseReference databaseReference;
    ArrayList<RestModel> dataModelArrayList;
    FirebaseAuth mAuth;
    String fuser,downloadUrl;
    PostAdapter postAdapter;
    GridView gridView;
    private ProgressDialog pd;
    private CollectionReference postRef = FirebaseFirestore.getInstance().collection("Post");
    private static final int SELECT_FILE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser().getUid();
        mStorage = FirebaseStorage.getInstance().getReference().child("Post");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pic, container, false);
        gridView = view.findViewById(R.id.grid_view);
        add_btn = view.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);
        db = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("PostImage").child(fuser);
        pd = new ProgressDialog(getActivity());

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

        MyBaseAdapter myBaseAdapter = new MyBaseAdapter(getContext(),dataModelArrayList);
        gridView.setAdapter(myBaseAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
//        postAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
//        postAdapter.stopListening();
    }
    @Override
    public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_FILE);

            }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //SAVE URI FROM GALLERY
        if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            pd.setMessage("Uploading...");
            pd.show();
//            imageHoldUri = result.getUri();


            final StorageReference filePath1 = mStorage.child(imageUri.getLastPathSegment());
            filePath1.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            downloadUrl = uri.toString();

                            RestModel restModel = new RestModel();
                            String strRe_Key = databaseReference.push().getKey();
                            restModel.setPostImage(downloadUrl);
                            databaseReference.child(strRe_Key).setValue(restModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(), "Image Added..", Toast.LENGTH_SHORT).show();
                                }
                            });
//                            CollectionReference postRef = FirebaseFirestore.getInstance().collection("Post");
//                            postRef.add(new Post(downloadUrl));
//                            Toast.makeText(getActivity(), "Added....", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getActivity(), downloadUrl, Toast.LENGTH_SHORT).show();



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

//    @Override
//    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//
//        String id = documentSnapshot.getId();
//
//        DocumentReference documentReference = db.collection("Post").document(id);
//
//        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                DocumentSnapshot doc = task.getResult();
//
//                String downloadUrl = doc.get("postImage").toString();
//
//                Intent intent = new Intent(getActivity().getApplication(),FullScreenImage.class);
//                intent.putExtra("downloadUrl",downloadUrl);
//                startActivity(intent);
//            }
//        });
//    }
}
