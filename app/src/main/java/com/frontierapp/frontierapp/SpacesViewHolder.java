package com.frontierapp.frontierapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SpacesViewHolder extends RecyclerView.ViewHolder{
    TextView spaceNameTextView;
    TextView spacePurposeTextView;
    LinearLayout spacesLinearLayout;

    public SpacesViewHolder(View itemView) {
        super(itemView);

        spaceNameTextView = (TextView) itemView.findViewById(R.id.spaceNameTextView);
        spacePurposeTextView = (TextView) itemView.findViewById(R.id.spacePurposeTextView);
        spacesLinearLayout = (LinearLayout) itemView.findViewById(R.id.spacesLinearLayout);
    }
}
