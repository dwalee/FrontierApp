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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText userPassword, userEmail, name, conPassword, dateOfBirth;
    private Button next;
    private String TAG;
    private CheckBox man, woman;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseUser;
    private FirebaseUser user;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);

        userPassword = (EditText) findViewById(R.id.userPassword);
        userEmail = (EditText) findViewById(R.id.userEmail);
        name = (EditText) findViewById(R.id.name);
        conPassword = (EditText) findViewById(R.id.conPassword);
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        man = (CheckBox) findViewById(R.id.man);
        woman = (CheckBox) findViewById(R.id.woman);
        next = (Button) findViewById(R.id.next);


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

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                registerUser();
                addUser();

                progressDialog.setMessage("Registering Please Wait...");
                progressDialog.show();

            }
        });
    }

    // Provides user data such as name email, and birthday
    private void addUser() {

        String emailText = userEmail.getText().toString();
        String passwordText = userPassword.getText().toString();
        String conPasswordText = conPassword.getText().toString();
        String nameText = name.getText().toString();
        String birthDate = dateOfBirth.getText().toString();
        String gender;

        if (woman.isChecked()) {
            gender = "woman";
        } else {
            gender = "man";
        };


        //Checks to see if editText boxes are empty

        if (!TextUtils.isEmpty(emailText) && !TextUtils.isEmpty(passwordText) && !TextUtils.isEmpty(conPasswordText) &&
                !TextUtils.isEmpty(nameText) && !TextUtils.isEmpty(birthDate) && (woman.isChecked()|| man.isChecked())){
            String id = databaseUser.push().getKey();

            UserInformation userInformation = new UserInformation(nameText, emailText,passwordText,birthDate,gender);

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
                TextUtils.isEmpty(nameText) && TextUtils.isEmpty(birthDate)){
            Toast.makeText(Register.this, "Please fill out all fields", Toast.LENGTH_LONG).show();

        }

    }

    private void registerUser(){

        //Converting  editText views to strings
        String emailText = userEmail.getText().toString();
        String passwordText = userPassword.getText().toString();

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //Creating a new user

        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Intent welcome = new Intent(Register.this, Skills.class);
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
