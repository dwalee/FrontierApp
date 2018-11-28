package com.frontierapp.frontierapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.frontierapp.frontierapp.model.Messages;
import com.frontierapp.frontierapp.repository.MessageRepository;

public class MessageViewModel extends ViewModel {
    private MutableLiveData<Messages> messagesMutableLiveData;
    private MessageRepository messageRepository;

    public MessageViewModel() {
        messageRepository = new MessageRepository();
        messagesMutableLiveData = messageRepository.getMessages();
    }

    public void retrieveMessages(){
        messageRepository.retriveMessages();
    }

    public MutableLiveData<Messages> getMessages(){
        return messagesMutableLiveData;
    }
}
