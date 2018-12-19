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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frontierapp.frontierapp.NotificationType;
import com.frontierapp.frontierapp.databinding.FragmentNotificationBinding;
import com.frontierapp.frontierapp.databinding.NotificationItemLayoutBinding;
import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.datasource.FirestoreConstants;
import com.frontierapp.frontierapp.datasource.FirestoreDBReference;
import com.frontierapp.frontierapp.listeners.NotificationClickListener;
import com.frontierapp.frontierapp.model.Notification;
import com.frontierapp.frontierapp.model.Notifications;
import com.frontierapp.frontierapp.model.Profile;
import com.frontierapp.frontierapp.viewmodel.NotificationViewModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;


public class NotificationFragment extends Fragment {
    private static final String TAG = "NotificationFragment";
    private FragmentNotificationBinding fragmentNotificationBinding;
    private RecyclerView recyclerView;
    private NotificationViewModel notificationViewModel;
    private NotificationRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentNotificationBinding = FragmentNotificationBinding.inflate(inflater, container, false);
        init();
        return fragmentNotificationBinding.getRoot();
    }


    public void init(){
        recyclerView = fragmentNotificationBinding.notificationRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new NotificationRecyclerViewAdapter(getLayoutInflater());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        notificationViewModel = ViewModelProviders.of(getActivity()).get(NotificationViewModel.class);
        notificationViewModel.retrieveNotifications();
        notificationViewModel.getNotifications().observe(getActivity(), new Observer<Notifications>() {
            @Override
            public void onChanged(@Nullable Notifications notifications) {
                adapter.submitList(notifications);
            }
        });

    }

    private static final DiffUtil.ItemCallback<Notification>  DIFF_CALLBACK = new DiffUtil.ItemCallback<Notification>() {
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

            return oldItem.isRead() == newItem.isRead() && old_time == new_time &&
                    oldProfile.equals(newProfile);
        }
    };

    public class NotificationRecyclerViewAdapter extends ListAdapter<Notification, NotificationViewHolder>{
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
        }

    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{

        public NotificationViewHolder(View itemView) {
            super(itemView);
        }
    }
}
