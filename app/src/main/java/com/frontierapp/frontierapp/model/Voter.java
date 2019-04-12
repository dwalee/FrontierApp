package com.frontierapp.frontierapp.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Voter {
    private boolean down_vote;
    private boolean up_vote;
    private Date timestamp;
    private DocumentReference user_ref;

    public boolean isDown_vote() {
        return down_vote;
    }

    public void setDown_vote(boolean down_vote) {
        this.down_vote = down_vote;
    }

    public boolean isUp_vote() {
        return up_vote;
    }

    public void setUp_vote(boolean up_vote) {
        this.up_vote = up_vote;
    }

    @ServerTimestamp
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Exclude
    public DocumentReference getUser_ref() {
        return user_ref;
    }

    public void setUser_ref(DocumentReference user_ref) {
        this.user_ref = user_ref;
    }
}
