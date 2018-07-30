package com.frontierapp.frontierapp;

public enum NotificationType {
    PARTNERSHIP_REQUEST("Partnership Request"),
    PARTNERSHIP_ACCEPTED("Partnership Accepted"),
    FOLLOW("Follow"),
    IGNORE("IGNORE");

    NotificationType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue(){
        return value;
    }
}
