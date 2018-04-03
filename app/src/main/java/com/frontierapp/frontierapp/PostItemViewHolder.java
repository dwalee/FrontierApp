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
    public ImageView userAvatarImageView, likeIconImageView,
    commentIconImageView, shareIconImageView;
    public ImageButton postImageButton;
    public TextView postTextView;
    public TextView postTimestampTextView;

    //Tie each child view to a variable;
    public PostItemViewHolder(View itemView) {
        super(itemView);

        userNameTextView = (TextView) itemView.findViewById(R.id.usernameTextView);
        userAvatarImageView = (ImageView) itemView.findViewById(R.id.userAvatarImageView);
        likeIconImageView = (ImageView) itemView.findViewById(R.id.likeIconImageView);
        commentIconImageView = (ImageView) itemView.findViewById(R.id.commentIconImageView);
        shareIconImageView = (ImageView) itemView.findViewById(R.id.shareIconImageView);
        postImageButton = (ImageButton) itemView.findViewById(R.id.postImageButton);
        postTextView = (TextView) itemView.findViewById(R.id.postTextView);
        postTimestampTextView = (TextView) itemView.findViewById(R.id.postTimestampTextView);
    }
}
