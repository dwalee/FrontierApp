package com.frontierapp.frontierapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private static final String TAG = "NotificationActivity";
    RecyclerView notificationRecyclerview;
    SwipeRefreshLayout notificationSwipeRefreshLayout;
    Toolbar notificationToolbar;
    TextView defaultTextView;
    Context context = this;

    UserFirestore userFirestore = new UserFirestore();
    NotificationFirestore notificationFirestore = new NotificationFirestore();
    SpaceFirestore spaceFirestore = new SpaceFirestore(this);

    private List<NotificationViewData> notificationViewDataList = new ArrayList<>();
    private NotificationItemRecyclerAdapter notificationItemRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        instantiateView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getNotifications();
    }

    public void instantiateView() {
        notificationToolbar = (Toolbar) findViewById(R.id.notificationToolbar);
        notificationToolbar.setTitle("Notifications");

        setSupportActionBar(notificationToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        defaultTextView = (TextView) findViewById(R.id.notificationDefaultTextView);

        notificationRecyclerview = (RecyclerView) findViewById(R.id.notificationRecyclerView);
        notificationRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        notificationRecyclerview.setHasFixedSize(true);

        notificationItemRecyclerAdapter = new NotificationItemRecyclerAdapter(context,
                notificationViewDataList);
        notificationRecyclerview.setAdapter(notificationItemRecyclerAdapter);

        notificationSwipeRefreshLayout = (SwipeRefreshLayout)
                findViewById(R.id.notificationSwipeRefreshLayout);

        notificationSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNotifications();
            }
        });

    }

    public void getNotifications() {

        notificationViewDataList.clear();

        notificationFirestore
                .getNotifications()
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            defaultTextView.setVisibility(View.VISIBLE);
                            notificationRecyclerview.setVisibility(View.GONE);
                            return;

                        } else {
                            defaultTextView.setVisibility(View.GONE);
                            notificationRecyclerview.setVisibility(View.VISIBLE);
                        }

                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            NotificationViewData notificationViewData = new NotificationViewData();

                            String notificationType = documentSnapshot.getString(notificationFirestore.TYPE);
                            Log.i(TAG, "onSuccess: notificationType = " + notificationType);
                            String notificationId = documentSnapshot.getId();
                            String senderId = documentSnapshot.getString(notificationFirestore.SENDER_ID);
                            String miscId = documentSnapshot.getString(notificationFirestore.MISC_ID);
                            String miscName = documentSnapshot.getString(notificationFirestore.MISC_NAME);
                            String full_name = documentSnapshot.getString(notificationFirestore.FULL_NAME);
                            Log.i(TAG, "onSuccess: full_name = " + full_name);
                            String image_url = documentSnapshot.getString(notificationFirestore.PROFILE_URL);
                            Boolean read = documentSnapshot.getBoolean(notificationFirestore.READ);
                            Timestamp timestamp = documentSnapshot.getTimestamp(notificationFirestore.TIMESTAMP);
                            Log.i(TAG, "onSuccess: timestamp = " + timestamp.toString());

                            notificationViewData.setFull_name(full_name);
                            notificationViewData.setNotificationImageUrl(image_url);
                            notificationViewData.setNotification_id(notificationId);
                            notificationViewData.setSender_id(senderId);
                            notificationViewData.setMiscId(miscId);
                            notificationViewData.setMiscName(miscName);
                            notificationViewData.setRead(read);
                            notificationViewData.setNotificationTimestamp(timestamp);

                            switch (notificationType) {
                                case "Follow":
                                    notificationViewData.setNotificationText(NotificationConstants.FOLLOWED);
                                    notificationViewData.setNotificationType(NotificationType.FOLLOW);
                                    break;
                                case "Partnership Accepted":
                                    notificationViewData.setNotificationText(NotificationConstants.PARTNERSHIP_ACCEPTED);
                                    notificationViewData.setNotificationType(NotificationType.PARTNERSHIP_ACCEPTED);
                                    break;
                                case "Partnership Request":
                                    notificationViewData.setNotificationText(NotificationConstants.PARTNERSHIP_REQUEST);
                                    notificationViewData.setNotificationAcceptButtonName("Accept");
                                    notificationViewData.setNotifcationCancelButtonName("Decline");
                                    notificationViewData.setNotificationType(NotificationType.PARTNERSHIP_REQUEST);
                                    break;
                                case "Space Invite":
                                    notificationViewData.setNotificationText(NotificationConstants.SPACE_INVITE);
                                    notificationViewData.setNotificationType(NotificationType.SPACE_INVITE);

                                    notificationViewData.setNotificationAcceptButtonName("Join");
                                    notificationViewData.setNotifcationCancelButtonName("Decline");
                                    break;
                                case "Joined Space":
                                    notificationViewData.setNotificationText(NotificationConstants.JOINED_SPACE);
                                    notificationViewData.setNotificationType(NotificationType.JOINED_SPACE);
                                    break;
                            }
                            Log.i(TAG, "onSuccess: notificationViewDataList = " + notificationViewDataList);
                            notificationViewDataList.add(notificationViewData);

                            notificationItemRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }
        });
    }


    public void refreshNotifications() {
        notificationSwipeRefreshLayout.setColorSchemeResources(R.color.colorLoadRefresh);
        notificationViewDataList.clear();

        this.onStart();
        notificationSwipeRefreshLayout.setColorSchemeResources(R.color.colorFinishRefresh);

        notificationSwipeRefreshLayout.setRefreshing(false);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

}
