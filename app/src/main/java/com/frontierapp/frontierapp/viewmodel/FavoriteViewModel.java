package com.frontierapp.frontierapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.frontierapp.frontierapp.model.Connections;
import com.frontierapp.frontierapp.repository.ConnectionsRepository;
import com.google.firebase.firestore.Query;

public class FavoriteViewModel extends ViewModel {
    private MutableLiveData<Connections> connectionsMutableLiveData;
    private ConnectionsRepository connectionsRepository = new ConnectionsRepository();

    public void retrieveConnections(Query connectionsCollectionReference){
        connectionsRepository.retrieveConnections(connectionsCollectionReference);
        connectionsMutableLiveData = connectionsRepository.getConnections();
    }

    public void cancel(){
        connectionsRepository.cancel();
    }


    public MutableLiveData<Connections> getConnections(){
        return connectionsMutableLiveData;
    }
}
