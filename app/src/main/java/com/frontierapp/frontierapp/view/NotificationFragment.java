package com.frontierapp.frontierapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frontierapp.frontierapp.NotificationType;
import com.frontierapp.frontierapp.databinding.FragmentNotificationBinding;
import com.frontierapp.frontierapp.databinding.NotificationItemLayoutBinding;
import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.datasource.FirestoreConstants;
import com.frontierapp.frontierapp.datasource.FirestoreDBReference;
import com.frontierapp.frontierapp.model.Connection;
import com.frontierapp.frontierapp.model.Notification;
import com.frontierapp.frontierapp.model.Notifications;
import com.frontierapp.frontierapp.model.Profile;
import com.frontierapp.frontierapp.viewmodel.NotificationsViewModel;


public class NotificationFragment extends Fragment {
    private static final String TAG = "NotificationFragment";
    private FragmentNotificationBinding fragmentNotificationBinding;
    private RecyclerView recyclerView;
    private NotificationsViewModel notificationsViewModel;
    private NotificationRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentNotificationBinding = FragmentNotificationBinding.inflate(inflater, container, false);
        init();
        return fragmentNotificationBinding.getRoot();
    }


    public void init() {
        recyclerView = fragmentNotificationBinding.notificationRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new NotificationRecyclerViewAdapter(this, getLayoutInflater());
        recyclerView.setAdapter(adapter);
    }

    //
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        notificationsViewModel = ViewModelProviders.of(getActivity()).get(NotificationsViewModel.class);
        notificationsViewModel.retrieveNotifications(getActivity().getApplication());
        notificationsViewModel.getNotifications().observe(getViewLifecycleOwner(), new Observer<Notifications>() {
            @Override
            public void onChanged(@Nullable Notifications notifications) {
                Log.i(TAG, "onChanged: old vs new " + notifications.get(0).getType());
                adapter.submitList(notifications);
            }
        });

    }
}
