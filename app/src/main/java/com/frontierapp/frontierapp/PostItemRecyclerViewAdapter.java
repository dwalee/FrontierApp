package com.frontierapp.frontierapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Yoshtown on 3/24/2018.
 */

public class PostItemRecyclerViewAdapter extends RecyclerView.Adapter<PostItemViewHolder> {
    private List<PostItemData> postItemDataList;

    //Create constructor to get the list
    public PostItemRecyclerViewAdapter(List<PostItemData> postItemDataList) {
        this.postItemDataList = postItemDataList;
    }


    //Create your view and tie it to the viewholder
    @Override
    public PostItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_layout,
                                        parent, false);
        return new PostItemViewHolder(view);
    }


    //Get each Post Item data from the post item data list and apply the data
    //to each respective view
    @Override
    public void onBindViewHolder(PostItemViewHolder holder, int position) {
        PostItemData postItemData = postItemDataList.get(position);
        holder.userNameTextView.setText(postItemData.getUserName());
        holder.userAvatarImageView.setImageResource(postItemData.getUserAvatar());
    }

    @Override
    public int getItemCount() {
        return postItemDataList.size();
    }
}
