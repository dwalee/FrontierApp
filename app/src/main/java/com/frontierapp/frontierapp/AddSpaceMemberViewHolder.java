package com.frontierapp.frontierapp;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AddSpaceMemberViewHolder extends RecyclerView.ViewHolder {
    AppCompatCheckBox addSpaceMemberCheckBox;
    ImageView addSpaceMemberImageView;
    TextView addSpaceMemberNameTextView;

    public AddSpaceMemberViewHolder(View itemView) {
        super(itemView);

        addSpaceMemberCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.addSpaceMemberCheckBox);
        addSpaceMemberImageView = (ImageView) itemView.findViewById(R.id.addSpaceMemberImageView);
        addSpaceMemberNameTextView = (TextView) itemView.findViewById(R.id.addSpaceMemberNameTextView);
    }
}
