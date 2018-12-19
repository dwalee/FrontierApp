package com.frontierapp.frontierapp.adapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.frontierapp.frontierapp.NotificationType;
import com.frontierapp.frontierapp.model.Notification;
import com.frontierapp.frontierapp.util.NotificationUtil;

public class CustomButtonBindingAdapter {

    @BindingAdapter("bind:positive_button")
    public static void setPositiveButton(AppCompatButton button, String typeString){
        NotificationType type = NotificationType.valueOf(typeString);
        String notification = NotificationUtil.getPositiveButtonText(typeString);

        switch (type){
            case FOLLOW:
            case PARTNERSHIP_ACCEPTED:
            case JOINED_SPACE:
                button.setVisibility(View.GONE);
                break;
            case PARTNERSHIP_REQUEST:
            case SPACE_INVITE:
                button.setVisibility(View.VISIBLE);
                button.setText(notification);
                break;
        }
    }

    @BindingAdapter("bind:negative_button")
    public static void setNegativeButton(AppCompatButton button, String typeString){
        NotificationType type = NotificationType.valueOf(typeString);
        String notification = NotificationUtil.getNegativeButtonText(typeString);

        switch (type){
            case FOLLOW:
            case PARTNERSHIP_ACCEPTED:
            case JOINED_SPACE:
                button.setVisibility(View.GONE);
                break;
            case PARTNERSHIP_REQUEST:
            case SPACE_INVITE:
                button.setVisibility(View.VISIBLE);
                button.setText(notification);
                break;
        }
    }

}
