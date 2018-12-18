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

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;

        if(!(obj instanceof Notification))
            return false;

        Notification notification = (Notification) obj;
        String this_sender_path = this.sender.getPath();
        String that_sender_path = notification.getSender().getPath();

        String this_misc_path = "";
        if(this.miscellaneous_ref != null)
            this_misc_path = this.miscellaneous_ref.getPath();

        String that_misc_path = "";
        if(notification.getMiscellaneous_ref() != null)
            that_misc_path = notification.getMiscellaneous_ref().getPath();

        Boolean b = this_sender_path.equals(that_sender_path) &&
        this_misc_path.equals(that_misc_path) &&
        this.created.getTime() == notification.getCreated().getTime() &&
        this.type.equals(notification.getType());

        return b;
    }
}
