package com.frontierapp.frontierapp;

public interface SpaceFirestoreConstants {
    //Space attributes
    String NAME = "space_name";
    String PURPOSE = "purpose";
    String IS_PRIVATE = "is_private";
    String DATE_CREATED = "date_created";

    //Member attributes
    String MEMBER_NAME = "name";
    String MEMBER_JOINED = "joined";
    String MEMBER_TYPE = "type";
    String MEMBER_PROFILE_URL = "profile_url";

    //Space subcollections titles
    String PENDING = "Pending";
    String MEMBERS = "Members";
    String FEED = "Feed";
}
