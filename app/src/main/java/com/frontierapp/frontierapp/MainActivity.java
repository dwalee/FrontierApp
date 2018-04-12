package com.frontierapp.frontierapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private EditText userPassword, userEmail;
    private TextView registerText;
    private Button btnLogIn, btnReset;
    private String TAG;
    private ProgressDialog progressDialog;
    private CheckBox remember;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        registerText = (TextView) findViewById(R.id.registerText);
        btnReset = (Button) findViewById(R.id.btnReset);
        userEmail = (EditText) findViewById(R.id.userEmail);
        userPassword = (EditText) findViewById(R.id.userPassword);
        progressDialog = new ProgressDialog(this);
        remember = (CheckBox) findViewById(R.id.remember);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override

            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

//OnClick Listeners
        if (remember.isChecked()) {
            if (user != null) {
                //User is signed in


            }
        }

        // registerText leads to RegisterActivity

        registerText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent register = new Intent(MainActivity.this, Register.class);
                    startActivity(register);
                }
            });
        //btnReset leads to Reset Password Activity

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reset = new Intent(MainActivity.this, ResetPassword.class);
                startActivity(reset);
            }
        });

        //btnLogIn leads HomePage after email and password authorization

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = userEmail.getText().toString();
                String passwordText = userPassword.getText().toString();

                progressDialog.setMessage("Logging In");
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                                    Toast.makeText(MainActivity.this,R.string.auth_failed,Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "Sign in Successful", Toast.LENGTH_SHORT).show();
                                    Intent welcome = new Intent(MainActivity.this, Home.class);
                                    uid = mAuth.getCurrentUser().getUid();
                                    SharedPreferences sharedPreferences;
                                    Log.i("onComplete: ", "uid: " + uid);
                                    startActivity(welcome);
                                    progressDialog.dismiss();
                                }
                            }
                        });
            }
        });

    }
}