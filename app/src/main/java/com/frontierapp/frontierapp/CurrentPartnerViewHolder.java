package com.frontierapp.frontierapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CurrentPartnerViewHolder extends RecyclerView.ViewHolder {
    ImageView currenPartnerAvatarImageView;
    TextView currentPartnerNameTextView;
    ImageButton currentPartnershipRequest;
    LinearLayout partnerItemLinearLayout;

    public CurrentPartnerViewHolder(View itemView) {
        super(itemView);

        partnerItemLinearLayout = (LinearLayout) itemView.findViewById(R.id.partnerItemLayout);
        currenPartnerAvatarImageView = (ImageView) itemView.findViewById(R.id.partnerAvatarImageView);
        currentPartnerNameTextView = (TextView) itemView.findViewById(R.id.partnerNameTextView);
        currentPartnershipRequest = (ImageButton) itemView.findViewById(R.id.partnershipRequestButton);
    }
}
