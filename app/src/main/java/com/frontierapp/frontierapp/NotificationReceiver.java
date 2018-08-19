package com.frontierapp.frontierapp;

public interface NotificationReceiver {
    Boolean receiveNotification(String currentUserId);
    //Notification getNotification(String currentUserId);
    Notifications getNotifications();
}
