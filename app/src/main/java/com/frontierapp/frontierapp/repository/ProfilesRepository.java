package com.frontierapp.frontierapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.datasource.FirestoreDBReference;
import com.frontierapp.frontierapp.listeners.OnSuccessCallback;
import com.frontierapp.frontierapp.model.Profile;
import com.frontierapp.frontierapp.model.Profiles;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

public class ProfilesRepository implements OnSuccessCallback<Profiles> {
    private MutableLiveData<Profiles> profilesMutableLiveData = new MutableLiveData<>();
    private Firestore<Profile> profileFirestore;
    private ProfileAsyncTask profileAsyncTask;
    private final CollectionReference collectionReference = FirestoreDBReference.userCollection;
    private final Query query = collectionReference.orderBy("first_name", Query.Direction.ASCENDING);

    public void retrieveProfiles() {
        profileAsyncTask = new ProfileAsyncTask();
        profileFirestore = new Firestore(query);
        profileAsyncTask.execute(this);
    }

    public MutableLiveData<Profiles> getProfiles() {
        return profilesMutableLiveData;
    }

    @Override
    public void OnSuccess(Profiles profiles) {
        //profiles.sort();
        profilesMutableLiveData.setValue(profiles);
    }

    private class ProfileAsyncTask extends AsyncTask<OnSuccessCallback<Profiles>, Void, Void> {
        private static final String TAG = "ProfileAsyncTask";
        private int index = 0;

        @Override
        protected Void doInBackground(final OnSuccessCallback<Profiles>... onSuccessCallbacks) {
            OnSuccessCallback<Profiles> profilesOnSuccessCallback = new OnSuccessCallback<Profiles>() {
                @Override
                public void OnSuccess(final Profiles profiles) {

                    onSuccessCallbacks[0].OnSuccess(profiles);
                }
            };

            profileFirestore.retrieveList(profilesOnSuccessCallback, Profile.class, new Profiles());
            return null;
        }
    }
}
