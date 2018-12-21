package com.frontierapp.frontierapp.model;

import com.google.firebase.firestore.DocumentReference;

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
    private int member_count;
    private int project_count;

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

    public int getMember_count() {
        return member_count;
    }

    public void setMember_count(int member_count) {
        this.member_count = member_count;
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
