package com.frontierapp.frontierapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileEditActivity extends AppCompatActivity {
    AppCompatButton cancelButton, saveButton;
    AppCompatEditText titleEditText, aboutMeEditText, goalEditText, cityEditText,
                        stateEditText;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    UserDB userDb;
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        instantiateViews();
        loadEditTextFromFireStore();
    }

    public void instantiateViews(){
        titleEditText = (AppCompatEditText) findViewById(R.id.titleEditTextView);
        aboutMeEditText = (AppCompatEditText) findViewById(R.id.aboutMeEditTextView);
        goalEditText = (AppCompatEditText) findViewById(R.id.goalsEditTextView);
        cityEditText = (AppCompatEditText) findViewById(R.id.cityEditTextView);
        stateEditText = (AppCompatEditText) findViewById(R.id.stateEditTextView);

        saveButton = (AppCompatButton) findViewById(R.id.saveEditProfileInfoRaisedButton);

        cancelButton = (AppCompatButton) findViewById(R.id.cancelEditProfileInfoRaisedButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getApplicationContext() , ProfileActivity.class);
                startActivity(profileIntent);
                finish();
            }
        });
    }

    public void loadEditTextFromFireStore(){

        userDb = new UserDB();
        String userId = currentFirebaseUser.getUid();
        Log.i("ProfileEditActivity/", "loadEditTextFromFireStore: " + userId);
        profile = userDb.getProfileDataFromFireStore(userId);

        Log.i("ProfileEditActivity/", "loadEditTextFromFireStore: " + profile.getUserTitle());
        titleEditText.setText(profile.getUserTitle());
        aboutMeEditText.setText(profile.getAboutMe());
        goalEditText.setText(profile.getGoal());
        cityEditText.setText(profile.getCity());
        stateEditText.setText(profile.getState());
    }
}
