package com.frontierapp.frontierapp.model;

import java.util.Date;

public class User {
    private String id;
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

    public Date getJoined() {
        return joined;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }

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
}
