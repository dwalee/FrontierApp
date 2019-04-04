package com.frontierapp.frontierapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


import com.frontierapp.frontierapp.model.Notifications;
import com.frontierapp.frontierapp.repository.NotificationsRepository;

public class NotificationsViewModel extends ViewModel {
    private MutableLiveData<Notifications> notificationsMutableLiveData;
    private NotificationsRepository notificationsRepository;

    public NotificationsViewModel() {

    }

    public void retrieveNotifications(Application application){
        notificationsRepository = new NotificationsRepository(application);
        //notificationsRepository.retrieveNotifications();
        notificationsMutableLiveData = notificationsRepository.getNotifications();
    }

    public MutableLiveData<Notifications> getNotifications(){
        return notificationsMutableLiveData;
    }
}
