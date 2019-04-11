package com.frontierapp.frontierapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.frontierapp.frontierapp.view.MainAppActivity;
import com.frontierapp.frontierapp.view.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

    public class register_image_upload extends Fragment {
        private Button submit;
        private ImageView camera;
        private ArrayList<String> pathArray;
        private int array_Position;
        private StorageReference storageReference;
        private FirebaseAuth mAuth;
        private Uri selectedImage;
        private ProgressBar progressBar;
        private DatabaseReference databaseUser;
        private FirebaseUser user;
        private FirebaseFirestore firebaseFireStore = FirebaseFirestore.getInstance();
        private String userID;
        private Upload imageurl;
        private Button choose;
        private String currentUser;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_register_image_upload, container, false);



            progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
            choose = (Button) view.findViewById(R.id.choose);
            submit = (Button) view.findViewById(R.id.submit);
            pathArray = new ArrayList<>();
            //mAuth = FirebaseAuth.getInstance();
            //user = mAuth.getCurrentUser();
            //userID = user.getUid();
            //currentUser = userID.toString().trim();
            //databaseUser = FirebaseDatabase.getInstance().getReference("UserInformation");
            //storageReference = FirebaseStorage.getInstance().getReference("UserImages").child("Users");



            choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPhoto();

                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Get signed in user
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userId = user.getUid();
                    storageReference.child("UserImages/Users");
                    uploadFile();


                    Intent welcome = new Intent(getActivity(), MainAppActivity.class);
                    startActivity(welcome);


                }
            });

            return view;
        }

        private void getPhoto(){
            Intent getProfilePic = new Intent();
            getProfilePic.setType("image/*");
            getProfilePic.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(getProfilePic, 1 );
        }




        @Override
        public  void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null ) {

                selectedImage = data.getData();


                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);

                    ImageView imageView = (ImageView) getActivity().findViewById(R.id.camera);

                    imageView.setImageBitmap(bitmap);
                    Toast.makeText(getActivity().getApplicationContext(),"Setting selected image as profile picture",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();

                }

            }

        }


        private String getFileExtension(Uri uri){
            ContentResolver contentResolver = getActivity().getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return  mime.getExtensionFromMimeType(contentResolver.getType(uri));
        }

        private void uploadFile(){
            if(selectedImage != null){
                StorageReference fileReference = storageReference.child("/" + currentUser + "/" + System.currentTimeMillis()+ "." + getFileExtension(selectedImage));
                fileReference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(0);

                            }
                        },5000);
                        Toast.makeText(getActivity().getApplicationContext(),"Upload Successful",Toast.LENGTH_SHORT).show();
                        String uid = databaseUser.push().getKey();

                        Upload uploadImage = new Upload(uid,taskSnapshot.getUploadSessionUri().toString());


                        Upload profilePic = new Upload(uid, uploadImage.toString());
                        Map<String, Object> userImage = new HashMap<>();
                        userImage.put("ImageUid", uploadImage.getUid());
                        userImage.put("userAvatarUrl", uploadImage.getImageUrl());


                        firebaseFireStore.collection("UserInformation").document("Users")
                                .collection("User").document(currentUser)
                                .update(userImage)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getActivity(), "You profile pic has been uploaded."
                                                , Toast.LENGTH_LONG)
                                                .show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Your profile pic failed to upload!"
                                                , Toast.LENGTH_LONG)
                                                .show();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(),"Couldn't upload image",Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressBar.setProgress((int)progress);

                    }
                });
            }
            else{
                Toast.makeText(getActivity().getApplicationContext(),"No File Selected", Toast.LENGTH_SHORT).show();

            }

        }



}
