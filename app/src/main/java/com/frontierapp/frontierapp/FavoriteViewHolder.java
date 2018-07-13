package com.frontierapp.frontierapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {
    ImageView favoriteAvatarImageView;
    TextView favoriteNameTextView;
    ImageButton favoriteRequestButton;
    LinearLayout favoriteItemLinearLayout;

    public FavoriteViewHolder(View itemView) {
        super(itemView);

        favoriteAvatarImageView = (ImageView) itemView.findViewById(R.id.partnerAvatarImageView);
        favoriteNameTextView = (TextView) itemView.findViewById(R.id.partnerNameTextView);
        favoriteRequestButton = (ImageButton) itemView.findViewById(R.id.partnershipRequestButton);
        favoriteItemLinearLayout = (LinearLayout) itemView.findViewById(R.id.partnerItemLayout);
    }
}
