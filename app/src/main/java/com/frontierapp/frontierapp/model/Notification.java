package com.frontierapp.frontierapp.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.Objects;

public class Notification {
   private boolean read;
   private DocumentReference sender;
   private Date created;
   private String type;
   private DocumentReference miscellaneous_ref;
   private boolean ignore;
   private Date updated;
   private Profile profile;
   private DocumentReference notification_ref;


    public Notification() {
    }

    public Notification(boolean read, DocumentReference sender, Date created, String type,
                        DocumentReference miscellaneous_ref, boolean ignore, Date updated,
                        Profile profile, DocumentReference notification_ref) {
        this.read = read;
        this.sender = sender;
        this.created = created;
        this.type = type;
        this.miscellaneous_ref = miscellaneous_ref;
        this.ignore = ignore;
        this.updated = updated;
        this.profile = profile;
        this.notification_ref = notification_ref;
    }

    @Exclude
    public DocumentReference getNotification_ref() {
        return notification_ref;
    }

    public void setNotification_ref(DocumentReference notification_ref) {
        this.notification_ref = notification_ref;
    }

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

    @Exclude
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification notification = (Notification) o;

        String this_path = this.notification_ref.getPath();
        String that_path = notification.notification_ref.getPath();

        return Objects.equals(this_path, that_path);
    }

    public boolean sameContent(Notification notification){

        boolean bool = this.ignore == notification.ignore &&
                this.type.equals(notification.ignore);

        return bool;
    }


    @Override
    public int hashCode() {

        return Objects.hash(notification_ref);
    }
}
