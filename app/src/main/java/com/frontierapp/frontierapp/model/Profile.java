package com.frontierapp.frontierapp.model;

import java.util.Date;

public class Profile extends User {
    private String about_me;
    private String goal;
    private String profile_url;
    private String profile_background_url;
    private String city;
    private String state;
    private String title;

    public Profile() {
    }

    public Profile(String id, String first_name, String last_name, Date joined, Date birthdate, String email,
                   String gender, String password, String about_me, String goal, String profile_url, String profile_background_url,
                   String city, String state, String title) {
        super(id, first_name, last_name, joined, birthdate, email, gender, password);
        this.about_me = about_me;
        this.goal = goal;
        this.profile_url = profile_url;
        this.profile_background_url = profile_background_url;
        this.city = city;
        this.state = state;
        this.title = title;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getProfile_background_url() {
        return profile_background_url;
    }

    public void setProfile_background_url(String profile_background_url) {
        this.profile_background_url = profile_background_url;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
