package com.frontierapp.frontierapp.model;

import com.google.firebase.firestore.DocumentReference;

public class Chat_Reference {
    private DocumentReference chat_ref;

    public DocumentReference getChat_ref() {
        return chat_ref;
    }

    public void setChat_ref(DocumentReference chat_ref) {
        this.chat_ref = chat_ref;
    }
}
