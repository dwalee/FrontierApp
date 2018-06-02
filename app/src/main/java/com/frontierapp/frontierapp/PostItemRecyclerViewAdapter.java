package com.frontierapp.frontierapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by Yoshtown on 3/24/2018.
 */

public class PostItemRecyclerViewAdapter extends RecyclerView.Adapter<PostItemViewHolder> {
    private List<PostItemData> postItemDataList;
    private Context context;
    private View view;

    //Create constructor to get the list
    public PostItemRecyclerViewAdapter(Context context, List<PostItemData> postItemDataList) {
        this.postItemDataList = postItemDataList;
        this.context = context;
    }

    //Create your parent view and tie it to the viewholder
    @Override
    public PostItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_layout,
                                        parent, false);
        return new PostItemViewHolder(view);
    }


    //Get each Post Item data from the post item data list and apply the data
    //to each respective child view
    @Override
    public void onBindViewHolder(PostItemViewHolder holder, int position) {
        final PostItemData postItemData = postItemDataList.get(position);
        holder.userNameTextView.setText(postItemData.getUserName());
        //holder.userAvatarImageView.setImageResource(postItemData.getUserAvatar());
        holder.postTextView.setText(postItemData.getPostString());
        holder.postTimestampTextView.setText(java.text.DateFormat.getInstance().format(postItemData.getPostTimeStamp()));
        postItemData.convertPostUrlToBitmap();
        //
        Glide.with(view).load(postItemData.getPostPhoto()).into(holder.postImageButton);
        //holder.postImageButton.setImageBitmap(postItemData.getPostPhoto());
        postItemData.convertUserAvatarUrlToBitmap();
        Glide.with(view).load(postItemData.getUserAvatarPhoto()).apply(RequestOptions.circleCropTransform()).into(holder.userAvatarImageView);
        //holder.userAvatarImageView.setImageBitmap(postItemData.getUserAvatarPhoto());
        holder.likeIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Change the like button to green when liked
                //Change it back to transparent when unliked
                if(!postItemData.getLiked())
                    v.setBackgroundColor(Color.GREEN);
                else
                    v.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        holder.commentIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open up new UI to view comments
            }
        });

        holder.shareIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open new UI to choose options to share post
            }
        });
    }

    @Override
    public int getItemCount() {
        return postItemDataList.size();
    }
}
