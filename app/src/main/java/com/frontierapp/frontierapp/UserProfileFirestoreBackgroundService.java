package com.frontierapp.frontierapp;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class UserProfileFirestoreBackgroundService extends Service {
    private static final String TAG = "ProfileBGService";
    private UserFirestore userFirestore;
    private CurrentPartnersFirestore currentPartnersFirestore;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String userid = intent.getStringExtra("UserId");

        new FirestoreAsyncTask().execute(userid);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    public void downloadUserProfileFromFirestore(String userId){
        userFirestore = new UserFirestore(getApplicationContext());
        userFirestore.getUserProfileDataFromFirestore(userId);
    }

    class FirestoreAsyncTask extends AsyncTask<String, String, Void> {
        private static final String TAG = "FirestoreAsyncTask";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "onPreExecute, Thread name: " + Thread.currentThread().getName() );
        }

        @Override
        protected Void doInBackground(String... str) {
            downloadUserProfileFromFirestore(str[0]);
            //currentPartnersFirestore = new CurrentPartnersFirestore(getApplicationContext());
            //currentPartnersFirestore.getPartnersIDsFromFireStore(str[0]);
            Log.i(TAG, "doInBackground, Thread name: " + Thread.currentThread().getName() );
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.i(TAG, "onProgressUpdate, Thread name: " + Thread.currentThread().getName() );
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i(TAG, "onPostExecute, Thread name: " + Thread.currentThread().getName() );
            super.onPostExecute(aVoid);
        }
    }
}
