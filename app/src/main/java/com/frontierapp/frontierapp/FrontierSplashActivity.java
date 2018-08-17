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

public class FrontierSplashActivity extends AppCompatActivity {
    ProgressBar splashProgressBar;
    FirebaseUser firebaseuser;
    private BroadcastReceiver localBroadcastReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent homeIntent = new Intent(FrontierSplashActivity.this, Home.class);
            startActivity(homeIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontier_splash);

        firebaseuser = FirebaseAuth.getInstance().getCurrentUser();

        splashProgressBar = (ProgressBar) findViewById(R.id.splashProgressBar);
        splashProgressBar.setVisibility(View.VISIBLE);

        startBackgroundService();

    }

    public void startBackgroundService() {
        Intent intent = new Intent(this, UserProfileFirestoreBackgroundService.class);
        intent.putExtra("UserId", firebaseuser.getUid());
        startService(intent);

        Intent partnerIntent = new Intent(this, UserPartnersFirestoreBackgroundService.class);
        partnerIntent.putExtra("UserId", firebaseuser.getUid());
        startService(partnerIntent);

        Intent favIntent = new Intent(this, UserFavFirestoreBackgroundService.class);
        favIntent.putExtra("UserId", firebaseuser.getUid());
        startService(favIntent);
    }


    public void stopBackgroundService(){
        Intent intent = new Intent(this, UserProfileFirestoreBackgroundService.class);
        stopService(intent);

        Intent partnerIntent = new Intent(this, UserPartnersFirestoreBackgroundService.class);
        stopService(partnerIntent);

        Intent favIntent = new Intent(this, UserFavFirestoreBackgroundService.class);
        stopService(favIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter("user.background.service.profile");
        LocalBroadcastManager.getInstance(this).registerReceiver(localBroadcastReciever,
                intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localBroadcastReciever);
    }

    @Override
    protected void onDestroy() {
        stopBackgroundService();
        super.onDestroy();
    }
}
