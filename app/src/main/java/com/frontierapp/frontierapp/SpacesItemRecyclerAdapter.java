package com.frontierapp.frontierapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class SpacesItemRecyclerAdapter extends RecyclerView.Adapter<SpacesViewHolder> {
    private static final String TAG = "SpacesItemRecycler";
    View view;
    List<SpacesViewData> spacesViewDataList;
    Context context;

    public SpacesItemRecyclerAdapter(List<SpacesViewData> spacesViewDataList, Context context) {
        this.spacesViewDataList = spacesViewDataList;
        this.context = context;
    }

    @Override
    public SpacesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spaces_item_layout,
                parent, false);

        return new SpacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpacesViewHolder holder, int position) {
        final SpacesViewData spacesViewData = spacesViewDataList.get(position);
        holder.spaceNameTextView.setText(spacesViewData.getSpace_name());
        holder.spacePurposeTextView.setText(spacesViewData.getSpace_purpose());
        holder.spacesLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent spaceIntent = new Intent(context, SpaceActivity.class);
                context.startActivity(spaceIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return spacesViewDataList.size();
    }
}
