package com.frontierapp.frontierapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.frontierapp.frontierapp.model.Message;
import com.frontierapp.frontierapp.model.Messages;
import com.frontierapp.frontierapp.repository.MessageRepository;
import com.google.firebase.firestore.Query;

public class MessageViewModel extends ViewModel {
    private static final String TAG = "MessageViewModel";
    private MutableLiveData<Messages> messagesMutableLiveData;
    private MessageRepository messageRepository;

    public MessageViewModel() {
        messageRepository = new MessageRepository();

    }

    public void retrieveMessages(Query query){
        messageRepository.retrieveMessages(query);
        messagesMutableLiveData = messageRepository.getMessages();
    }

    public MutableLiveData<Messages> getMessages(){
        int index = 0;
        if(messagesMutableLiveData.getValue() != null) {
            for (Message message : messagesMutableLiveData.getValue()) {
                Log.d(TAG, "OnSuccess() called with: messages = [" + message.getMessage() + ' ' + index++ + " ]");
            }
        }
        return messagesMutableLiveData;
    }
}
