package com.frontierapp.frontierapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class SkillsInformation extends AppCompatActivity {

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
    private ImageButton addSkill1;
    private ImageButton addSkill2;
    private ImageButton addSkill3;
    private ImageButton addSkill4;
    private ImageButton addSkill5;
    private ImageButton addSkill6;
    private Button next;
    private FirebaseFirestore firestoredb = FirebaseFirestore.getInstance();
    private Skills skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        addSkill1 = (ImageButton) findViewById(R.id.addSkill1);
        addSkill2 = (ImageButton) findViewById(R.id.addSkill2);
        addSkill3 = (ImageButton) findViewById(R.id.addSkill3);
        addSkill4 = (ImageButton) findViewById(R.id.addSkill4);
        addSkill5 = (ImageButton) findViewById(R.id.addSkill5);
        addSkill6 = (ImageButton) findViewById(R.id.addSkill6);

        next = (Button) findViewById(R.id.next);
        skillOne = (EditText) findViewById(R.id.skillOne);
        skillTwo = (EditText) findViewById(R.id.skillTwo);
        skillThree = (EditText) findViewById(R.id.skillThree);
        skillFour = (EditText) findViewById(R.id.skillFour);
        skillFive = (EditText) findViewById(R.id.skillFive);
        skillSix = (EditText) findViewById(R.id.skillSix);
        skillSeven = (EditText) findViewById(R.id.skillSeven);

        skillTwo.setVisibility(View.GONE);
        skillThree.setVisibility(View.GONE);
        skillFour.setVisibility(View.GONE);
        skillFive.setVisibility(View.GONE);
        skillSix.setVisibility(View.GONE);
        skillSeven.setVisibility(View.GONE);

        addSkill2.setVisibility(View.GONE);
        addSkill3.setVisibility(View.GONE);
        addSkill4.setVisibility(View.GONE);
        addSkill5.setVisibility(View.GONE);
        addSkill6.setVisibility(View.GONE);

        FirebaseUser userNow = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        databaseUser = FirebaseDatabase.getInstance().getReference(userID);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSkills();
                Intent welcome = new Intent(SkillsInformation.this, Home.class);
                startActivity(welcome);
            }
        });

        addSkill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillTwo.setVisibility(View.VISIBLE);
                addSkill2.setVisibility(View.VISIBLE);
                }
        });

        addSkill2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillThree.setVisibility(View.VISIBLE);
                addSkill3.setVisibility(View.VISIBLE);
            }
        });

        addSkill3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillFour.setVisibility(View.VISIBLE);
                addSkill4.setVisibility(View.VISIBLE);
            }
        });

        addSkill4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillFive.setVisibility(View.VISIBLE);
                addSkill5.setVisibility(View.VISIBLE);
            }
        });

        addSkill5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillSix.setVisibility(View.VISIBLE);
                addSkill6.setVisibility(View.VISIBLE);
            }
        });

        addSkill6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillSeven.setVisibility(View.VISIBLE);
            }
        });
    }


    public void addSkills() {

        String name = getIntent().getExtras().getString("Name");
        String email = getIntent().getExtras().getString("Email");
        String birth_date = getIntent().getExtras().getString("Birth Date");
        String password = getIntent().getExtras().getString("Password");
        String gender = getIntent().getExtras().getString("Gender");


        String id = skills.getUid();
        String skill1 = skillOne.getText().toString().toLowerCase();
        String skill2 = skillTwo.getText().toString().toLowerCase();
        String skill3 = skillThree.getText().toString().toLowerCase();
        String skill4 = skillFour.getText().toString().toLowerCase();
        String skill5 = skillFive.getText().toString().toLowerCase();
        String skill6 = skillSix.getText().toString().toLowerCase();
        String skill7 = skillSeven.getText().toString().toLowerCase();

        skills = new Skills(id,skill1, skill2, skill3, skill4, skill5,
                skill6, skill7);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("UI", skills.getUid());
        userInfo.put("Skill Name", skills.getSkill1());
        userInfo.put("Skill Name", skills.getSkill2());
        userInfo.put("Skill Name", skills.getSkill3());
        userInfo.put("Skill Name", skills.getSkill4());
        userInfo.put("Skill Name", skills.getSkill5());
        userInfo.put("Skill Name", skills.getSkill6());
        userInfo.put("Skill Name", skills.getSkill7());

        Map<String, Object> user = new HashMap<>();

        user.put("Skills", skills);
        firestoredb.collection("Skill Repository").document("Skills")
                .collection("User").document(id).set(skills)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SkillsInformation.this, "Your Skills have been ."
                                , Toast.LENGTH_LONG)
                                .show();
                    }
                });

        //String id = databaseUser.push().getKey();

        //Skills  skills = new Skills(skill1,skill2,skill3,skill4,skill5,skill6,skill7);

        //databaseUser.child("user").child(id).setValue(skills);

        //Toast.makeText(SkillsInformation.this, "Adding User", Toast.LENGTH_LONG).show();

        }

    }
