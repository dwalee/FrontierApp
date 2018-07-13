package com.frontierapp.frontierapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;



public class FollowerItemRecyclerAdapter extends RecyclerView.Adapter<FollowerViewHolder> {
    private static final String TAG = "FollowerRecycler";
    private List<FollowerViewData> followerViewDataList;
    private Context context;
    private View view;

    public FollowerItemRecyclerAdapter(Context context, List<FollowerViewData> followerViewDataList) {
        this.context = context;
        this.followerViewDataList = followerViewDataList;
    }

    @Override
    public FollowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.partner_item_layout,
                parent, false);

        return new FollowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FollowerViewHolder holder, int position) {
        final FollowerViewData followerViewData = followerViewDataList.get(position);
        Glide.with(context).load(followerViewData.getFollowerProfilePicUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.followerAvatarImageView);

        Log.i(TAG, "onBindViewHolder: followerName = " + followerViewData.getFollowerName());
        holder.followerNameTextView.setText(followerViewData.getFollowerName());
        holder.followerRequestButton.setVisibility(View.GONE);
        holder.followerItemLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent partnerProfileIntent = new Intent(context, CurrentPartnerProfileActivity.class);

                partnerProfileIntent.putExtra("CurrentPartnerId",
                        followerViewData.getFollowerId());
                partnerProfileIntent.putExtra("ClassName", "Follower");
                Log.i("CurrentPartnerRecycler", "onClick: " + followerViewData.getFollowerId());
                context.startActivity(partnerProfileIntent);
                //((CurrentPartnersActivity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return followerViewDataList.size();
    }
}
