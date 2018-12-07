package com.frontierapp.frontierapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProjectViewHolder extends RecyclerView.ViewHolder {
    LinearLayout projectLinearLayout;
    TextView projectName, projectProgress;

    public ProjectViewHolder(View itemView) {
        super(itemView);

        projectLinearLayout = (LinearLayout) itemView.findViewById(R.id.projectLinearLayout);
        projectName = (TextView) itemView.findViewById(R.id.projectName);
        projectProgress = (TextView) itemView.findViewById(R.id.projectProgress);
    }
}
