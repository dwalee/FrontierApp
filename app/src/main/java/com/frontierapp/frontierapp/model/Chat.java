package com.frontierapp.frontierapp.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class Chat {
    private String name;
    private Date create;
    private Date updated;
    private Profiles profiles;
    private Message message;
    private Members members;
    private DocumentReference chat_ref;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Profiles getProfiles() {
        return profiles;
    }

    public void setProfiles(Profiles profiles) {
        this.profiles = profiles;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }

    public DocumentReference getChat_ref() {
        return chat_ref;
    }

    public void setChat_ref(DocumentReference chat_ref) {
        this.chat_ref = chat_ref;
    }

    @Override
    public boolean equals(Object obj) {
        Chat chat = (Chat) obj;

        String this_path = this.chat_ref.getPath();
        String that_path = chat.chat_ref.getPath();

        return this_path.equals(that_path);
    }
}
