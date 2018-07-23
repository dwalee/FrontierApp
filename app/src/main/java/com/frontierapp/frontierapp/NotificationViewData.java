package com.frontierapp.frontierapp;

public class NotificationViewData {
    String notificationText;
    String notificationAcceptButtonName;
    String notifcationCancelButtonName;
    String notificationImageUrl;

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
}
