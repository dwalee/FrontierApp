package com.frontierapp.frontierapp;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private static final String TAG = "NotificationActivity";
    RecyclerView notificationRecyclerview;
    SwipeRefreshLayout notificationSwipeRefreshLayout;
    Toolbar notificationToolbar;

    private final List<NotificationViewData> notificationViewDataList =
            new ArrayList<>();
    private NotificationItemRecyclerAdapter notificationItemRecyclerAdapter;

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

            }
        });

        loadNotifications();
    }

    public void loadNotifications(){
        NotificationViewData notificationViewData = new NotificationViewData();
        notificationViewData.setNotificationImageUrl(
                "https://firebasestorage.googleapis.com/v0/b/frontierapp-65ac1.appspot.com/o/" +
                        "UserImages%2FUsers%2FNpDh1YWoGAc7Mn39itFNwpQ8pKg2%2F1531215199981." +
                        "gif?alt=media&token=856714ab-af9d-4874-938a-aa24717ee6af");
        notificationViewData.setNotificationText("Yoshua Isreal has followed you.");

        notificationViewDataList.add(notificationViewData);

        notificationItemRecyclerAdapter.notifyDataSetChanged();
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
