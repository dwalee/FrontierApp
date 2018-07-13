package com.frontierapp.frontierapp;

public class FollowerViewData {
    String followerName;
    String followerProfilePicUrl;
    String followerId;

    public FollowerViewData() {
    }

    public FollowerViewData(String followerName, String followerProfilePicUrl, String followerId) {
        this.followerName = followerName;
        this.followerProfilePicUrl = followerProfilePicUrl;
        this.followerId = followerId;
    }

    public String getFollowerName() {
        return followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }

    public String getFollowerProfilePicUrl() {
        return followerProfilePicUrl;
    }

    public void setFollowerProfilePicUrl(String followerProfilePicUrl) {
        this.followerProfilePicUrl = followerProfilePicUrl;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }
}
