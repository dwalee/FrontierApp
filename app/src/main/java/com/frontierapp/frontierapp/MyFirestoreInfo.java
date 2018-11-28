package com.frontierapp.frontierapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public interface MyFirestoreInfo {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference userInformationDatabase = firebaseFirestore
            .collection("UserInformation");
}
