package com.frontierapp.frontierapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.frontierapp.frontierapp.listeners.OnSuccessCallback;
import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.model.Profile;
import com.google.firebase.firestore.DocumentReference;

public class ProfileRepository implements OnSuccessCallback<Profile> {
    private MutableLiveData<Profile> profileMutableLiveData = new MutableLiveData<>();
    private Firestore profileFirestore;
    private ProfileAsyncTask profileAsyncTask;

    public ProfileRepository() {
    }

    public void retrieveProfile(DocumentReference profileDocumentReference){
        profileAsyncTask = new ProfileAsyncTask();
        profileFirestore = new Firestore(profileDocumentReference);
        profileAsyncTask.execute(this);
    }

    public MutableLiveData<Profile> getProfile() {
        return profileMutableLiveData;
    }

    @Override
    public void OnSuccess(Profile profile) {
        profileMutableLiveData.setValue(profile);
    }

    public class ProfileAsyncTask extends AsyncTask<OnSuccessCallback<Profile>, Void, Void>{

        @Override
        protected Void doInBackground(OnSuccessCallback<Profile>... onSuccessCallbacks) {
            profileFirestore.retrieve(onSuccessCallbacks[0], Profile.class);
            return null;
        }
    }
}
