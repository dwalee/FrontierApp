package com.frontierapp.frontierapp.datasource;

import com.frontierapp.frontierapp.Firestore;
import com.frontierapp.frontierapp.model.Message;
import com.frontierapp.frontierapp.model.Messages;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

public class ChatFireStore extends Firestore {
    private static final String TAG = "ChatFireStore";
    protected CollectionReference chatData = userInformationDatabase
            .document("Chats")
            .collection("Chat");
    protected OnSuccessCallback onSuccessCallback;

    public void create(){

    }

    public void add(){

    }

    public void update(){

    }

    public void remove(){

    }

    public void get(String id, String collection, OnSuccessCallback callback){
        this.onSuccessCallback = callback;
        chatData.document(id).collection(collection)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Message message = queryDocumentSnapshots.getDocuments().get(0).toObject(Message.class);
                        onSuccessCallback.onSuccess(message);
                    }
                });

    }

    public void get(){

    }


    public interface OnSuccessCallback{
        void onSuccess(Message message);
    }
}


