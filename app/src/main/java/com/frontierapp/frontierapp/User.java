package com.frontierapp.frontierapp;

public class User {
    private String uid;
    private String first_name;
    private String last_name;
    private String password;
    private String birthdate;

    public User(String uid, String first_name, String last_name,
                String password, String birthdate) {
        this.uid = uid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.birthdate = birthdate;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
