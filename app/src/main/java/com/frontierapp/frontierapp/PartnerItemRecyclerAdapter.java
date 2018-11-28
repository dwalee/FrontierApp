package com.frontierapp.frontierapp;

import android.content.Context;
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
    private Context context;
    private View view;

    public PartnerItemRecyclerAdapter(Context context, List<PartnershipViewData> partnershipViewDataList) {
        this.partnershipViewDataList = partnershipViewDataList;
        this.context = context;
    }

    @Override
    public PartnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.connections_item_layout,
                parent, false
        );

        return new PartnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PartnerViewHolder holder, final int position) {
        /*PartnershipViewData partnershipViewData = partnershipViewDataList.get(position);
        Glide.with(context).load(partnershipViewData.getPartnerAvatarUrl()).apply(RequestOptions.circleCropTransform()).into(holder.connectionsProfileImageView);
        //holder.connectionsProfileImageView.setImageBitmap(partnershipViewData.getPartnerAvatarBitmap());
        holder.connectionsNameTextView.setText(partnershipViewData.getPartnerName());
        holder.partnershipRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick: ",  Integer.toString(position));
                Toast.makeText(context, Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return partnershipViewDataList.size();
    }
}
