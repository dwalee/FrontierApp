package com.frontierapp.frontierapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Skills extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;
    private DatabaseReference databaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText skillOne;
    private EditText skillTwo;
    private EditText skillThree;
    private EditText skillFour;
    private EditText skillFive;
    private EditText skillSix;
    private EditText skillSeven;
    private String TAG;
    private Button addSkill;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        addSkill = (Button) findViewById(R.id.addSkill);
        next = (Button) findViewById(R.id.next);
        skillOne = (EditText) findViewById(R.id.skillOne);
        skillTwo = (EditText) findViewById(R.id.skillTwo);
        skillThree = (EditText) findViewById(R.id.skillThree);
        skillFour = (EditText) findViewById(R.id.skillFour);
        skillFive = (EditText) findViewById(R.id.skillFive);
        skillSix = (EditText) findViewById(R.id.skillSix);
        skillSeven = (EditText) findViewById(R.id.skillSix);

        skillTwo.setVisibility(View.GONE);
        skillThree.setVisibility(View.GONE);
        skillFour.setVisibility(View.GONE);
        skillFive.setVisibility(View.GONE);
        skillSix.setVisibility(View.GONE);
        skillSeven.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        databaseUser = FirebaseDatabase.getInstance().getReference("UserInformation");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is signed instance of
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(Skills.this, "Successfullly Registered", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Skills.this, "Successfullly sign out", Toast.LENGTH_LONG).show();
                }
            }
        };

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilePic = new Intent(Skills.this, ProfilePic.class);
                setIntent(profilePic);
            }
        });

        addSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                if (count == 0) {
                    skillTwo.setVisibility(View.VISIBLE);
                    count += 1;
                } else if (count == 1) {
                    skillThree.setVisibility(View.VISIBLE);
                    count += 1;
                } else if (count == 2) {
                    skillFour.setVisibility(View.VISIBLE);
                    count += 1;
                } else if (count == 1) {
                    skillFive.setVisibility(View.VISIBLE);
                    count += 1;
                } else if (count == 3) {
                    skillSix.setVisibility(View.VISIBLE);
                    count += 1;
                } else if (count == 4) {
                    skillSeven.setVisibility(View.VISIBLE);
                }

            }
        });
    }


    public void addInterest() {

        String s1 = skillOne.getText().toString().toLowerCase();
        String s2 = skillTwo.getText().toString().toLowerCase();
        String s3 = skillThree.getText().toString().toLowerCase();
        String s4 = skillFour.getText().toString().toLowerCase();
        String s5 = skillFive.getText().toString().toLowerCase();
        String s6 = skillSix.getText().toString().toLowerCase();
        String s7 = skillSeven.getText().toString().toLowerCase();
        UserInformation userInformation = new UserInformation(s1, s2, s3, s4, s5, s6, s7);

    }

}
