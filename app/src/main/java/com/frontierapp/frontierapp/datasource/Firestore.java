package com.frontierapp.frontierapp.datasource;

import android.content.Context;
import android.util.Log;

import com.frontierapp.frontierapp.listeners.OnSuccessCallback;
import com.frontierapp.frontierapp.model.Profile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

public class Firestore<T> implements FirestoreDAO<T>, FirestoreDBReference {
    private static final String TAG = "Firestore";
    public static FirebaseAuth currentFirebaseAuth;
    public static FirebaseUser currentFirebaseUser;
    public static String currentUserId;
    private Context context;

    protected DocumentReference documentReference;
    protected CollectionReference collectionReference;
    protected Query query;

    public Firestore(DocumentReference documentReference) {

        this.documentReference = documentReference;
    }

    public Firestore(CollectionReference collectionReference) {

        this.collectionReference = collectionReference;
        this.query = this.collectionReference;
    }

    public Firestore(Query query) {
        this.query = query;
    }

    @Override
    public void add(T t) {
        collectionReference
                .document()
                .set(t);
    }

    @Override
    public void add(String id, T t) {
        collectionReference
                .document(id)
                .set(t);
    }

    @Override
    public void update(String docId, HashMap<String, Object> map) {
        collectionReference
                .document(docId)
                .update(map);
    }

    @Override
    public void remove(String docId) {
        collectionReference
                .document(docId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
    }


    @Override
    public void retrieve(final OnSuccessCallback<T> callback, final Class<T> tClass) {
        documentReference
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "onEvent: ", e);
                            return;
                        }

                        T t = null;
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            t = documentSnapshot.toObject(tClass);
                            if (t instanceof Profile)
                                ((Profile) t).setThis_ref(documentSnapshot.getReference());
                            callback.OnSuccess(t);
                        }



                    }
                });
    }

    @Override
    public <S extends ArrayList<T>> void retrieveList(final OnSuccessCallback<S> callback, final Class<T> tClass, final S s) {

        ListenerRegistration listenerRegistration = query.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "onEvent: ", e);
                    return;
                }

                if (queryDocumentSnapshots != null) {
                    Log.i(TAG, "List size before = " + (s == null ? 0 : s.size()));
                    int loop_count = 0;
                    for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                        T o = null;
                        QueryDocumentSnapshot documentSnapshot = documentChange.getDocument();
                        o = documentSnapshot.toObject(tClass);
                        switch (documentChange.getType()) {
                            case MODIFIED:
                                Log.i(TAG, "DocumentModified = " + documentChange.getDocument().getString("message"));
                                Log.i(TAG, "contains new item = " + s.contains(o));
                                s.set(s.indexOf(o), o);
                                break;
                            case ADDED:
                                Log.i(TAG, "DocumentAdded = " + documentChange.getDocument().getId());
                                s.add(o);
                                break;
                            case REMOVED:
                                Log.i(TAG, "DocumentRemoved = " + documentChange.getDocument().getId());
                                Log.i(TAG, "removed = " + s.remove(o));
                                break;
                        }
                    }
                    Log.i(TAG, "List size after = " + (s == null ? 0 : s.size()));
                    callback.OnSuccess(s);
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });


    }


}
