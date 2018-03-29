package com.frontierapp.frontierapp;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Yoshtown on 3/29/2018.
 */

public class PartnerViewHolder extends RecyclerView.ViewHolder {
    ImageView partnerAvatarImageView;
    TextView partnerNameTextView;
    AppCompatButton partnershipRequest;

    public PartnerViewHolder(final View itemView) {
        super(itemView);

        partnerAvatarImageView = (ImageView) itemView.findViewById(R.id.partnerAvatarImageView);
        partnerNameTextView = (TextView) itemView.findViewById(R.id.partnerNameTextView);
        partnershipRequest = (AppCompatButton) itemView.findViewById(R.id.partnershipRequestButton);
    }
}
