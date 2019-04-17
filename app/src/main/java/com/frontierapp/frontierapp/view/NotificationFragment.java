package com.frontierapp.frontierapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.recyclerview.extensions.ListAdapter;
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
import com.frontierapp.frontierapp.listeners.NotificationClickListener;
import com.frontierapp.frontierapp.model.Connection;
import com.frontierapp.frontierapp.model.Notification;
import com.frontierapp.frontierapp.model.Notifications;
import com.frontierapp.frontierapp.model.Profile;
import com.frontierapp.frontierapp.util.NotificationUtil;
import com.frontierapp.frontierapp.viewmodel.NotificationsViewModel;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.HashMap;
import java.util.Map;


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
        adapter = new NotificationRecyclerViewAdapter(getLayoutInflater());
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

    private static final DiffUtil.ItemCallback<Notification> DIFF_CALLBACK = new DiffUtil.ItemCallback<Notification>() {
        @Override
        public boolean areItemsTheSame(Notification oldItem, Notification newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(Notification oldItem, Notification newItem) {
            long old_time = oldItem.getUpdated().getTime();
            long new_time = newItem.getUpdated().getTime();

            Profile oldProfile = oldItem.getProfile();
            Profile newProfile = newItem.getProfile();

            String oldType = oldItem.getType();
            String newType = newItem.getType();

            Log.i(TAG, "areContentsTheSame: old vs new " + oldType + " " + newType);

            return oldItem.isRead() == newItem.isRead() && old_time == new_time &&
                    oldItem.sameContent(newItem);
        }
    };

    public class NotificationRecyclerViewAdapter extends ListAdapter<Notification, NotificationViewHolder> {
        private static final String TAG = "NotificationRecyclerVie";
        NotificationItemLayoutBinding binding;
        LayoutInflater inflater;

        public NotificationRecyclerViewAdapter(LayoutInflater inflater) {
            super(DIFF_CALLBACK);
            this.inflater = inflater;
        }

        @NonNull
        @Override
        public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            binding = NotificationItemLayoutBinding.inflate(inflater, parent, false);
            return new NotificationViewHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
            NotificationItemLayoutBinding binding = DataBindingUtil.getBinding(holder.itemView);
            Notification notification = getItem(position);
            Profile profile = notification.getProfile();

            binding.setNotification(notification);
            binding.setProfile(profile);
            binding.setListener(new NotificationClickListener(binding));
            binding.setButtonListener(new ButtonClickListener(binding));
        }

    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        public NotificationViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ButtonClickListener implements View.OnClickListener {
        NotificationItemLayoutBinding binding;

        public ButtonClickListener(NotificationItemLayoutBinding binding) {
            this.binding = binding;
        }

        @Override
        public void onClick(View view) {
            NotificationType type;
            Notification notification = binding.getNotification();
            if (view.getId() == binding.notificationPositiveButton.getId()) {
                Toast.makeText(binding.getRoot().getContext(), "Positive button pressed!", Toast.LENGTH_SHORT).show();

                type = NotificationType.valueOf(binding.getNotification().getType());

                switch (type) {
                    case PARTNERSHIP_REQUEST:
                        Connection newConnection = new Connection();
                        newConnection.setPartner(true);
                        newConnection.setFavorite(true);
                        newConnection.setFollower(true);

                        FirestoreDBReference.userCollection.document(Firestore.currentUserId)
                                .collection(FirestoreConstants.CONNECTIONS)
                                .document(binding.getProfile().getUser_ref().getId())
                                .set(newConnection);

                        FirestoreDBReference.userCollection.document(binding.getProfile().getUser_ref().getId())
                                .collection(FirestoreConstants.CONNECTIONS)
                                .document(Firestore.currentUserId)
                                .set(newConnection);


                        binding.notificationPositiveButton.setVisibility(View.GONE);
                        binding.notificationNegativeButton.setVisibility(View.GONE);
                        notification.getNotification_ref().update("ignore", true);

                        notification.setType(NotificationType.PARTNERSHIP_ACCEPTED_BY_YOU.getValue());
                        FirestoreDBReference.userCollection.document(Firestore.currentUserId)
                                .collection(FirestoreConstants.NOTIFICATIONS)
                                .document()
                                .set(binding.getNotification());
                        break;
                    case SPACE_INVITE:
                        binding.notificationPositiveButton.setVisibility(View.GONE);
                        binding.notificationNegativeButton.setVisibility(View.GONE);
                        break;
                }

            } else if (view.getId() == binding.notificationNegativeButton.getId()) {
                Toast.makeText(binding.getRoot().getContext(), "Negative button pressed!", Toast.LENGTH_SHORT).show();
                type = NotificationType.valueOf(binding.getNotification().getType());

                switch (type) {
                    case PARTNERSHIP_REQUEST:
                        binding.getNotification().getNotification_ref().update("ignore", true);
                        binding.notificationPositiveButton.setVisibility(View.GONE);
                        binding.notificationNegativeButton.setVisibility(View.GONE);
                        break;
                    case SPACE_INVITE:
                        binding.notificationPositiveButton.setVisibility(View.GONE);
                        binding.notificationNegativeButton.setVisibility(View.GONE);
                        break;
                }
            }
        }
    }
}
