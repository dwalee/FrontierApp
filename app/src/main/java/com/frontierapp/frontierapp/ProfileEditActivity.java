package com.frontierapp.frontierapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class ProfileEditActivity extends AppCompatActivity {
    AppCompatButton cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

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
}
