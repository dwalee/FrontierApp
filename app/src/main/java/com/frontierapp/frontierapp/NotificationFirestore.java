package com.frontierapp.frontierapp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NotificationFirestore extends Firestore implements NotificationDAO {
    private static final String TAG = "NotificationFirestore";

    private UserFirestore userFirestore = new UserFirestore();
    private DocumentReference userData = userFirestore.getUserData(userFirestore.getCurrentUserId());
    private CollectionReference userNotifications = userData.collection(INCOMING_ACTIVITY);

    private List<Notification> notificationList = new ArrayList<>();

    @Override
    public Boolean receiveNotification(String currentUserId) {
        notificationList.clear();
        Log.d(TAG, "receiveNotification() called with: currentUserId = [" + currentUserId + "]");

        userNotifications
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "onEvent: ", e);
                        }

                        for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                            Notification notification = new Notification();

                            String notificationId = documentChange.getDocument().getId();
                            Boolean notificationStatus = documentChange.getDocument().getBoolean(READ);
                            String notificationType = documentChange.getDocument().getString(TYPE);
                            String notificationSenderId = documentChange.getDocument().getString(SENDER_ID);
                            String notificationFullName = documentChange.getDocument().getString(FULL_NAME);
                            String notificationProfileUrl = documentChange.getDocument().getString(PROFILE_URL);
                            String notificationMiscId = documentChange.getDocument().getString(MISC_ID);
                            String notificationMiscName = documentChange.getDocument().getString(MISC_NAME);
                            Boolean notificationIgnore = documentChange.getDocument().getBoolean(IGNORE);

                            Log.i(TAG, "onEvent: notificationId = " + notificationId);
                            Log.i(TAG, "onEvent: notificationStatus = " + notificationStatus);
                            Log.i(TAG, "onEvent: notificationType = " + notificationType);
                            Log.i(TAG, "onEvent: notificationSenderId = " + notificationSenderId);
                            Log.i(TAG, "onEvent: notificationFullName = " + notificationFullName);
                            Log.i(TAG, "onEvent: notificationProfileUrl = " + notificationProfileUrl);

                            notification.setId(notificationId);

                            if (notificationStatus.equals(false))
                                notification.setNotificationStatus(NotificationStatus.UNREAD);
                            else if (notificationStatus.equals(true))
                                notification.setNotificationStatus(NotificationStatus.READ);

                            if (notificationType.equals(NotificationType.FOLLOW.getValue()))
                                notification.setNotificationType(NotificationType.FOLLOW);
                            else if (notificationType.equals(NotificationType.PARTNERSHIP_ACCEPTED.getValue()))
                                notification.setNotificationType(NotificationType.PARTNERSHIP_ACCEPTED);
                            else if (notificationType.equals(NotificationType.PARTNERSHIP_REQUEST.getValue()))
                                notification.setNotificationType(NotificationType.PARTNERSHIP_REQUEST);
                            else if (notificationType.equals(NotificationType.SPACE_INVITE.getValue()))
                                notification.setNotificationType(NotificationType.SPACE_INVITE);

                            notification.setSenderId(notificationSenderId);
                            notification.setFullName(notificationFullName);
                            notification.setProfileUrl(notificationProfileUrl);
                            notification.setMiscId(notificationMiscId);
                            notification.setMiscName(notificationMiscName);
                            notification.setIgnore(notificationIgnore);

                            notificationList.add(notification);
                        }

                    }
                });

        Log.i(TAG, "receiveNotification: getNotificationList = " + getNotificationList());
        return true;
    }

    @Override
    public Query getNotifications() {
        Date date = new Date(284014800000L);
        Timestamp timestamp = new Timestamp(date);
        Query query = userNotifications
                .whereEqualTo(IGNORE, false)
                .whereGreaterThan(TIMESTAMP, timestamp)
                .orderBy(TIMESTAMP, Query.Direction.DESCENDING);
        return query;
    }

    @Override
    public Boolean sendNotification(final NotificationType type, final String senderId, final String receiverId) {
        Log.d(TAG, "sendNotification() called with: type = [" + type + "], " +
                "senderId = [" + senderId + "], receiverId = [" + receiverId + "]");

        final DocumentReference receiverUserData = userFirestore.getUserData(receiverId);

        userData.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String full_name = documentSnapshot.getString(UserFirestore.FIRST_NAME)
                                + " " + documentSnapshot.getString(UserFirestore.LAST_NAME);
                        String profile_url = documentSnapshot.getString(UserFirestore.PROFILE_AVATAR);
                        Log.i(TAG, "sendNotification: full_name = " + full_name);

                        final Map<String, Object> notificationData = new HashMap<>();
                        notificationData.put(READ, NotificationStatus.UNREAD.getValue());
                        notificationData.put(TIMESTAMP, FieldValue.serverTimestamp());
                        notificationData.put(TYPE, type.getValue());
                        notificationData.put(SENDER_ID, senderId);
                        notificationData.put(FULL_NAME, full_name);
                        notificationData.put(PROFILE_URL, profile_url);
                        notificationData.put(IGNORE, false);
                        notificationData.put(MISC_ID, "");
                        notificationData.put(MISC_NAME, "");

                        final DocumentReference userNotification = receiverUserData.collection(INCOMING_ACTIVITY)
                                .document();
                        userNotification
                                .set(notificationData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i(TAG, "onSuccess: true");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "onFailure: ", e);
                            }
                        });
                    }
                });
        return true;
    }


    public Boolean sendNotification(final NotificationType type, final String senderId,
                                    final String receiverId, final String miscId, final String miscName) {
        Log.d(TAG, "sendNotification() called with: type = [" + type + "], " +
                "senderId = [" + senderId + "], receiverId = [" + receiverId + "]");

        final DocumentReference receiverUserData = userFirestore.getUserData(receiverId);

        userData.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String full_name = documentSnapshot.getString(UserFirestore.FIRST_NAME)
                                + " " + documentSnapshot.getString(UserFirestore.LAST_NAME);
                        String profile_url = documentSnapshot.getString(UserFirestore.PROFILE_AVATAR);
                        Log.i(TAG, "sendNotification: full_name = " + full_name);

                        final Map<String, Object> notificationData = new HashMap<>();
                        notificationData.put(READ, NotificationStatus.UNREAD.getValue());
                        notificationData.put(TIMESTAMP, FieldValue.serverTimestamp());
                        notificationData.put(TYPE, type.getValue());
                        notificationData.put(SENDER_ID, senderId);
                        notificationData.put(FULL_NAME, full_name);
                        notificationData.put(PROFILE_URL, profile_url);
                        notificationData.put(IGNORE, false);
                        notificationData.put(MISC_ID, "");
                        notificationData.put(MISC_NAME, "");

                        final DocumentReference userNotification = receiverUserData.collection(INCOMING_ACTIVITY)
                                .document();
                        userNotification
                                .set(notificationData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i(TAG, "onSuccess: true");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "onFailure: ", e);
                            }
                        });
                    }
                });
        return true;
    }

    public DocumentReference sendCustomNotification(String receiverId) {

        DocumentReference receiverIncomingActivityDocumentReference = userFirestore
                .getUserData(receiverId)
                .collection(INCOMING_ACTIVITY)
                .document();

        return receiverIncomingActivityDocumentReference;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    @Override
    public Boolean updateNotification(String notificationId, Boolean ignore) {
        Log.d(TAG, "updateNotification() called with: notificationId = " +
                "[" + notificationId + "], ignore = [" + ignore + "]");

        final DocumentReference userNotification = userNotifications.document(notificationId);
        userNotification
                .update(READ, NotificationStatus.READ.getValue(),
                        IGNORE, ignore)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "onSuccess: true");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e);
            }
        });

        return true;
    }

}
