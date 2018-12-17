package com.frontierapp.frontierapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.datasource.FirestoreConstants;
import com.frontierapp.frontierapp.datasource.FirestoreDBReference;
import com.frontierapp.frontierapp.listeners.OnSuccessCallback;
import com.frontierapp.frontierapp.model.Chat;
import com.frontierapp.frontierapp.model.Chat_Reference;
import com.frontierapp.frontierapp.model.Chat_References;
import com.frontierapp.frontierapp.model.Chats;
import com.frontierapp.frontierapp.model.Member;
import com.frontierapp.frontierapp.model.Members;
import com.frontierapp.frontierapp.model.Message;
import com.frontierapp.frontierapp.model.Messages;
import com.frontierapp.frontierapp.model.Profile;
import com.frontierapp.frontierapp.model.Profiles;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

public class ChatsRepository implements OnSuccessCallback<Chats> {
    private static final String TAG = "ChatsRepository";
    private MutableLiveData<Chats> ChatsMutableLiveData = new MutableLiveData<>();
    private Firestore<Chat_Reference> chatFirestore;
    private ChatsRepository.ChatAsyncTask chatAsyncTask;
    private final CollectionReference collectionReference = FirestoreDBReference.userCollection
            .document(Firestore.currentUserId)
            .collection(FirestoreConstants.CHAT_REFERENCES);

    public void retrieveChats() {
        chatAsyncTask = new ChatsRepository.ChatAsyncTask();
        chatFirestore = new Firestore(collectionReference);
        chatAsyncTask.execute(this);
    }

    public MutableLiveData<Chats> getChats() {
        return ChatsMutableLiveData;
    }

    @Override
    public void OnSuccess(Chats chats) {
        Log.d(TAG, "OnSuccess() called with: Chats = [" + (chats == null ? 0 : chats.size()) + "]");

        ChatsMutableLiveData.setValue(chats);
    }

    private class ChatAsyncTask extends AsyncTask<OnSuccessCallback<Chats>, Void, Void> {
        private static final String TAG = "ChatAsyncTask";
        private int index = 0;

        @Override
        protected Void doInBackground(final OnSuccessCallback<Chats>... onSuccessCallbacks) {

            OnSuccessCallback<Chat_References> chatReferencesOnSuccessCallback = new OnSuccessCallback<Chat_References>() {
                @Override
                public void OnSuccess(Chat_References chat_references) {
                    Log.i(TAG, "chat reference = " + (chat_references == null ? 0 : chat_references.size()));
                    final Chats chatList = new Chats();
                    int loop_count = 0;
                    for (final Chat_Reference chat_reference : chat_references) {
                        Log.i(TAG, "loop_count = " + ++loop_count);
                        final DocumentReference documentReference = chat_reference.getChat_ref();
                        final Chat thisChat = new Chat();
                        thisChat.setChat_ref(documentReference);

                        OnSuccessCallback<Members> membersOnSuccessCallback = new OnSuccessCallback<Members>() {
                            @Override
                            public void OnSuccess(final Members members) {

                                final Profiles profiles = new Profiles();
                                for (Member member : members) {
                                    Log.d(TAG, "OnSuccess() called with: members = [" + (members == null ? 0 : members.size()) + "]");
                                    final DocumentReference docRef = member.getMember();
                                    String theId = docRef.getId().toString();
                                    String myId = Firestore.currentUserId;
                                    //Log.i(TAG, "theId = " + theId);
                                    //Log.i(TAG, "myId = " + myId);

                                    index = 0;
                                    if (!myId.equals(theId)) {
                                        OnSuccessCallback<Profile> profileOnSuccessCallback = new OnSuccessCallback<Profile>() {
                                            @Override
                                            public void OnSuccess(Profile profile) {
                                                Log.d(TAG, "OnSuccess() called with: profile = [" + (profile == null ? "" : profile.getFirst_name()) + "]");
                                                profiles.add(profile);


                                                if (index == (members.size() - 2)) {
                                                    thisChat.setProfiles(profiles);

                                                    OnSuccessCallback<Messages> messagesOnSuccessCallback = new OnSuccessCallback<Messages>() {
                                                        @Override
                                                        public void OnSuccess(Messages messages) {
                                                            Log.i(TAG, "Message call count = " + ++call_count);
                                                            Log.d(TAG, "OnSuccess() called with: messages = [" + (messages == null ? 0 : messages.size()) + "]");
                                                            thisChat.setMessage(messages.get(0));

                                                            if (chatList.contains(thisChat)) {
                                                                chatList.set(chatList.indexOf(thisChat), thisChat);
                                                            } else {
                                                                chatList.add(thisChat);
                                                            }

                                                            onSuccessCallbacks[0].OnSuccess(chatList);
                                                        }
                                                    };

                                                    Query messageQuery = documentReference.collection("Messages").orderBy("sent", Query.Direction.DESCENDING).limit(1);
                                                    Firestore<Message> messagesFirestore = new Firestore<>(messageQuery);
                                                    messagesFirestore.retrieveList(messagesOnSuccessCallback, Message.class, new Messages());
                                                }
                                                index++;

                                                /////////////////////////////////////////////////////////////////////////////////////////////
                                            }
                                        };

                                        Firestore<Profile> profileFirestore = new Firestore<>(docRef);
                                        profileFirestore.retrieve(profileOnSuccessCallback, Profile.class);
                                    }

                                }

                            }
                        };
                        Query memberQuery = documentReference.collection("Members");
                        Firestore<Member> membersFirestore = new Firestore<>(memberQuery);
                        membersFirestore.retrieveList(membersOnSuccessCallback, Member.class, new Members());

                    }
                }
            };

            chatFirestore.retrieveList(chatReferencesOnSuccessCallback, Chat_Reference.class, new Chat_References());
            return null;
        }


    }

    private int call_count = 0;
}
