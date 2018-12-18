package com.frontierapp.frontierapp.model;

import com.frontierapp.frontierapp.datasource.Firestore;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class Profiles extends ArrayList<Profile> {

    public static Profiles moveToNew(Profiles profileList) {
        Profiles profiles = new Profiles();
        for (Profile profile : profileList) {
            profiles.add(pointToNew(profile));
        }
        profileList.clear();
        return profiles;
    }

    public static Profile pointToNew(Profile profile){
        Profile newProfile = new Profile();
        DocumentReference documentReference = Firestore.myFirestore.document(profile.getThis_ref().getPath());
        newProfile.setThis_ref(documentReference);
        newProfile.setFirst_name(profile.getFirst_name());
        newProfile.setLast_name(profile.getLast_name());
        newProfile.setProfile_url(profile.getProfile_url());

        return newProfile;
    }

}
