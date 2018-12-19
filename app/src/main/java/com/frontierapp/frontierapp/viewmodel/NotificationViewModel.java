package com.frontierapp.frontierapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


import com.frontierapp.frontierapp.model.Notifications;
import com.frontierapp.frontierapp.repository.NotificationRepository;
import com.google.firebase.firestore.Query;

public class NotificationViewModel extends ViewModel {
    private MutableLiveData<Notifications> notificationsMutableLiveData;
    private NotificationRepository notificationRepository = new NotificationRepository();

    public void retrieveNotifications(){
        notificationRepository.retrieveNotifications();
        notificationsMutableLiveData = notificationRepository.getNotifications();
    }

    public MutableLiveData<Notifications> getNotifications(){
        return notificationsMutableLiveData;
    }
}
