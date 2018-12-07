package com.frontierapp.frontierapp;

public interface UserFirestoreConstants {
    //User title
    String FIRST_NAME = "User.first_name";
    String LAST_NAME = "User.last_name";
    String EMAIL = "User.email";
    String BIRTHDATE = "User.birthdate";
    String GENDER = "User.gender";
    String PASSWORD = "User.password";

    //Profile title
    String ABOUT_ME = "Profile.about_me";
    String CITY = "Profile.city";
    String STATE = "Profile.state";
    String GOAL = "Profile.goal";
    String PROFILE_AVATAR = "Profile.profile_avatar";
    String PROFILE_BACKGROUND = "Profile.profile_background_image_url";
    String PROFILE_TITLE = "Profile.title";

    //User Collection titles
    String CONNECTIONS = "Connections";

    //Connections fields
    String FAVORITE = "favorite";
    String FOLLOWER = "follower";
    String PARTNER = "partner";
    String CONNECTIONS_FIRST_NAME = "first_name";
    String CONNECTIONS_LAST_NAME = "last_name";
    String CONNECTIONS_PROFILE_URL = "profile_url";
    String CONNECTIONS_TITLE = "title";
}
