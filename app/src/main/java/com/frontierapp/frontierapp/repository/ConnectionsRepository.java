package com.frontierapp.frontierapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.listeners.OnSuccessCallback;
import com.frontierapp.frontierapp.model.Connection;
import com.frontierapp.frontierapp.model.Connections;
import com.frontierapp.frontierapp.model.Profile;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

public class ConnectionsRepository implements OnSuccessCallback<Connections> {
    private static final String TAG = "ConnectionsRepository";
    private MutableLiveData<Connections> connectionsMutableLiveData = new MutableLiveData<>();
    private Firestore<Connection> connectionFirestore;
    private ConnectionsAsyncTask connectionsAsyncTask;

    public ConnectionsRepository() {
    }

    public void retrieveConnections(Query connectionCollectionReference) {
        connectionsAsyncTask = new ConnectionsAsyncTask();
        connectionFirestore = new Firestore(connectionCollectionReference);
        connectionsAsyncTask.execute(this);
    }

    public MutableLiveData<Connections> getConnections() {
        return connectionsMutableLiveData;
    }

    @Override
    public void OnSuccess(Connections connections) {
        Log.i(TAG, "From repository connections = " + (connections == null ? 0 : connections.size()));
        connectionsMutableLiveData.setValue(connections);
    }

    public class ConnectionsAsyncTask extends AsyncTask<OnSuccessCallback<Connections>, Void, Void> {
        private static final String TAG = "ConnectionsAsyncTask";
        private int index = 0;

        @Override
        protected Void doInBackground(final OnSuccessCallback<Connections>... onSuccessCallbacks) {

            OnSuccessCallback<Connections> callbackConnections = new OnSuccessCallback<Connections>() {
                @Override
                public void OnSuccess(final Connections connections) {
                    Log.i(TAG, "connections = " + (connections == null ? 0 : connections.size()));
                    if (!connections.isEmpty()) {
                        final Connections connectionList = new Connections();

                        index = 0;
                        for (final Connection connection : connections) {
                            DocumentReference documentReference = connection.getUser_ref();

                            OnSuccessCallback<Profile> callback = new OnSuccessCallback<Profile>() {
                                @Override
                                public void OnSuccess(Profile profile) {
                                    connection.setProfile(profile);
                                    connectionList.add(connection);

                                    if(index == (connections.size() - 1) || connections.size() == 1)
                                        onSuccessCallbacks[0].OnSuccess(connectionList);

                                    index++;
                                }
                            };

                            Firestore<Profile> firestore = new Firestore<>(documentReference);
                            firestore.retrieve(callback, Profile.class);
                        }
                    }else{
                        onSuccessCallbacks[0].OnSuccess(connections);
                    }
                }
            };
            connectionFirestore.retrieveList(callbackConnections, Connection.class, new Connections());
            return null;
        }
    }
}
