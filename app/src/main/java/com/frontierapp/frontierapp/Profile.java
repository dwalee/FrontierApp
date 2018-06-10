package com.frontierapp.frontierapp;

public class Profile {
    String aboutMe;
    String userTitle;
    String city;
    String state;
    String goal;
    String profileAvatarUrl;
    String profileBackgroundUrl;

    public Profile() {
    }

    public Profile(String aboutMe, String userTitle, String city, String state,
                   String goal, String profileAvatarUrl, String profileBackgroundUrl) {
        this.aboutMe = aboutMe;
        this.userTitle = userTitle;
        this.city = city;
        this.state = state;
        this.goal = goal;
        this.profileAvatarUrl = profileAvatarUrl;
        this.profileBackgroundUrl = profileBackgroundUrl;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
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

    public String getProfileAvatarUrl() {
        return profileAvatarUrl;
    }

    public void setProfileAvatarUrl(String profileAvatarUrl) {
        this.profileAvatarUrl = profileAvatarUrl;
    }

    public String getProfileBackgroundUrl() {
        return profileBackgroundUrl;
    }

    public void setProfileBackgroundUrl(String profileBackgroundUrl) {
        this.profileBackgroundUrl = profileBackgroundUrl;
    }
}
