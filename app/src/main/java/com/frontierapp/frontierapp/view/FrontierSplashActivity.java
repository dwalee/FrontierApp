package com.frontierapp.frontierapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.frontierapp.frontierapp.R;

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

        Intent homeIntent = new Intent(FrontierSplashActivity.this, MainAppActivity.class);
        startActivity(homeIntent);
        finish();
    }

}
