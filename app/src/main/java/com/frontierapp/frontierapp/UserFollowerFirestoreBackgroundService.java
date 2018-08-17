package com.frontierapp.frontierapp;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class UserFollowerFirestoreBackgroundService extends IntentService {
    private static final String TAG = "FollowerFirestoreBGSrv";
    private CurrentPartnersFirestore currentPartnersFirestore;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public UserFollowerFirestoreBackgroundService() {
        super("UserFollowerFirestoreBackgroundThread");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String id = intent.getStringExtra("UserId");
        Log.i(TAG, "onHandleIntent: notification_id = " + id );
        currentPartnersFirestore = new CurrentPartnersFirestore(getApplicationContext());
        currentPartnersFirestore.getFollowersIDsFromFireStore(id);
    }

}
