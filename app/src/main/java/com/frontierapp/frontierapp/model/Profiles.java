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
        String first_name = new String(profile.getFirst_name().toCharArray());
        String last_name = new String(profile.getLast_name().toCharArray());
        String profile_url = new String(profile.getProfile_url().toCharArray());

        newProfile.setThis_ref(documentReference);
        newProfile.setFirst_name(first_name);
        newProfile.setLast_name(last_name);
        newProfile.setProfile_url(profile_url);

        return newProfile;
    }

}
