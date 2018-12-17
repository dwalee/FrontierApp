package com.frontierapp.frontierapp.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Notification {
   private boolean read;
   private DocumentReference sender;
   private Date created;
   private String type;
   private DocumentReference miscellaneous_ref;
   private boolean ignore;
   private Date updated;
   private Profile profile;

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public DocumentReference getSender() {
        return sender;
    }

    public void setSender(DocumentReference sender) {
        this.sender = sender;
    }

    @ServerTimestamp
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DocumentReference getMiscellaneous_ref() {
        return miscellaneous_ref;
    }

    public void setMiscellaneous_ref(DocumentReference miscellaneous_ref) {
        this.miscellaneous_ref = miscellaneous_ref;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    @ServerTimestamp
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
