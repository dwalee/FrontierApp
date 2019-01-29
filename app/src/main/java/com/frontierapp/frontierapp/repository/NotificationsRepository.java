package com.frontierapp.frontierapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.datasource.FirestoreConstants;
import com.frontierapp.frontierapp.datasource.FirestoreDBReference;
import com.frontierapp.frontierapp.listeners.OnSuccessCallback;
import com.frontierapp.frontierapp.model.Notification;
import com.frontierapp.frontierapp.model.Notifications;
import com.frontierapp.frontierapp.model.Profile;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

public class NotificationsRepository implements OnSuccessCallback<Notifications> {
    private MutableLiveData<Notifications> notificationsMutableLiveData = new MutableLiveData<>();
    private Firestore<Notification> notificationFirestore;
    private NotificationAsyncTask notificationAsyncTask;
    private final CollectionReference collectionReference = FirestoreDBReference.userCollection
            .document(Firestore.currentUserId)
            .collection(FirestoreConstants.NOTIFICATIONS);
    private final Query query = collectionReference.whereEqualTo(FirestoreConstants.IGNORE, false).orderBy("updated", Query.Direction.DESCENDING);

    public void retrieveNotifications() {
        notificationAsyncTask = new NotificationAsyncTask();
        notificationFirestore = new Firestore(query);
        notificationAsyncTask.execute(this);
    }

    public MutableLiveData<Notifications> getNotifications() {
        return notificationsMutableLiveData;
    }

    @Override
    public void OnSuccess(Notifications notifications) {
        notifications.reverseSort();
        notificationsMutableLiveData.setValue(notifications);
    }

    private class NotificationAsyncTask extends AsyncTask<OnSuccessCallback<Notifications>, Void, Void> {
        private static final String TAG = "NotificationAsyncTask";
        private int index = 0;

        @Override
        protected Void doInBackground(final OnSuccessCallback<Notifications>... onSuccessCallbacks) {
            OnSuccessCallback<Notifications> notificationsOnSuccessCallback = new OnSuccessCallback<Notifications>() {
                @Override
                public void OnSuccess(final Notifications notifications) {
                    if (!notifications.isEmpty()) {
                        final Notifications notificationsList = new Notifications();

                        index = 0;
                        for (final Notification notification : notifications) {
                            DocumentReference documentReference = notification.getSender();

                            OnSuccessCallback<Profile> callback = new OnSuccessCallback<Profile>() {
                                @Override
                                public void OnSuccess(Profile profile) {
                                    notification.setProfile(profile);
                                    if(notificationsList.contains(notification)){
                                        notificationsList.set(notificationsList.indexOf(notification), notification);
                                    }else{
                                        notificationsList.add(notification);
                                    }

                                    if(index == (notifications.size() - 1))
                                        onSuccessCallbacks[0].OnSuccess(notificationsList);

                                    index++;
                                }
                            };

                            Firestore<Profile> firestore = new Firestore<>(documentReference);
                            firestore.retrieve(callback, Profile.class);
                        }
                    } else {
                        onSuccessCallbacks[0].OnSuccess(notifications);
                    }
                }
            };
            notificationFirestore.retrieveList(notificationsOnSuccessCallback, Notification.class, new Notifications());
            return null;
        }
    }
}
