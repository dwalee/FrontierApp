package com.frontierapp.frontierapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SkillsInformationActivity extends AppCompatActivity {

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
    private FirebaseFirestore firebaseFireStore = FirebaseFirestore.getInstance();
    private Skills skills;
    private Skills dataSkills;
    private String currentUser;
    private String skillName;


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




        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        currentUser = userID.toString().trim();
        databaseUser = FirebaseDatabase.getInstance().getReference("UserInformation");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addDbSkills();
                addSkills();
                Intent pic = new Intent(SkillsInformationActivity.this, RegisterProfilePicActivity.class);
                startActivity(pic);
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
        String skill1 = skillOne.getText().toString().toLowerCase();
        String skill2 = skillTwo.getText().toString().toLowerCase();
        String skill3 = skillThree.getText().toString().toLowerCase();
        String skill4 = skillFour.getText().toString().toLowerCase();
        String skill5 = skillFive.getText().toString().toLowerCase();
        String skill6 = skillSix.getText().toString().toLowerCase();
        String skill7 = skillSeven.getText().toString().toLowerCase();


        com.google.firebase.firestore.Query skillQuery = firebaseFireStore.collection("User Information").document("Users").collection("User")
                .whereEqualTo("uid", userID);

        skills = new Skills(skill1, skill2, skill3, skill4, skill5,
                skill6, skill7);

        ArrayMap<String, Object> userInfo = new ArrayMap<>();
        userInfo.put("Skill 1", skills.getSkill1());
        userInfo.put("Skill 2", skills.getSkill2());
        userInfo.put("Skill 3", skills.getSkill3());
        userInfo.put("Skill 4", skills.getSkill4());
        userInfo.put("Skill 5", skills.getSkill5());
        userInfo.put("Skill 6", skills.getSkill6());
        userInfo.put("Skill 7", skills.getSkill7());


        firebaseFireStore.collection("UserInformation").document("Users").collection("User")
                .document(currentUser).update(userInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Successfully added skills to user");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating skills for user", e);
                    }
                });
    }

        public void addDbSkills(){

            String dbSKill1 = skillOne.getText().toString().toLowerCase();
            String dbSKill2 = skillTwo.getText().toString().toLowerCase();
            String dbSKill3 = skillThree.getText().toString().toLowerCase();
            String dbSKill4 = skillFour.getText().toString().toLowerCase();
            String dbSKill5 = skillFive.getText().toString().toLowerCase();
            String dbSKill6 = skillSix.getText().toString().toLowerCase();
            String dbSKill7 = skillSeven.getText().toString().toLowerCase();


            dataSkills = new Skills(dbSKill1, dbSKill2, dbSKill3, dbSKill4, dbSKill5,
                    dbSKill6, dbSKill7);

            ArrayMap<String, Object> skillDb  = new ArrayMap<>();
            skillDb.put("Skill", dataSkills.getSkill1());
            skillDb.put("Skill", dataSkills.getSkill2());
            skillDb.put("Skill", dataSkills.getSkill3());
            skillDb.put("Skill", dataSkills.getSkill4());
            skillDb.put("Skill", dataSkills.getSkill5());
            skillDb.put("Skill", dataSkills.getSkill6());
            skillDb.put("Skill", dataSkills.getSkill7());

            firebaseFireStore.collection("UserInformation").document("Users").collection("Skills").add(skillDb)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }}




