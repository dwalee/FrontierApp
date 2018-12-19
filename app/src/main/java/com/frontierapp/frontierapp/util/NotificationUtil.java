package com.frontierapp.frontierapp.util;

import android.util.Log;

import com.frontierapp.frontierapp.NotificationType;
import com.frontierapp.frontierapp.model.Notification;

import static com.frontierapp.frontierapp.datasource.FirestoreConstants.*;

public class NotificationUtil {
    private static final String TAG = "NotificationUtil";
    public static String getTypeString(String typeString){

        final NotificationType type = NotificationType.valueOf(typeString);

        switch (type){
            case FOLLOW:
                return FOLLOWED;
            case PARTNERSHIP_REQUEST:
                return PARTNERSHIP_REQUEST;
            case PARTNERSHIP_ACCEPTED:
                return PARTNERSHIP_ACCEPTED;

        }
        return null;
    }


    public static String getPositiveButtonText(String typeString){
        final NotificationType type = NotificationType.valueOf(typeString);
        switch (type){
            case PARTNERSHIP_REQUEST:
                return "Accept";
            case SPACE_INVITE:
                return "Join";
        }
        return null;
    }

    public static String getNegativeButtonText(String typeString){
        final NotificationType type = NotificationType.valueOf(typeString);
        switch (type){
            case PARTNERSHIP_REQUEST:
            case SPACE_INVITE:
                return "Decline";
        }
        return null;
    }
}
