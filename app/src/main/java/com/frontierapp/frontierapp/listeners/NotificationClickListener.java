package com.frontierapp.frontierapp.listeners;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.frontierapp.frontierapp.NotificationType;
import com.frontierapp.frontierapp.databinding.NotificationItemLayoutBinding;
import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.model.Notification;
import com.frontierapp.frontierapp.view.ConnectionsProfileActivity;

public class NotificationClickListener implements View.OnClickListener {
    private static final String TAG = "NotificationClickListen";
    private NotificationItemLayoutBinding binding;
    private Context context;

    public NotificationClickListener(NotificationItemLayoutBinding binding) {
        this.binding = binding;
        context = binding.getRoot().getContext();
    }

    @Override
    public void onClick(View v) {
        Notification notification = binding.getNotification();
        NotificationType type = NotificationType.valueOf(notification.getType());
        Intent intent;

        switch (type){
            case FOLLOW:
            case PARTNERSHIP_REQUEST:
            case PARTNERSHIP_ACCEPTED:
                intent = new Intent(context, ConnectionsProfileActivity.class);
                intent.putExtra("PATH", notification.getSender().getPath());
                context.startActivity(intent);
                break;
        }
    }
}
