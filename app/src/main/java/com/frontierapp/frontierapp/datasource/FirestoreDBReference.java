package com.frontierapp.frontierapp.datasource;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public interface FirestoreDBReference extends FirestoreConstants {
    String old_database = "UserInformation";
    String new_database = "WaveDatabase";
    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build();
    FirebaseFirestore myFirestore = FirebaseFirestore.getInstance();

    CollectionReference database = FirebaseFirestore.getInstance()
            .collection(new_database);


    DocumentReference userDocument = database.document(USERS);
    CollectionReference userCollection = userDocument.collection(USER);

    DocumentReference spacesDocument = database.document(SPACES);
    CollectionReference spaceCollection = spacesDocument.collection(SPACE);

    DocumentReference chatsDocument = database.document(CHATS);
    CollectionReference chatCollection = chatsDocument.collection(CHAT);


}
