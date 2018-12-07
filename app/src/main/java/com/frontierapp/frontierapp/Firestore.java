package com.frontierapp.frontierapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Firestore implements MyFirestoreInfo{
    protected final FirebaseAuth currentFirebaseAuth = FirebaseAuth.getInstance();
    protected final FirebaseUser currentFirebaseUser = currentFirebaseAuth.getCurrentUser();
    protected final String currentUserId = currentFirebaseUser.getUid();

    public FirebaseAuth getCurrentFirebaseAuth() {
        return currentFirebaseAuth;
    }

    public FirebaseUser getCurrentFirebaseUser() {
        return currentFirebaseUser;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }
}
