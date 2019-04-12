package com.frontierapp.frontierapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.datasource.FirestoreDBReference;
import com.frontierapp.frontierapp.listeners.OnSuccessCallback;
import com.frontierapp.frontierapp.model.Space;
import com.frontierapp.frontierapp.model.Spaces;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

public class SpacesRepository implements OnSuccessCallback<Spaces> {
    private static final String TAG = "SpacesRepository";
    private MutableLiveData<Spaces> spacesMutableLiveData = new MutableLiveData<>();
    private Firestore<Space> spacesFirestore;
    private SpaceAsyncTask spaceAsyncTask;
    private final CollectionReference collectionReference = FirestoreDBReference.userCollection
            .document(Firestore.currentUserId)
            .collection(Firestore.SPACE_REFERENCES);
    private final Query query = collectionReference;
    private Context context;
    private ListenerRegistration listenerRegistration;

    public void retrieveSpaces() {
        spaceAsyncTask = new SpaceAsyncTask();
        spacesFirestore = new Firestore(query);
        spaceAsyncTask.execute(this);
    }

    public MutableLiveData<Spaces> getSpaces() {
        return spacesMutableLiveData;
    }

    @Override
    public void OnSuccess(Spaces spaces) {
        for (Space space : spaces) {
            Log.d(TAG, "OnSuccess() called before with: spaces = [" + space.getName() + "]");
        }
        spaces.sort();
        for (Space space : spaces) {
            Log.d(TAG, "OnSuccess() called after with: spaces = [" + space.getName() + "]");
        }
        spacesMutableLiveData.setValue(spaces);
    }

    private class SpaceAsyncTask extends AsyncTask<OnSuccessCallback<Spaces>, Void, Void> {
        private static final String TAG = "SpaceAsyncTask";

        @Override
        protected Void doInBackground(final OnSuccessCallback<Spaces>... onSuccessCallbacks) {
            OnSuccessCallback<Spaces> spacesOnSuccessCallback = new OnSuccessCallback<Spaces>() {
                @Override
                public void OnSuccess(final Spaces spaces) {
                    final Spaces spaceList = new Spaces();
                    final int size = spaces.size();

                    for (Space space : spaces) {

                        Log.d(TAG, "OnSuccess() called with: spaceRef = [" + space.getSpace_ref().getPath() + "]");

                        OnSuccessCallback<Space> spaceOnSuccessCallback = new OnSuccessCallback<Space>() {
                            @Override
                            public void OnSuccess(Space space) {
                                Log.d(TAG, "OnSuccess() called with: spaceName = [" + space.getName() + "]");
                                Log.d(TAG, "OnSuccess() called with: spacePath = [" + space.getSpace_ref().getPath() + "]");

                                if (spaceList.contains(space))
                                    spaceList.set(spaceList.indexOf(space), space);
                                else
                                    spaceList.add(space);

                                int current_size = spaceList.size();
                                if (current_size == size)
                                    onSuccessCallbacks[0].OnSuccess(spaceList);

                            }
                        };

                        DocumentReference spaceQuery = space.getSpace_ref();
                        Firestore<Space> spaceFirestore = new Firestore<>(spaceQuery);
                        spaceFirestore.retrieve(spaceOnSuccessCallback, Space.class);

                    }
                }
            };
            spacesFirestore.retrieveList(spacesOnSuccessCallback, Space.class, new Spaces());
            return null;
        }
    }
}
