package com.frontierapp.frontierapp.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.frontierapp.frontierapp.datasource.MessageFirestore;
import com.frontierapp.frontierapp.model.Messages;

public class MessageRepository {
    private final MutableLiveData<Messages> messagesMutableLiveData = new MutableLiveData<>();
    private MessagesAsyncTask messagesAsyncTask = new MessagesAsyncTask();

    public MessageRepository() {

    }

    public void retriveMessages(){
        messagesAsyncTask.execute();
    }

    public MutableLiveData<Messages> getMessages(){
        return messagesMutableLiveData;
    }

    public class MessagesAsyncTask extends AsyncTask<Void, Void, Void> {
        MessageFirestore messageFirestore = new MessageFirestore();

        @Override
        protected Void doInBackground(Void... voids) {
            messageFirestore.retrieveMessages(new MessageFirestore.OnSuccessCallback() {
                @Override
                public void onSuccess(Messages messages) {
                    messagesMutableLiveData.setValue(messages);
                }
            });
            return null;
        }
    }
}
