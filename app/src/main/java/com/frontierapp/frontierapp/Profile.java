package com.frontierapp.frontierapp;

public class Profile extends User {
    String about_me;
    String title;
    String city;
    String state;
    String goal;
    String profile_avatar;
    String profile_background_image_url;

    public Profile() {
    }

    public Profile(String about_me, String title, String city, String state,
                   String goal, String profile_avatar, String profile_background_image_url) {
        this.about_me = about_me;
        this.title = title;
        this.city = city;
        this.state = state;
        this.goal = goal;
        this.profile_avatar = profile_avatar;
        this.profile_background_image_url = profile_background_image_url;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getProfile_avatar() {
        return profile_avatar;
    }

    public void setProfile_avatar(String profile_avatar) {
        this.profile_avatar = profile_avatar;
    }

    public String getProfile_background_image_url() {
        return profile_background_image_url;
    }

    public void setProfile_background_image_url(String profile_background_image_url) {
        this.profile_background_image_url = profile_background_image_url;
    }
}
