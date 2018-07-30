package com.frontierapp.frontierapp;

public interface NotificationSender {
    Boolean sendNotification(NotificationType type, String senderId, String receiverId);
    Boolean updateNotification(NotificationType type, String notificationId);
}
