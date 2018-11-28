package com.frontierapp.frontierapp;

import com.google.firebase.Timestamp;

import java.sql.Date;

public class NotificationViewData {
    String notificationText;
    String notificationAcceptButtonName;
    String notifcationCancelButtonName;
    String notificationImageUrl;
    String full_name;
    Timestamp notificationTimestamp;
    String notification_id;
    String sender_id;
    Boolean read;
    NotificationType notificationType;
    String miscId, miscName;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getNotificationImageUrl() {
        return notificationImageUrl;
    }

    public void setNotificationImageUrl(String notificationImageUrl) {
        this.notificationImageUrl = notificationImageUrl;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNotificationAcceptButtonName() {
        return notificationAcceptButtonName;
    }

    public void setNotificationAcceptButtonName(String notificationAcceptButtonName) {
        this.notificationAcceptButtonName = notificationAcceptButtonName;
    }

    public String getNotifcationCancelButtonName() {
        return notifcationCancelButtonName;
    }

    public void setNotifcationCancelButtonName(String notifcationCancelButtonName) {
        this.notifcationCancelButtonName = notifcationCancelButtonName;
    }

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getMiscId() {
        return miscId;
    }

    public void setMiscId(String miscId) {
        this.miscId = miscId;
    }

    public String getMiscName() {
        return miscName;
    }

    public void setMiscName(String miscName) {
        this.miscName = miscName;
    }

    public Timestamp getNotificationTimestamp() {
        return notificationTimestamp;
    }

    public void setNotificationTimestamp(Timestamp notificationTimestamp) {
        this.notificationTimestamp = notificationTimestamp;
    }
}
