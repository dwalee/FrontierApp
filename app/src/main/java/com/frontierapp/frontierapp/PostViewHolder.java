package com.frontierapp.frontierapp;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.relex.circleindicator.CircleIndicator;

public class PostViewHolder extends RecyclerView.ViewHolder {
    TextView postDescTextView, postTitleTextView, usernameTextView, postTimestampTextView, voteValueTextView,
    commentValueTextView;
    ImageView profileImageView, upvoteIconImageView, downvoteIconImageView;
    ViewPager postViewPager;
    CircleIndicator circleIndicator;
    RelativeLayout mediaPostRelativeLayout;

    public PostViewHolder(View itemView) {
        super(itemView);

        postDescTextView = (TextView) itemView.findViewById(R.id.postDescTextView);
        postTitleTextView = (TextView) itemView.findViewById(R.id.postTitleTextView);
        usernameTextView = (TextView) itemView.findViewById(R.id.usernameTextView);
        postTimestampTextView = (TextView) itemView.findViewById(R.id.postTimestampTextView);
        mediaPostRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.mediaPostRelativeLayout);
        profileImageView = (ImageView) itemView.findViewById(R.id.profileImageView);
        postViewPager = (ViewPager) itemView.findViewById(R.id.mediaViewPager);
        circleIndicator = (CircleIndicator) itemView.findViewById(R.id.mediaCircleIndicator);
        voteValueTextView = (TextView) itemView.findViewById(R.id.voteValueTextView);
        upvoteIconImageView = (ImageView) itemView.findViewById(R.id.upvoteIconImageView);
        downvoteIconImageView = (ImageView) itemView.findViewById(R.id.downvoteIconImageView);
        commentValueTextView = (TextView) itemView.findViewById(R.id.commentValueTextView);
    }
}
