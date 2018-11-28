package com.frontierapp.frontierapp;

import com.google.firebase.firestore.Query;

public interface NotificationReceiver {
    Boolean receiveNotification(String currentUserId);
    //Notification getNotification(String currentUserId);
    Query getNotifications();
}
