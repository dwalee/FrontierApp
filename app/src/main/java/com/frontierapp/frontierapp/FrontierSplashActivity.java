package com.frontierapp.frontierapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FrontierSplashActivity extends AppCompatActivity{
    ProgressBar splashProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontier_splash);

        splashProgressBar = (ProgressBar) findViewById(R.id.splashProgressBar);
        splashProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent homeIntent = new Intent(FrontierSplashActivity.this, Home.class);
        startActivity(homeIntent);
        finish();
    }

}
