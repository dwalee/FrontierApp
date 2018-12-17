package com.frontierapp.frontierapp.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class Member {
    private Date joined;
    private DocumentReference member;

    public Date getJoined() {
        return joined;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }

    public DocumentReference getMember() {
        return member;
    }

    public void setMember(DocumentReference member) {
        this.member = member;
    }
}
