package com.frontierapp.frontierapp;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
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
    public void onBindViewHolder(final NotificationViewHolder holder, int position) {
        final NotificationViewData notificationViewData = notificationsViewDataList.get(position);

        String acceptButtonName = notificationViewData.getNotificationAcceptButtonName();
        String cancelButtonName = notificationViewData.getNotifcationCancelButtonName();

        holder.setNotification_id(notificationViewData.getNotification_id());
        holder.setSender_id(notificationViewData.getSender_id());

        if(acceptButtonName != null)
            holder.notificationAcceptButton.setText(acceptButtonName);
        else
            holder.notificationAcceptButton.setVisibility(View.GONE);

        if(cancelButtonName != null)
            holder.notificationCancelButton.setText(cancelButtonName);
        else
            holder.notificationCancelButton.setVisibility(View.GONE);

        String full_name = notificationViewData.getFull_name();
        holder.setFullName(full_name);

        int fullNameSize = 0;
        if(full_name != null)
            fullNameSize = notificationViewData.getFull_name().length();

        final SpannableStringBuilder sb = new SpannableStringBuilder( full_name
                + " " + notificationViewData.getNotificationText());
        final StyleSpan bold = new StyleSpan(Typeface.BOLD);
        sb.setSpan(bold, 0, fullNameSize, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        holder.notificationTextView.setText(sb);

        String notificationImageUrl = notificationViewData.getNotificationImageUrl();

        Glide.with(context)
                .load(notificationImageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.notificationImageView);
    }

    @Override
    public int getItemCount() {
        return notificationsViewDataList.size();
    }
}
