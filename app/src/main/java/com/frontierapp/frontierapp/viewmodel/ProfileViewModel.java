package com.frontierapp.frontierapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.frontierapp.frontierapp.model.Profile;
import com.frontierapp.frontierapp.repository.ProfileRepository;
import com.google.firebase.firestore.DocumentReference;

public class ProfileViewModel extends ViewModel {
    private ProfileRepository profileRepository = new ProfileRepository();
    private MutableLiveData<Profile> profileMutableLiveData;

    public ProfileViewModel() {

    }

    public void retrieveProfile(DocumentReference profileDocumentReference){
        profileRepository.retrieveProfile(profileDocumentReference);
        profileMutableLiveData = profileRepository.getProfile();
    }

    public MutableLiveData<Profile> getProfile() {
        return profileMutableLiveData;
    }
}
