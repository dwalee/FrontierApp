package com.frontierapp.frontierapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.frontierapp.frontierapp.model.Chats;
import com.frontierapp.frontierapp.repository.ChatsRepository;

public class ChatsViewModel extends ViewModel {
    private MutableLiveData<Chats> notificationsMutableLiveData;
    private ChatsRepository notificationRepository = new ChatsRepository();

    public void retrieveChats(){
        notificationRepository.retrieveChats();
        notificationsMutableLiveData = notificationRepository.getChats();
    }

    public MutableLiveData<Chats> getChats(){
        return notificationsMutableLiveData;
    }
}
