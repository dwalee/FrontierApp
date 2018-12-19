package com.frontierapp.frontierapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.listeners.OnSuccessCallback;
import com.frontierapp.frontierapp.model.Connection;
import com.google.firebase.firestore.DocumentReference;

public class ConnectionRepository implements OnSuccessCallback<Connection> {
    private MutableLiveData<Connection> connectionMutableLiveData = new MutableLiveData<>();
    private Firestore connectionFirestore;
    private ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask();

    public void retrieveConnection(DocumentReference connectionDocumentReference){
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
