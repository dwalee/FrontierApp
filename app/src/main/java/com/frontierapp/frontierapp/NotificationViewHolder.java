package com.frontierapp.frontierapp;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    ImageView notificationImageView;
    TextView notificationTextView;
    AppCompatButton notificationAcceptButton, notificationCancelButton;

    public NotificationViewHolder(View itemView) {
        super(itemView);

        notificationImageView = (ImageView) itemView.findViewById(R.id.notificationImageView);
        notificationTextView = (TextView) itemView.findViewById(R.id.notificationTextView);
        notificationAcceptButton = (AppCompatButton) itemView.findViewById(R.id.notificationAcceptButton);
        notificationCancelButton = (AppCompatButton) itemView.findViewById(R.id.notificationCancelButton);
    }
}
