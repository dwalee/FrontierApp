package com.frontierapp.frontierapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class NotificationItemRecyclerAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    private final String TAG = "NotificationItem";
    private Context context;
    private List<NotificationViewData> notificationsViewDataList;
    private View view;

    private SpaceFirestore spaceFirestore = new SpaceFirestore(context);
    private UserFirestore userFirestore = new UserFirestore();
    private NotificationFirestore notificationFirestore = new NotificationFirestore();

    private final String CURRENT_CONNECTIONS_ID = "current_connections_id";
    private final String CURRENT_NOTIFICATION_ID = "current_notification_id";

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
        final LinearLayout linearLayout = holder.notificationLinearLayout;

        String acceptButtonName = notificationViewData.getNotificationAcceptButtonName();
        String cancelButtonName = notificationViewData.getNotifcationCancelButtonName();

        final String full_name = notificationViewData.getFull_name();
        final String miscName = notificationViewData.getMiscName();
        final NotificationType type = notificationViewData.getNotificationType();
        final String connectionId = notificationViewData.getSender_id();
        final String notificationId = notificationViewData.getNotification_id();
        Timestamp timestamp = notificationViewData.getNotificationTimestamp();

        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        Date date = timestamp.toDate();
        String dateString = dateFormat.format(date);

        holder.notificationTimestampTextView
                .setText(dateString);

        if (!notificationViewData.getRead()) {
            linearLayout
                    .setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorNotificationItemUnRead));
        } else {
            linearLayout
                    .setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorNotificationItemRead));
        }


        if (acceptButtonName != null)
            holder.notificationAcceptButton.setText(acceptButtonName);
        else
            holder.notificationAcceptButton.setVisibility(View.GONE);

        if (cancelButtonName != null)
            holder.notificationCancelButton.setText(cancelButtonName);
        else
            holder.notificationCancelButton.setVisibility(View.GONE);

        String str = "";
        SpannableString spannableString;
        if (miscName.isEmpty()) {
            str = full_name
                    + " " + notificationViewData.getNotificationText();

            spannableString = new SpannableString(str);


            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(boldSpan, 0, full_name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        } else

        {
            str = full_name + " " + notificationViewData.getNotificationText() + " " + miscName;
            int miscNameStart = full_name.length() + notificationViewData.getNotificationText().length() + 2;

            spannableString = new SpannableString(str);
            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
            StyleSpan boldSpan2 = new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(boldSpan, 0, full_name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(boldSpan2, miscNameStart, miscName.length() + miscNameStart, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        holder.notificationTextView.setText(spannableString);

        String notificationImageUrl = notificationViewData.getNotificationImageUrl();

        Glide.with(context)
                .load(notificationImageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.notificationImageView);

        holder.notificationAcceptButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                holder.notificationAcceptButton.setVisibility(View.GONE);
                holder.notificationCancelButton.setVisibility(View.GONE);

                setNewTextAccept(holder, notificationViewData);
            }
        });

        holder.notificationCancelButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                holder.notificationAcceptButton.setVisibility(View.GONE);
                holder.notificationCancelButton.setVisibility(View.GONE);

                setNewTextDecline(holder, notificationViewData);
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(type){
                    case FOLLOW:
                    case PARTNERSHIP_REQUEST:
                    case PARTNERSHIP_ACCEPTED:
                        Log.i(TAG, "onClick: ");
                        Intent connectionIntent = new Intent(context, CurrentConnectionsProfileActivity.class);
                        connectionIntent.putExtra(CURRENT_CONNECTIONS_ID, connectionId);
                        connectionIntent.putExtra(CURRENT_NOTIFICATION_ID, notificationId);
                        context.startActivity(connectionIntent);
                        notificationFirestore.updateNotification(notificationId, false);
                        break;
                }
            }
        });
    }

    public void setNewTextAccept(NotificationViewHolder holder, NotificationViewData notificationViewData) {
        String str = "";
        SpannableString spannableString;
        String full_name = notificationViewData.getFull_name();
        String miscName = notificationViewData.getMiscName();

        if (notificationViewData.getNotificationType().equals(NotificationType.PARTNERSHIP_REQUEST)) {
            str = "You have accepted " + notificationViewData.getFull_name()
                    + " as a partner.";

            spannableString = new SpannableString(str);
            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(boldSpan, 18, full_name.length() + 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.notificationTextView.setText(spannableString);
        } else if (notificationViewData.getNotificationType().equals(NotificationType.SPACE_INVITE)) {
            str = "You have joined " + notificationViewData.getFull_name()
                    + " space " + notificationViewData.getMiscName();

            int miscNameStart = 23 + full_name.length();

            spannableString = new SpannableString(str);
            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
            StyleSpan boldSpan2 = new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(boldSpan, 16, full_name.length() + 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(boldSpan2, miscNameStart, miscName.length() + miscNameStart, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.notificationTextView.setText(spannableString);
            spaceFirestore.addSpaceMember(userFirestore.getCurrentUserId(), notificationViewData);
        }

        notificationFirestore.updateNotification(notificationViewData.getNotification_id(), true);
    }

    public void setNewTextDecline(NotificationViewHolder holder, NotificationViewData notificationViewData) {
        String str = "";
        SpannableString spannableString;
        String full_name = notificationViewData.getFull_name();
        String miscName = notificationViewData.getMiscName();

        if (notificationViewData.getNotificationType().equals(NotificationType.PARTNERSHIP_REQUEST)) {
            str = "You have declined " + notificationViewData.getFull_name()
                    + " as a partner.";

            spannableString = new SpannableString(str);
            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(boldSpan, 18, full_name.length() + 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.notificationTextView.setText(spannableString);
        } else if (notificationViewData.getNotificationType().equals(NotificationType.SPACE_INVITE)) {
            str = "You have declined to join " + notificationViewData.getFull_name()
                    + " space " + notificationViewData.getMiscName();

            int miscNameStart = 33 + full_name.length();

            spannableString = new SpannableString(str);
            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
            StyleSpan boldSpan2 = new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(boldSpan, 26, full_name.length() + 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(boldSpan2, miscNameStart, miscName.length() + miscNameStart, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.notificationTextView.setText(spannableString);

            spaceFirestore.removeSpacePendingMember(notificationViewData.getMiscId(), userFirestore.getCurrentUserId());
        }

        notificationFirestore.updateNotification(notificationViewData.getNotification_id(), true);
    }

    @Override
    public int getItemCount() {
        return notificationsViewDataList.size();
    }

}
