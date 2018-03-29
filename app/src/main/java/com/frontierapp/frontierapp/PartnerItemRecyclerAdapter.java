package com.frontierapp.frontierapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Yoshtown on 3/29/2018.
 */

public class PartnerItemRecyclerAdapter extends RecyclerView.Adapter<PartnerViewHolder> {
    private List<PartnershipViewData> partnershipViewDataList;

    public PartnerItemRecyclerAdapter(List<PartnershipViewData> partnershipViewDataList) {
        this.partnershipViewDataList = partnershipViewDataList;
    }

    @Override
    public PartnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.partner_item_layout,
                parent, false
        );

        return new PartnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PartnerViewHolder holder, int position) {
        PartnershipViewData partnershipViewData = partnershipViewDataList.get(position);
        holder.partnerAvatarImageView.setImageResource(partnershipViewData.getPartnerAvatar());
        holder.partnerNameTextView.setText(partnershipViewData.getPartnerName());
    }

    @Override
    public int getItemCount() {
        return partnershipViewDataList.size();
    }
}
