package com.frontierapp.frontierapp.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;

public class Connection {
    private boolean favorite;
    private boolean follower;
    private boolean partner;
    private Date created;
    private DocumentReference user_ref;
    private Profile profile;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFollower() {
        return follower;
    }

    public void setFollower(boolean follower) {
        this.follower = follower;
    }

    public boolean isPartner() {
        return partner;
    }

    public void setPartner(boolean partner) {
        this.partner = partner;
    }

    @ServerTimestamp
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Exclude
    public DocumentReference getUser_ref() {
        return user_ref;
    }

    public void setUser_ref(DocumentReference user_ref) {
        this.user_ref = user_ref;
    }

    @Exclude
    public Profile getProfile() {
        return profile;
    }


    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object obj) {
        Connection connection = (Connection) obj;
        String this_path = this.user_ref.getPath();
        String that_path = connection.user_ref.getPath();

        return this_path.equals(that_path);
    }
}
