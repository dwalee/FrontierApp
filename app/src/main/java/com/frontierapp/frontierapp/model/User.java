package com.frontierapp.frontierapp.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.Objects;

public class User {
    private String id;
    protected DocumentReference user_ref;
    private String first_name;
    private String last_name;
    private Date joined;
    private Date birthdate;
    private String email;
    private String gender;
    private String password;

    public User() {
    }

    public User(String id, String first_name, String last_name, Date joined, Date birthdate, String email, String gender, String password) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.joined = joined;
        this.birthdate = birthdate;
        this.email = email;
        this.gender = gender;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @ServerTimestamp
    public Date getJoined() {
        return joined;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }

    @ServerTimestamp
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DocumentReference getUser_ref() {
        return user_ref;
    }

    public void setUser_ref(DocumentReference user_ref) {
        this.user_ref = user_ref;
    }

    public boolean sameContent(User user) {
        if (!(this.equals(user)))
            return false;

        if (user == null)
            return false;

        return  first_name.equals(user.first_name) &&
                last_name.equals(user.last_name) &&
                birthdate.equals(user.birthdate) &&
                email.equals(user.email) &&
                gender.equals(user.gender) &&
                password.equals(user.password);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof User))
            return false;

        User user = (User) obj;

        String this_path = this.user_ref.getPath();
        String that_path = user.user_ref.getPath();

        return this_path.equals(that_path);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user_ref);
    }
}
