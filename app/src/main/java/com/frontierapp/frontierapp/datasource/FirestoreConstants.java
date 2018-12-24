package com.frontierapp.frontierapp.datasource;

public interface FirestoreConstants {

    //User collection name
    String USERS = "Users";
    String USER = "User";

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

    //Notification types
    String PARTNERSHIP_REQUEST = "wants to partner with you.";
    String PARTNERSHIP_ACCEPTED = "agreed to a partnership.";
    String FOLLOWED = "followed you.";
    String SPACE_INVITE = "invited you to join his space";
    String JOINED_SPACE = "has joined your space";
    //String LOOKOUTS

    //Notification fields
    String READ = "read";
    String TIMESTAMP = "timestamp";
    String TYPE = "type";
    String SENDER_ID = "senderId";
    String FULL_NAME = "full_name";
    String PROFILE_URL = "profile_url";
    String MISC_ID = "misc_id";
    String MISC_NAME = "misc_name";
    String IGNORE = "ignore";

    //user Collection title
    String NOTIFICATIONS = "Notifications";
    String CHAT_REFERENCES = "Chat_References";
    String SPACE_REFERENCES = "Space_References";



    //Space attributes
    String SPACE_NAME = "space_name";
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
    String PROJECTS = "Projects";

    //Chat
    String CHATS = "Chats";
    String CHAT = "Chat";

    String SPACES = "Spaces";
    String SPACE = "Space";


}
