package com.frontierapp.frontierapp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileEditActivity extends AppCompatActivity {
    AppCompatButton cancelButton, saveButton;
    AppCompatEditText titleEditText, aboutMeEditText, goalEditText, cityEditText,
                        stateEditText;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    Profile profile;
    UserDB userDB;
    UserFirestore userFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        instantiateViews();
        userDB = new UserDB(getApplicationContext());
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadEditTextFromSQLite();
            }
        },150);

    }

    public void instantiateViews(){
        titleEditText = (AppCompatEditText) findViewById(R.id.titleEditTextView);
        aboutMeEditText = (AppCompatEditText) findViewById(R.id.aboutMeEditTextView);
        goalEditText = (AppCompatEditText) findViewById(R.id.goalsEditTextView);
        cityEditText = (AppCompatEditText) findViewById(R.id.cityEditTextView);
        stateEditText = (AppCompatEditText) findViewById(R.id.stateEditTextView);

        saveButton = (AppCompatButton) findViewById(R.id.saveEditProfileInfoRaisedButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadEditTextValuesToFirestore();
                Intent profileIntent = new Intent(getApplicationContext(),
                        ProfileActivity.class);
                updateSQLiteFromFirestore();
                startActivity(profileIntent);
                finish();
            }
        });

        cancelButton = (AppCompatButton) findViewById(R.id.cancelEditProfileInfoRaisedButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profileIntent);
                finish();
            }
        });
    }

    public void loadEditTextFromSQLite(){

        profile = userDB.getProfileDataFromSQLite();

        Log.i("ProfileEditActivity/", "loadEditTextFromFireStore: " + profile.getUserTitle());
        titleEditText.setText(profile.getUserTitle());
        aboutMeEditText.setText(profile.getAboutMe());
        goalEditText.setText(profile.getGoal());
        cityEditText.setText(profile.getCity());
        stateEditText.setText(profile.getState());
    }

    public void uploadEditTextValuesToFirestore(){
        userFirestore = new UserFirestore();

        profile = new Profile();
        profile.setUserTitle(titleEditText.getText().toString());
        profile.setAboutMe(aboutMeEditText.getText().toString());
        profile.setGoal(goalEditText.getText().toString());
        profile.setCity(cityEditText.getText().toString());
        profile.setState(stateEditText.getText().toString());

        userFirestore.updateProfileToFireStore(currentFirebaseUser.getUid(), profile);
    }

    public void updateSQLiteFromFirestore(){
        String userId = currentFirebaseUser.getUid();
        userFirestore = new UserFirestore(getApplicationContext());
        //Log.i("ProfileEditActivity/", "loadEditTextFromFireStore: " + userId);
        userFirestore.getProfileDataFromFireStore(userId, UserDB.UPDATE);
    }

}
