package com.frontierapp.frontierapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.listeners.OnSuccessCallback;
import com.frontierapp.frontierapp.model.Message;
import com.frontierapp.frontierapp.model.Messages;
import com.frontierapp.frontierapp.model.Profile;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

public class MessageRepository implements OnSuccessCallback<Messages> {
    private static final String TAG = "MessageRepository";
    private final MutableLiveData<Messages> messagesMutableLiveData = new MutableLiveData<>();
    private MessagesAsyncTask messagesAsyncTask;
    private Firestore messageFirestore;

    public MessageRepository() {
    }

    public void retrieveMessages(Query query) {
        messagesAsyncTask = new MessagesAsyncTask();
        messageFirestore = new Firestore(query);
        messagesAsyncTask.execute(this);
    }


    public MutableLiveData<Messages> getMessages() {
        return messagesMutableLiveData;
    }

    @Override
    public void OnSuccess(Messages messages) {
        int index = 0;
        for (Message message:messages){
            Log.d(TAG, "OnSuccess() called with: messages = [" + message.getMessage() + ' ' + index++ + " ]");
        }

        messagesMutableLiveData.setValue(messages);
    }

    public class MessagesAsyncTask extends AsyncTask<OnSuccessCallback<Messages>, Void, Void> {
        private int index = 0;
        @Override
        protected Void doInBackground(final OnSuccessCallback<Messages>... onSuccessCallbacks) {

            OnSuccessCallback<Messages> callbackMsgs = new OnSuccessCallback<Messages>() {
                @Override
                public void OnSuccess(final Messages messages) {
                    if (!messages.isEmpty()) {
                        final Messages msgs = new Messages();
                        index = 0;
                        for (final Message msg : messages) {

                            DocumentReference documentReference = msg.getSender();

                            OnSuccessCallback<Profile> callback = new OnSuccessCallback<Profile>() {
                                @Override
                                public void OnSuccess(Profile profile) {
                                    msg.setProfile(profile);
                                    msgs.add(msg);
                                    Log.i(TAG, "OnSuccess: index = " + index);
                                    if(index == (messages.size() - 1) )
                                        onSuccessCallbacks[0].OnSuccess(msgs);
                                    index++;
                                }
                            };

                            Firestore<Profile> firestore = new Firestore<>(documentReference);
                            firestore.retrieve(callback, Profile.class);

                        }
                    } else {
                        onSuccessCallbacks[0].OnSuccess(messages);
                    }
                }
            };

            messageFirestore.retrieveList(callbackMsgs, Message.class, new Messages());

            return null;
        }
    }
}
