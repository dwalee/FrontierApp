package com.frontierapp.frontierapp.datasource;

import android.util.Log;

import com.frontierapp.frontierapp.model.Message;
import com.frontierapp.frontierapp.model.Messages;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class MessageFirestore extends ChatFireStore {
    private static final String TAG = "MessageFirestore";
    protected static final String chatId= "Zea0FX7EXzJfxpZN9sMs";

    public void add(HashMap<String,Object> messageData){

    }

    public void update(String field, Object object){

    }

    public void remove(String messageId){

    }

    public void retrieveMessages(final OnSuccessCallback callback){
        Log.d(TAG, "retrieveMessages() called with: callback = [" + callback + "]");

        chatData.document(chatId)
                .collection("Messages")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "onSuccess() called with: queryDocumentSnapshots = [" + queryDocumentSnapshots + "]");
                        Messages messages = new Messages();
                        Log.i(TAG, "first doc: " + queryDocumentSnapshots.getDocuments().get(0).getString("message"));
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Message message = documentSnapshot.toObject(Message.class);
                            Log.i(TAG, "retrieve message = " + message.getMessage());
                            messages.add(message);
                        }
                        callback.onSuccess(messages);
                    }
                });
    }


    public interface OnSuccessCallback{
        void onSuccess(Messages messages);
    }

}
