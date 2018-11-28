package com.frontierapp.frontierapp;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    ImageView notificationImageView;
    TextView notificationTextView, notificationTimestampTextView;
    AppCompatButton notificationAcceptButton, notificationCancelButton;
    LinearLayout notificationLinearLayout;

    public NotificationViewHolder(View itemView) {
        super(itemView);

        notificationTimestampTextView = (TextView) itemView.findViewById(R.id.notificationTimestampTextView);
        notificationImageView = (ImageView) itemView.findViewById(R.id.notificationImageView);
        notificationTextView = (TextView) itemView.findViewById(R.id.notificationTextView);
        notificationAcceptButton = (AppCompatButton) itemView.findViewById(R.id.notificationAcceptButton);
        notificationCancelButton = (AppCompatButton) itemView.findViewById(R.id.notificationCancelButton);
        notificationLinearLayout = (LinearLayout) itemView.findViewById(R.id.notificationItemLinearLayout);
    }
}
