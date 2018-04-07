package com.frontierapp.frontierapp;

import java.util.Date;

public class User {
    private String uid;
    private String first_name;
    private String last_name;
    private String password;
    private Date birthdate;
    private String gender;
    private String email;

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

    public User(String uid, String first_name, String last_name,
                String password, Date birthdate, String gender, String email) {
        this.uid = uid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.birthdate = birthdate;
        this.gender = gender;
        this.email = email;
    }

    public User() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
