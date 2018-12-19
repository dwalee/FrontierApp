package com.frontierapp.frontierapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.frontierapp.frontierapp.model.Spaces;
import com.frontierapp.frontierapp.repository.SpacesRepository;

public class SpacesViewModel extends ViewModel {

    private MutableLiveData<Spaces> spacesMutableLiveData;
    private SpacesRepository spaceRepository = new SpacesRepository();

    public void retrieveSpaces(){
        spaceRepository.retrieveSpaces();
        spacesMutableLiveData = spaceRepository.getSpaces();
    }

    public MutableLiveData<Spaces> getSpaces(){
        return spacesMutableLiveData;
    }
}
