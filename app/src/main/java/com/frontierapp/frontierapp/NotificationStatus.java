package com.frontierapp.frontierapp;

public enum NotificationStatus {
    UNREAD(false),
    READ(true);

    NotificationStatus(boolean b) {
        this.b = b;
    }

    private boolean b;

    public boolean getValue(){
        return b;
    }
}
