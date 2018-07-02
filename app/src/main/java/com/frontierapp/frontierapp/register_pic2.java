package com.frontierapp.frontierapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class register_pic2 extends AppCompatActivity {
    private Button submit;
    private ImageView camera;
    private ArrayList<String> pathArray;
    private int array_Position;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private ProgressDialog processDialog;
    private DatabaseReference databaseReference;
    private Uri selectedImage;
    private ProgressBar progressBar;
    private DatabaseReference databaseUser;
    private FirebaseUser user;
    private FirebaseFirestore firebaseFireStore = FirebaseFirestore.getInstance();
    private String userID;
    private Upload imageurl;
    private Button choose;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pic2);

        choose = (Button) findViewById(R.id.choose);
        submit = (Button) findViewById(R.id.submit);
        pathArray = new ArrayList<>();
        processDialog = new ProgressDialog(register_pic2.this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        databaseUser = FirebaseDatabase.getInstance().getReference("UserInformation");
        storageReference = FirebaseStorage.getInstance().getReference("UserImages").child("Users");

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


                Intent welcome = new Intent(register_pic2.this, Home.class);
                startActivity(welcome);


            }
        });


    }

    private void getPhoto(){
        Intent getProfilePic = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(getProfilePic, 1);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null ) {

            Uri selectedImage = data.getData();


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                ImageView imageView = (ImageView) findViewById(R.id.camera);

                imageView.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(),"Setting selected image as profile picture",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();

            }

        }

    }


    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile(){
        if(selectedImage != null){
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()+ "." + getFileExtension(selectedImage));
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
                    Toast.makeText(getApplicationContext(),"Couldn't upload image",Toast.LENGTH_SHORT).show();
                    Upload upload = new Upload(taskSnapshot.getDownloadUrl().toString());
                    String uploadId = databaseUser.push().getKey();

                    Upload profilePic = new Upload(imageurl.toString());
                    Map<String, Object> userImage = new HashMap<>();
                    userImage.put("userAvatarUrl", profilePic);

                    Map<String, Object> userAvatar = new HashMap<>();

                    firebaseFireStore.collection("UserInformation").document("Users")
                            .collection("User").document(userID)
                            .update(userAvatar)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(register_pic2.this, "You profile pic has been uploaded."
                                            , Toast.LENGTH_LONG)
                                            .show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(register_pic2.this, "Your profile pic failed to upload!"
                                            , Toast.LENGTH_LONG)
                                            .show();
                                }
                            });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Couldn't upload image",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(),"No File Selected", Toast.LENGTH_SHORT);

        }

    }

}


