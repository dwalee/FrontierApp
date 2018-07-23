package com.frontierapp.frontierapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class NotificationItemRecyclerAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    private Context context;
    private List<NotificationViewData> notificationsViewDataList;
    private View view;

    public NotificationItemRecyclerAdapter(Context context, List<NotificationViewData> notificationsViewDataList) {
        this.context = context;
        this.notificationsViewDataList = notificationsViewDataList;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_layout,
                parent, false);

        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        final NotificationViewData notificationViewData = notificationsViewDataList.get(position);

        holder.notificationTextView.setText(notificationViewData.getNotificationText());

        String acceptButtonName = notificationViewData.getNotificationAcceptButtonName();
        String cancelButtonName = notificationViewData.getNotifcationCancelButtonName();
        String notifcationImageUrl = notificationViewData.getNotificationImageUrl();

        if(acceptButtonName != null)
            holder.notificationAcceptButton.setText(acceptButtonName);
        else
            holder.notificationAcceptButton.setVisibility(View.GONE);

        if(cancelButtonName != null)
            holder.notificationCancelButton.setText(cancelButtonName);
        else
            holder.notificationCancelButton.setVisibility(View.GONE);

        Glide.with(context)
                .load(notifcationImageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.notificationImageView);
    }

    @Override
    public int getItemCount() {
        return notificationsViewDataList.size();
    }
}
