package com.frontierapp.frontierapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConnectionViewHolder extends RecyclerView.ViewHolder {
    ImageView connectionProfileImageView;
    TextView connectionNameTextView, connectionTitleTextView;
    ImageButton connectionRequest;
    LinearLayout connectionItemLinearLayout;

    public ConnectionViewHolder(View itemView) {
        super(itemView);

        connectionItemLinearLayout = (LinearLayout) itemView.findViewById(R.id.connectionsItemLayout);
        connectionProfileImageView = (ImageView) itemView.findViewById(R.id.connectionsProfileImageView);
        connectionNameTextView = (TextView) itemView.findViewById(R.id.connectionsNameTextView);
        connectionRequest = (ImageButton) itemView.findViewById(R.id.connectionsRequestButton);
        connectionTitleTextView = (TextView) itemView.findViewById(R.id.connectionsTitleTextView);
    }
}
