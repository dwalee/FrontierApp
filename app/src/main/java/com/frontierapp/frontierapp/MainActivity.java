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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;

    private EditText userPasswordEditText, userEmailEditText;
    private TextView registerText;
    private Button btnLogIn, btnReset;
    private final String TAG = "MainActivity";
    private ProgressDialog progressDialog;
    private CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        instantiateViews();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        Log.i(TAG, "onStart: currentId = " + user);
        isUserLoggedIn(user);
    }

    public void instantiateViews() {
        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        registerText = (TextView) findViewById(R.id.registerText);
        btnReset = (Button) findViewById(R.id.btnReset);
        userEmailEditText = (EditText) findViewById(R.id.userEmail);
        userPasswordEditText = (EditText) findViewById(R.id.userPassword);
        progressDialog = new ProgressDialog(this);
        remember = (CheckBox) findViewById(R.id.remember);


        // registerText leads to RegisterActivity
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });

        //btnReset leads to Reset Password Activity
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reset = new Intent(MainActivity.this, ResetPasswordActivity.class);
                startActivity(reset);
            }
        });

        //btnLogIn leads HomePage after email and password authorization

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = userEmailEditText.getText().toString();
                String passwordText = userPasswordEditText.getText().toString();

                progressDialog.setMessage("Logging In");
                signIn(emailText, passwordText);
            }
        });
    }

    public void signIn(String email, String password) {
        if (!validateForm())
            return;

        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                            isUserLoggedIn(null);
                        } else {
                            Toast.makeText(MainActivity.this, "Sign successful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            isUserLoggedIn(user);
                        }

                        progressDialog.dismiss();
                    }
                });

    }

    private void signOut() {
        mAuth.signOut();
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = userEmailEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            userEmailEditText.setError("Required.");
            valid = false;
        } else {
            userEmailEditText.setError(null);
        }

        String password = userPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            userPasswordEditText.setError("Required.");
            valid = false;
        } else {
            userPasswordEditText.setError(null);
        }

        return valid;
    }

    public void isUserLoggedIn(FirebaseUser user) {
        if (user != null) {
            Intent welcome = new Intent(MainActivity.this, Home.class);
            startActivity(welcome);
            finish();
        } else {
            //User is not signed in
            signOut();
            return;
        }
    }
}