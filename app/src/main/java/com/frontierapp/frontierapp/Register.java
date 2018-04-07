package com.frontierapp.frontierapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText userPasswordEditText, userEmailEditText, firstNameEditText, lastNameEditText, conPasswordEditText, dateOfBirthEditText;
    private Button nextButton;
    private String TAG;
    private CheckBox manGenderCheckBox, womanGenderCheckBox;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseUser;
    private FirebaseUser user;
    private FirebaseFirestore firestoreUser = FirebaseFirestore.getInstance();
    private User newUser;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);

        userPasswordEditText = (EditText) findViewById(R.id.userPasswordEditText);
        userEmailEditText = (EditText) findViewById(R.id.userEmailEditText);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        conPasswordEditText = (EditText) findViewById(R.id.conPasswordEditText);
        dateOfBirthEditText = (EditText) findViewById(R.id.dateOfBirthEditText);
        manGenderCheckBox = (CheckBox) findViewById(R.id.manGenderCheckBox);
        womanGenderCheckBox = (CheckBox) findViewById(R.id.womanGenderCheckBox);
        nextButton = (Button) findViewById(R.id.nextButton);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        databaseUser = FirebaseDatabase.getInstance().getReference("UserInformation");

        progressDialog = new ProgressDialog(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user !=null){
                    //User is signed instance of
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(Register.this, "Successfullly signed in", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Register.this, "Successfullly sign out", Toast.LENGTH_LONG).show();
                }
            }
        };

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                registerUser();
                addUser();

                progressDialog.setMessage("Registering Please Wait...");
                progressDialog.show();

            }
        });
    }

    //Unique key must be added preferably from Auth key created
    //Add User to firestore
    public void addUser(String uid){
        String emailText = userEmailEditText.getText().toString();
        String passwordText = userPasswordEditText.getText().toString();
        String conPasswordText = conPasswordEditText.getText().toString();
        String firstNameText = firstNameEditText.getText().toString();
        String lastNameText = lastNameEditText.getText().toString();
        String birthDateText = dateOfBirthEditText.getText().toString();
        String gender;
        Date birthDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            birthDate = dateFormat.parse(birthDateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (womanGenderCheckBox.isChecked()) {
            gender = "woman";
        } else {
            gender = "man";
        };

        newUser = new User(uid, firstNameText, lastNameText, passwordText, birthDate,
                gender, emailText);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("uid", newUser.getUid());
        userInfo.put("first_name", newUser.getFirst_name());
        userInfo.put("last_name", newUser.getLast_name());
        userInfo.put("email", newUser.getEmail());
        userInfo.put("birthdate", newUser.getBirthdate());
        userInfo.put("gender", newUser.getGender());
        userInfo.put("password", newUser.getPassword());

        Map<String, Object> user = new HashMap<>();

        user.put("User", userInfo);
        firestoreUser.collection("UserInformation").document("Users")
                .collection("User").document(uid)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Register.this, "You account has been created."
                        , Toast.LENGTH_LONG)
                        .show();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, "Account creation failed!"
                        , Toast.LENGTH_LONG)
                        .show();
            }
        });

    }

    // Provides user data such as name email, and birthday
   private void addUser() {

        String emailText = userEmailEditText.getText().toString();
        String passwordText = userPasswordEditText.getText().toString();
        String conPasswordText = conPasswordEditText.getText().toString();
        String firstNameText = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String fullName = firstNameText + " " + lastName;
        String birthDateText = dateOfBirthEditText.getText().toString();
        String gender;
        Date birthDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            birthDate = dateFormat.parse(birthDateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (womanGenderCheckBox.isChecked()) {
            gender = "woman";
        } else {
            gender = "man";
        };


        //Checks to see if editText boxes are empty

        if (!TextUtils.isEmpty(emailText) && !TextUtils.isEmpty(passwordText) && !TextUtils.isEmpty(conPasswordText) &&
                !TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(birthDateText) && (womanGenderCheckBox.isChecked()|| manGenderCheckBox.isChecked())){
            String id = databaseUser.push().getKey();

            UserInformation userInformation = new UserInformation(birthDateText ,passwordText, emailText, fullName,gender);

            databaseUser.child("user").child(userID).setValue(userInformation);

            Toast.makeText(Register.this, "Adding User", Toast.LENGTH_LONG).show();

        }
        if(!passwordText.equals(conPasswordText)) {
            Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_LONG).show();
        }
        if(conPasswordText.length() < 6 && passwordText.length() < 6 ) {
            Toast.makeText(Register.this, "Password length is too short", Toast.LENGTH_LONG).show();
            // Seeks data from users to put in database
        }
        if(TextUtils.isEmpty(emailText) && TextUtils.isEmpty(passwordText) && TextUtils.isEmpty(conPasswordText) &&
                TextUtils.isEmpty(fullName) && TextUtils.isEmpty(birthDateText)){
            Toast.makeText(Register.this, "Please fill out all fields", Toast.LENGTH_LONG).show();

        }

    }

    private void registerUser(){

        //Converting  editText views to strings
        String emailText = userEmailEditText.getText().toString();
        String passwordText = userPasswordEditText.getText().toString();
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //Creating a new user

        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        String newUserID = task.getResult().getUser().getUid();
                        addUser(newUserID);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Intent welcome = new Intent(Register.this, SkillsInformation.class);
                            startActivity(welcome);
                        }
                        progressDialog.dismiss();

                    }

                });


    }


    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
