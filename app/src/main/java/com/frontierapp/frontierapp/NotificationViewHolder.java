package com.frontierapp.frontierapp;

import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    ImageView notificationImageView;
    TextView notificationTextView;
    AppCompatButton notificationAcceptButton, notificationCancelButton;

    private String full_name;
    private String notifcation_text;
    private String notification_id;
    private String reciever_id;
    private String sender_id;

    private NotificationFirestore notificationFirestore = new NotificationFirestore();
    private CurrentPartnersFirestore currentPartnersFirestore;
    private final FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();

    public NotificationViewHolder(View itemView) {
        super(itemView);

        currentPartnersFirestore = new CurrentPartnersFirestore(itemView.getContext());

        notificationImageView = (ImageView) itemView.findViewById(R.id.notificationImageView);
        notificationTextView = (TextView) itemView.findViewById(R.id.notificationTextView);
        notificationAcceptButton = (AppCompatButton) itemView.findViewById(R.id.notificationAcceptButton);
        notificationCancelButton = (AppCompatButton) itemView.findViewById(R.id.notificationCancelButton);

        notificationAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifcation_text = "You have agreed to partner with";
                notificationAcceptButton.setVisibility(View.GONE);
                notificationCancelButton.setVisibility(View.GONE);

                final SpannableStringBuilder sb = new SpannableStringBuilder( notifcation_text
                        + " " + getFull_name());
                final StyleSpan bold = new StyleSpan(Typeface.BOLD);
                sb.setSpan(bold, notifcation_text.length(), sb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                currentPartnersFirestore.requestNewPartnerToFirestore(firebaseuser.getUid(), getSender_id(),
                        PartnerStatus.True);

                notificationFirestore.updateNotification(NotificationType.IGNORE, getNotification_id());
                notificationTextView.setText(sb);
            }
        });

        notificationCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifcation_text = "You have denied to partner with";
                notificationAcceptButton.setVisibility(View.GONE);
                notificationCancelButton.setVisibility(View.GONE);

                final SpannableStringBuilder sb = new SpannableStringBuilder( notifcation_text
                        + " " + getFull_name());
                final StyleSpan bold = new StyleSpan(Typeface.BOLD);
                sb.setSpan(bold, notifcation_text.length(), sb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                currentPartnersFirestore.requestNewPartnerToFirestore(firebaseuser.getUid(), getSender_id(),
                        PartnerStatus.False);
                notificationFirestore.updateNotification(NotificationType.IGNORE, getNotification_id());
                notificationTextView.setText(sb);
            }
        });
    }

    public void setFullName(String full_name){
        this.full_name = full_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReciever_id() {
        return reciever_id;
    }

    public void setReciever_id(String reciever_id) {
        this.reciever_id = reciever_id;
    }
}
