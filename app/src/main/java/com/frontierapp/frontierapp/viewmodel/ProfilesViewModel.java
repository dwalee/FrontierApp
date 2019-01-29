package com.frontierapp.frontierapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.frontierapp.frontierapp.model.Profiles;
import com.frontierapp.frontierapp.repository.ProfilesRepository;

public class ProfilesViewModel extends ViewModel {
    private MutableLiveData<Profiles> profilesMutableLiveData;
    private ProfilesRepository profilesRepository = new ProfilesRepository();

    public void retrieveProfiles(){
        profilesRepository.retrieveProfiles();
        profilesMutableLiveData = profilesRepository.getProfiles();
    }

    public MutableLiveData<Profiles> getProfiles(){
        return profilesMutableLiveData;
    }
}
