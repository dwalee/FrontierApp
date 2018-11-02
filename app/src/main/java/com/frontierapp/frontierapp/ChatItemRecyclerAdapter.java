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

public class ChatItemRecyclerAdapter extends RecyclerView.Adapter<CurrentPartnerViewHolder>{
    private List<CurrentPartnershipViewData> currentPartnershipViewDataList;
    private Context context;
    private View view;

    public ChatItemRecyclerAdapter(Context context, List<CurrentPartnershipViewData> currentPartnershipViewDataList) {
        this.currentPartnershipViewDataList = currentPartnershipViewDataList;
        this.context = context;
    }

    @Override
    public CurrentPartnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.partner_item_layout,
                parent, false
        );

        return new CurrentPartnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrentPartnerViewHolder holder, final int position) {
        final CurrentPartnershipViewData currentPartnershipViewData = currentPartnershipViewDataList.get(position);
        Glide.with(context).load(currentPartnershipViewData.getCurrentPartnerProfilePicUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.currenPartnerAvatarImageView);
        //holder.partnerAvatarImageView.setImageBitmap(partnershipViewData.getPartnerAvatarBitmap());
        holder.currentPartnerNameTextView.setText(currentPartnershipViewData.getCurrentPartnerName());
        holder.currentPartnershipRequest.setVisibility(View.GONE);
        holder.partnerItemLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent partnerProfileIntent = new Intent(context, ChatUI.class);
                //partnerProfileIntent.putExtra("CurrentPartnerIdIndex", position);
                partnerProfileIntent.putExtra("CurrentPartnerId",
                        currentPartnershipViewData.getCurrentPartnerId());
                partnerProfileIntent.putExtra("ClassName", "Partner");
                Log.i("CurrentPartnerRecycler", "onClick: " + currentPartnershipViewData.getCurrentPartnerId());
                context.startActivity(partnerProfileIntent);
                //((CurrentPartnersActivity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return currentPartnershipViewDataList.size();
    }
}
