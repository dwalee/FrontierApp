package com.frontierapp.frontierapp;

public interface NotificationSender {
    Boolean sendNotification(NotificationType type, String senderId, String receiverId);
    //Boolean addNotification(Notification notification);
    Boolean updateNotification(NotificationType type, String notificationId);
    //Boolean updateNotification(Notification notification);
}
