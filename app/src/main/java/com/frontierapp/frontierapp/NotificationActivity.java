package com.frontierapp.frontierapp;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private static final String TAG = "NotificationActivity";
    RecyclerView notificationRecyclerview;
    SwipeRefreshLayout notificationSwipeRefreshLayout;
    Toolbar notificationToolbar;

    UserFirestore userFirestore = new UserFirestore();
    NotificationFirestore notificationFirestore = new NotificationFirestore();

    private final List<NotificationViewData> notificationViewDataList =
            new ArrayList<>();
    private NotificationItemRecyclerAdapter notificationItemRecyclerAdapter;
    private final FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        instantiateView();
    }

    public void instantiateView(){
        notificationToolbar = (Toolbar) findViewById(R.id.notificationToolbar);
        notificationToolbar.setTitle("Notifications");

        setSupportActionBar(notificationToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        notificationItemRecyclerAdapter = new NotificationItemRecyclerAdapter(this ,
                notificationViewDataList);

        notificationRecyclerview = (RecyclerView) findViewById(R.id.notificationRecyclerView);
        notificationRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        notificationRecyclerview.setHasFixedSize(true);

        notificationRecyclerview.setAdapter(notificationItemRecyclerAdapter);

        notificationSwipeRefreshLayout = (SwipeRefreshLayout)
                findViewById(R.id.notificationSwipeRefreshLayout);
        notificationSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNotifications();
            }
        });

        loadNotifications();
    }

    public void loadNotifications(){
        notificationViewDataList.clear();
        notificationFirestore.receiveNotification(firebaseuser.getUid());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final List<Notification> notificationList = notificationFirestore.getNotificationList();
                Log.i(TAG, "loadNotifications: getNotificationList() = " + notificationList);

                for (int i = 0; i < notificationList.size(); i++) {
                    final NotificationType notificationType = notificationList.get(i).getNotificationType();
                    final String notificationId = notificationList.get(i).getId();
                    final String senderId = notificationList.get(i).getSenderId();

                    userFirestore.getUserProfileDataFromFirestore(senderId);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            NotificationViewData notificationViewData = new NotificationViewData();
                            User user = userFirestore.getUser();
                            Profile profile = userFirestore.getProfile();

                            String full_name = user.getFirst_name().toString() + " " + user.getLast_name().toString();
                            notificationViewData.setFull_name(full_name);

                            String image_url = profile.getProfileAvatarUrl();
                            notificationViewData.setNotificationImageUrl(image_url);

                            switch (notificationType) {
                                case FOLLOW:
                                    notificationViewData.setNotificationText(NotificationConstants.FOLLOWED);
                                    break;
                                case PARTNERSHIP_ACCEPTED:
                                    notificationViewData.setNotificationText(NotificationConstants.PARTNERSHIP_ACCEPTED);
                                    break;
                                case PARTNERSHIP_REQUEST:
                                    notificationViewData.setNotificationText(NotificationConstants.PARTNERSHIP_REQUEST);
                                    notificationViewData.setNotificationAcceptButtonName("Accept");
                                    notificationViewData.setNotifcationCancelButtonName("Deny");
                                    break;
                                case IGNORE:
                                    break;
                            }


                            notificationViewData.setNotification_id(notificationId);
                            notificationViewData.setSender_id(senderId);

                            notificationViewDataList.add(notificationViewData);
                            notificationItemRecyclerAdapter.notifyDataSetChanged();
                        }
                    }, 1000);
                }
            }
        },500);
    }

    public void refreshNotifications(){
        notificationViewDataList.clear();
        notificationSwipeRefreshLayout.setColorSchemeResources(R.color.colorLoadRefresh);
        notificationFirestore.receiveNotification(firebaseuser.getUid());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final List<Notification> notificationList = notificationFirestore.getNotificationList();
                Log.i(TAG, "loadNotifications: getNotificationList() = " + notificationList);

                for (int i = 0; i < notificationList.size(); i++) {
                    final NotificationType notificationType = notificationList.get(i).getNotificationType();
                    final String notificationId = notificationList.get(i).getId();
                    final String senderId = notificationList.get(i).getSenderId();

                    userFirestore.getUserProfileDataFromFirestore(senderId);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            NotificationViewData notificationViewData = new NotificationViewData();
                            User user = userFirestore.getUser();
                            Profile profile = userFirestore.getProfile();

                            String full_name = user.getFirst_name().toString() + " " + user.getLast_name().toString();
                            notificationViewData.setFull_name(full_name);

                            String image_url = profile.getProfileAvatarUrl();
                            notificationViewData.setNotificationImageUrl(image_url);

                            switch (notificationType) {
                                case FOLLOW:
                                    notificationViewData.setNotificationText(NotificationConstants.FOLLOWED);
                                    break;
                                case PARTNERSHIP_ACCEPTED:
                                    notificationViewData.setNotificationText(NotificationConstants.PARTNERSHIP_ACCEPTED);
                                    break;
                                case PARTNERSHIP_REQUEST:
                                    notificationViewData.setNotificationText(NotificationConstants.PARTNERSHIP_REQUEST);
                                    notificationViewData.setNotificationAcceptButtonName("Accept");
                                    notificationViewData.setNotifcationCancelButtonName("Deny");
                                    break;
                                case IGNORE:
                                    break;
                            }

                            notificationViewData.setNotification_id(notificationId);
                            notificationViewData.setSender_id(senderId);
                            notificationViewDataList.add(notificationViewData);

                            notificationSwipeRefreshLayout.setColorSchemeResources(R.color.colorFinishRefresh);
                            notificationItemRecyclerAdapter.notifyDataSetChanged();
                        }
                    }, 500);
                }

                notificationSwipeRefreshLayout.setRefreshing(false);
            }
        },500);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

}
