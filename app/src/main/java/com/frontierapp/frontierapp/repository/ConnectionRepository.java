package com.frontierapp.frontierapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.listeners.OnSuccessCallback;
import com.frontierapp.frontierapp.model.Connection;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.Map;

public class ConnectionRepository implements OnSuccessCallback<Connection> {
    private MutableLiveData<Connection> connectionMutableLiveData = new MutableLiveData<>();
    private Firestore connectionFirestore;
    private ConnectionAsyncTask connectionAsyncTask;
    private Firestore<Connection> myConnectionFirestore = new Firestore<>(Firestore.userCollection
            .document(Firestore.currentUserId)
            .collection(Firestore.CONNECTIONS));

    public void update(final String type, final DocumentReference profileDocRef, final Map<String, Object> connectionData){
        myConnectionFirestore.update(profileDocRef.getId(), connectionData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DocumentReference profileConnectionDocRef = profileDocRef.collection(Firestore.CONNECTIONS).document(Firestore.currentUserId);
                switch (type){
                    case Firestore.FAVORITE:
                        profileConnectionDocRef.update(Firestore.FOLLOWER, connectionData.get(Firestore.FAVORITE));
                        break;
                    case Firestore.PARTNER:
                        profileConnectionDocRef.update(Firestore.PARTNER, connectionData.get(Firestore.PARTNER));
                        break;

                }

            }
        });
    }

    public void retrieveConnection(DocumentReference connectionDocumentReference){
        connectionAsyncTask = new ConnectionAsyncTask();
        connectionFirestore = new Firestore(connectionDocumentReference);
        connectionAsyncTask.execute(this);
    }

    public MutableLiveData<Connection> getConnection() {
        return connectionMutableLiveData;
    }

    @Override
    public void OnSuccess(Connection connection) {
        connectionMutableLiveData.setValue(connection);
    }

    public class ConnectionAsyncTask extends AsyncTask<OnSuccessCallback<Connection>, Void, Void> {

        @Override
        protected Void doInBackground(OnSuccessCallback<Connection>... onSuccessCallbacks) {
            connectionFirestore.retrieve(onSuccessCallbacks[0], Connection.class);
            return null;
        }
    }


}
