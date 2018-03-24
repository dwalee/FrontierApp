package com.frontierapp.frontierapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Yoshtown on 3/24/2018.
 */

public class PostItemViewHolder extends RecyclerView.ViewHolder {
    public TextView userNameTextView;
    public ImageView userAvatarImageView;

    public PostItemViewHolder(View itemView) {
        super(itemView);

        userNameTextView = (TextView) itemView.findViewById(R.id.usernameTextView);
        userAvatarImageView = (ImageView) itemView.findViewById(R.id.userAvatarImageView);
    }
}
