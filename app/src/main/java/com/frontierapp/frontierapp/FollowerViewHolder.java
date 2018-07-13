package com.frontierapp.frontierapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FollowerViewHolder extends RecyclerView.ViewHolder {
    ImageView followerAvatarImageView;
    TextView followerNameTextView;
    ImageButton followerRequestButton;
    LinearLayout followerItemLinearLayout;

    public FollowerViewHolder(View itemView) {
        super(itemView);

        followerAvatarImageView = (ImageView) itemView.findViewById(R.id.partnerAvatarImageView);
        followerNameTextView = (TextView) itemView.findViewById(R.id.partnerNameTextView);
        followerRequestButton = (ImageButton) itemView.findViewById(R.id.partnershipRequestButton);
        followerItemLinearLayout = (LinearLayout) itemView.findViewById(R.id.partnerItemLayout);
    }
}
