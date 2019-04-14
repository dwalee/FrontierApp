package com.frontierapp.frontierapp.util;

import android.util.Log;

import com.frontierapp.frontierapp.NotificationType;
import com.frontierapp.frontierapp.model.Notification;

import static com.frontierapp.frontierapp.datasource.FirestoreConstants.*;

public class NotificationUtil {
    private static final String TAG = "NotificationUtil";

    public static String getTypeString(String typeString, String first_name, String last_name) {

        final NotificationType type = NotificationType.valueOf(typeString);

        switch (type) {
            case FOLLOW:
                return first_name + " " + last_name + " " + FOLLOWED;
            case PARTNERSHIP_REQUEST:
                return first_name + " " + last_name + " " + PARTNERSHIP_REQUEST;
            case PARTNERSHIP_ACCEPTED_BY_YOU:
                return "You " + PARTNERSHIP_ACCEPTED + " with " + first_name + " " + last_name + ".";
            case PARTNERSHIP_ACCEPTED:
                return first_name + " " + last_name + " " + PARTNERSHIP_ACCEPTED + ".";
        }
        return null;
    }


    public static String getPositiveButtonText(String typeString) {
        final NotificationType type = NotificationType.valueOf(typeString);
        switch (type) {
            case PARTNERSHIP_REQUEST:
                return "Accept";
            case SPACE_INVITE:
                return "Join";
        }
        return null;
    }

    public static String getNegativeButtonText(String typeString) {
        final NotificationType type = NotificationType.valueOf(typeString);
        switch (type) {
            case PARTNERSHIP_REQUEST:
            case SPACE_INVITE:
                return "Decline";
        }
        return null;
    }
}
