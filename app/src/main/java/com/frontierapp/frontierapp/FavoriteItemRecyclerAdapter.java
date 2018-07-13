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

public class FavoriteItemRecyclerAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {
    private List<FavoriteViewData> favoriteViewDataList;
    private Context context;
    private View view;

    public FavoriteItemRecyclerAdapter(Context context, List<FavoriteViewData> favoriteViewDataList) {
        this.context = context;
        this.favoriteViewDataList = favoriteViewDataList;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.partner_item_layout,
        parent, false);

        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        final FavoriteViewData favoriteViewData = favoriteViewDataList.get(position);
        Glide.with(context).load(favoriteViewData.getFavoriteProfilePicUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.favoriteAvatarImageView);

        holder.favoriteNameTextView.setText(favoriteViewData.getFavoriteName());
        holder.favoriteRequestButton.setVisibility(View.GONE);
        holder.favoriteItemLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent partnerProfileIntent = new Intent(context, CurrentPartnerProfileActivity.class);

                partnerProfileIntent.putExtra("CurrentPartnerId",
                        favoriteViewData.getFavoriteId());
                partnerProfileIntent.putExtra("ClassName", "Favorite");
                Log.i("CurrentPartnerRecycler", "onClick: " + favoriteViewData.getFavoriteId());
                context.startActivity(partnerProfileIntent);
                //((CurrentPartnersActivity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteViewDataList.size();
    }
}
