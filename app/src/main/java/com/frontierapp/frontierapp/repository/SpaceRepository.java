package com.frontierapp.frontierapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.listeners.OnSuccessCallback;
import com.frontierapp.frontierapp.model.Space;
import com.google.firebase.firestore.DocumentReference;

public class SpaceRepository implements OnSuccessCallback<Space> {
    private MutableLiveData<Space> spaceMutableLiveData = new MutableLiveData<>();
    private Firestore spaceFirestore;
    private SpaceAsyncTask spaceAsyncTask = new SpaceAsyncTask();

    public void retrieveSpace(DocumentReference spaceDocumentReference){
        spaceFirestore = new Firestore(spaceDocumentReference);
        spaceAsyncTask.execute(this);
    }

    public MutableLiveData<Space> getSpace() {
        return spaceMutableLiveData;
    }

    @Override
    public void OnSuccess(Space space) {
        spaceMutableLiveData.setValue(space);
    }

    public class SpaceAsyncTask extends AsyncTask<OnSuccessCallback<Space>, Void, Void> {

        @Override
        protected Void doInBackground(OnSuccessCallback<Space>... onSuccessCallbacks) {
            spaceFirestore.retrieve(onSuccessCallbacks[0], Space.class);
            return null;
        }
    }
}
