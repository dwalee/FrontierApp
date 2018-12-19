package com.frontierapp.frontierapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.frontierapp.frontierapp.model.Space;
import com.frontierapp.frontierapp.repository.SpaceRepository;
import com.google.firebase.firestore.DocumentReference;

public class SpaceViewModel extends ViewModel {

    private SpaceRepository spaceRepository = new SpaceRepository();
    private MutableLiveData<Space> spaceMutableLiveData;


    public void retrieveSpace(DocumentReference spaceDocumentReference){
        spaceRepository.retrieveSpace(spaceDocumentReference);
        spaceMutableLiveData = spaceRepository.getSpace();
    }



    public MutableLiveData<Space> getSpace() {
        return spaceMutableLiveData;
    }


}
