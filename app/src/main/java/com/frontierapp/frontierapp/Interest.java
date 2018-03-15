package com.frontierapp.frontierapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class Interest extends AppCompatActivity {

    private FirebaseDatabase databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        databaseUser = FirebaseDatabase.getInstance();


    }

    public void addInterest(){

        UserInformation userInformation = new UserInformation();
    }

}
