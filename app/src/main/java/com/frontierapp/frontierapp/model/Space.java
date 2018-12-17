package com.frontierapp.frontierapp.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class Space {
    private String name;
    private String purpose;
    private Boolean is_private;
    private Date created;
    private int number_of_members;
    private DocumentReference creator;
    private Profile profile;

    public Space() {
    }

    public Space(String name, String purpose, Boolean is_private, Date created, int number_of_members, DocumentReference creator) {
        this.name = name;
        this.purpose = purpose;
        this.is_private = is_private;
        this.created = created;
        this.number_of_members = number_of_members;
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Boolean getPrivate() {
        return is_private;
    }

    public void setPrivate(Boolean aPrivate) {
        is_private = aPrivate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Boolean getIs_private() {
        return is_private;
    }

    public void setIs_private(Boolean is_private) {
        this.is_private = is_private;
    }

    public int getNumber_of_members() {
        return number_of_members;
    }

    public void setNumber_of_members(int number_of_members) {
        this.number_of_members = number_of_members;
    }

    public DocumentReference getCreator() {
        return creator;
    }

    public void setCreator(DocumentReference creator) {
        this.creator = creator;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
