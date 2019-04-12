package com.frontierapp.frontierapp.model;

import com.frontierapp.frontierapp.datasource.Firestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.Objects;

public class Space {
    private DocumentReference space_ref;
    private String name;
    private String purpose;
    private Boolean is_private;
    private Date created;
    private int number_of_members;
    private DocumentReference creator;
    private String background;
    private int project_count;

    public Space() {
    }

    //Use constructor when creating a new space
    public Space(String name, String purpose, Boolean is_private, String background) {
        this.name = name;
        this.purpose = purpose;
        this.is_private = is_private;
        this.background = background;
        this.creator = Firestore.userCollection.document(Firestore.currentUserId);
        this.number_of_members = 1;
        this.project_count = 0;
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

    public Boolean getIs_private() {
        return is_private;
    }

    public void setIs_private(Boolean is_private) {
        this.is_private = is_private;
    }

    @ServerTimestamp
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Space space = (Space) o;

        String this_path = this.space_ref.getPath();
        String that_path = space.space_ref.getPath();

        return Objects.equals(this_path, that_path);
    }

    public DocumentReference getSpace_ref() {
        return space_ref;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setSpace_ref(DocumentReference space_ref) {
        this.space_ref = space_ref;
    }

    public int getProject_count() {
        return project_count;
    }

    public void setProject_count(int project_count) {
        this.project_count = project_count;
    }

    @Override
    public int hashCode() {

        return Objects.hash(space_ref);
    }
}
