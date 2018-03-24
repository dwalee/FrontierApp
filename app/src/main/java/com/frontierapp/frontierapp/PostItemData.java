package com.frontierapp.frontierapp;

/**
 * Created by Yoshtown on 3/24/2018.
 */

public class PostItemData {
    private String userName;
    private int userAvatar;

    public PostItemData() {

    }

    public PostItemData(String userName, int userAvatar) {
        this.userName = userName;
        this.userAvatar = userAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(int userAvatar) {
        this.userAvatar = userAvatar;
    }
}
