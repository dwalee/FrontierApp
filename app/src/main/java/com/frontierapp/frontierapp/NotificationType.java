package com.frontierapp.frontierapp;

public enum NotificationType {
    PARTNERSHIP_REQUEST("PARTNERSHIP_REQUEST"),
    PARTNERSHIP_ACCEPTED("PARTNERSHIP_ACCEPTED"),
    FOLLOW("FOLLOW"),
    SPACE_INVITE("SPACE_INVITE"),
    JOINED_SPACE("JOINED_SPACE");

    NotificationType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue(){
        return value;
    }
}
