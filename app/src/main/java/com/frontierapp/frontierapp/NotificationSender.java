package com.frontierapp.frontierapp;

public interface NotificationSender {
    Boolean sendNotification(NotificationType type, String senderId, String receiverId);
    //Boolean addNotification(Notification notification);
    Boolean updateNotification(String notificationId, Boolean ignore);
    //Boolean updateNotification(Notification notification);
}
