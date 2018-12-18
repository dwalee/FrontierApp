package com.frontierapp.frontierapp.model;

import com.frontierapp.frontierapp.datasource.Firestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.Date;

public class Message {
    private String message;
    private Date sent;
    private Boolean read;
    private Date edited;
    private DocumentReference sender;
    private Profile profile;

    public Message() {
    }

    public Message(String message, Date sent, Boolean read, Date edited, DocumentReference sender, Profile profile) {
        this.message = message;
        this.sent = sent;
        this.read = read;
        this.edited = edited;
        this.sender = sender;
        this.profile = profile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @ServerTimestamp
    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    @ServerTimestamp
    public Date getEdited() {
        return edited;
    }

    public void setEdited(Date edited) {
        this.edited = edited;
    }

    public DocumentReference getSender() {
        return sender;
    }

    public void setSender(DocumentReference sender) {
        this.sender = sender;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object obj) {
        Message message = (Message) obj;
        Date this_sent = this.sent;
        long this_time = this_sent.getTime();

        Date that_sent = message.sent;
        long that_time = that_sent.getTime();

        String this_path = this.sender.getPath();
        String that_path = message.sender.getPath();

        return this_time == that_time && this_path.equals(that_path);
    }
}
