package com.frontierapp.frontierapp.datasource;

import android.content.Context;
import android.util.Log;

import com.frontierapp.frontierapp.listeners.OnSuccessCallback;
import com.frontierapp.frontierapp.model.Post;
import com.frontierapp.frontierapp.model.Profile;
import com.frontierapp.frontierapp.model.Space;
import com.google.android.gms.tasks.Task;
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
import java.util.Map;

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
    public Task<Void> add(T t) {
        documentReference = collectionReference.document();
        return documentReference
                .set(t);
    }

    @Override
    public Task<Void> add(String id, T t) {
        documentReference = collectionReference.document(id);
        return documentReference
                .set(t);
    }

    @Override
    public Task<Void> update(String docId, Map<String, Object> map) {
        documentReference = collectionReference.document(docId);
        return documentReference.update(map);
    }

    @Override
    public Task<Void> remove(String docId) {
        return collectionReference
                .document(docId)
                .delete();
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

                        T t;
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            t = documentSnapshot.toObject(tClass);
                            DocumentReference reference = documentSnapshot.getReference();
                            if (t instanceof Profile)
                                ((Profile) t).setUser_ref(reference);
                            else if (t instanceof Space)
                                ((Space) t).setSpace_ref(reference);
                            else if(t instanceof Post)
                                ((Post) t).setPost_ref(reference);

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

                    for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                        T o;
                        QueryDocumentSnapshot documentSnapshot = documentChange.getDocument();
                        DocumentReference reference = documentSnapshot.getReference();
                        o = documentSnapshot.toObject(tClass);

                        if(o instanceof Post)
                            ((Post) o).setPost_ref(reference);
                        else if (o instanceof Profile) {
                            String path_id = reference.getId();
                            String id = Firestore.currentUserId;
                            Log.i(TAG, "id and path = " + id + " " + path_id);
                            if(id.equals(path_id))
                                continue;
                            ((Profile) o).setUser_ref(reference);
                        }

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

    public DocumentReference getDocumentReference() {
        return documentReference;
    }

    public void setDocumentReference(DocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    public CollectionReference getCollectionReference() {
        return collectionReference;
    }

    public void setCollectionReference(CollectionReference collectionReference) {
        this.collectionReference = collectionReference;
    }
}
