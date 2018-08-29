package com.frontierapp.frontierapp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class NotificationFirestore extends Notification implements NotificationDAO {
    private static final String TAG = "NotificationFirestore";
    protected static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
    protected static DocumentReference userData;
    protected static DocumentReference currentUserData;
    private List<Notification> notificationList = new ArrayList<>();

    private final String READ = "read";
    private final String TIMESTAMP = "timestamp";
    private final String TYPE = "type";
    private final String SENDER_ID = "senderId";
    private final String FULL_NAME = "full_name";
    private final String PROFILE_URL = "profile_url";

    @Override
    public Boolean receiveNotification(String currentUserId) {
        notificationList.clear();
        Log.d(TAG, "receiveNotification() called with: currentUserId = [" + currentUserId + "]");
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserId);

        final CollectionReference userNotifications = userData.collection("Incoming Activity");
        userNotifications
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                    if(e != null){
                        Log.w(TAG, "onEvent: ", e);
                    }

                    for(DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()){
                        Notification notification = new Notification();

                        String notificationId = documentChange.getDocument().getId();
                        Boolean notificationStatus = documentChange.getDocument().getBoolean(READ);
                        String notificationType = documentChange.getDocument().getString(TYPE);
                        String notificationSenderId = documentChange.getDocument().getString(SENDER_ID);
                        String notificationFullName = documentChange.getDocument().getString(FULL_NAME);
                        String notificationProfileUrl = documentChange.getDocument().getString(PROFILE_URL);

                        Log.i(TAG, "onEvent: notificationId = " + notificationId);
                        Log.i(TAG, "onEvent: notificationStatus = " + notificationStatus);
                        Log.i(TAG, "onEvent: notificationType = " + notificationType);
                        Log.i(TAG, "onEvent: notificationSenderId = " + notificationSenderId);
                        Log.i(TAG, "onEvent: notificationFullName = " + notificationFullName);
                        Log.i(TAG, "onEvent: notificationProfileUrl = " + notificationProfileUrl );

                        notification.setId(notificationId);

                        if(notificationStatus.equals(false))
                            notification.setNotificationStatus(NotificationStatus.UNREAD);
                        else if(notificationStatus.equals(true))
                            notification.setNotificationStatus(NotificationStatus.READ);

                        if(notificationType.equals(NotificationType.FOLLOW.getValue()))
                            notification.setNotificationType(NotificationType.FOLLOW);
                        else if(notificationType.equals(NotificationType.PARTNERSHIP_ACCEPTED.getValue()))
                            notification.setNotificationType(NotificationType.PARTNERSHIP_ACCEPTED);
                        else if(notificationType.equals(NotificationType.PARTNERSHIP_REQUEST.getValue()))
                            notification.setNotificationType(NotificationType.PARTNERSHIP_REQUEST);
                        else if(notificationType.equals(NotificationType.IGNORE.getValue()))
                            notification.setNotificationType(NotificationType.IGNORE);

                        notification.setSenderId(notificationSenderId);
                        notification.setFullName(notificationFullName);
                        notification.setProfileUrl(notificationProfileUrl);

                        notificationList.add(notification);
                    }

                }
            });

        //Log.i(TAG, "receiveNotification: type = " + notificationList.get(0).getNotificationType().getValue());

        Log.i(TAG, "receiveNotification: getNotificationList = " + getNotificationList());
        return true;
    }

    @Override
    public Notifications getNotifications() {
        return null;
    }

    @Override
    public Boolean sendNotification(final NotificationType type, final String senderId, final String receiverId) {
        Log.d(TAG, "sendNotification() called with: type = [" + type + "], " +
                "senderId = [" + senderId + "], receiverId = [" + receiverId + "]");

        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(receiverId);

        currentUserData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(firebaseuser.getUid());

        currentUserData.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String full_name = documentSnapshot.getString("User.first_name") + " " + documentSnapshot.getString("User.last_name");
                        String profile_url = documentSnapshot.getString("Profile.profile_avatar");
                        Log.i(TAG, "sendNotification: full_name = " + full_name);

                        final Map<String, Object> notificationData = new HashMap<>();
                        notificationData.put(READ, NotificationStatus.UNREAD.getValue());
                        notificationData.put(TIMESTAMP, FieldValue.serverTimestamp());
                        notificationData.put(TYPE, type.getValue());
                        notificationData.put(SENDER_ID, senderId);
                        notificationData.put(FULL_NAME, full_name);
                        notificationData.put(PROFILE_URL, profile_url);

                        final DocumentReference userNotification = userData.collection("Incoming Activity").document();
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

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    @Override
    public Boolean updateNotification(NotificationType type, String notificationId) {
        Log.d(TAG, "updateNotification() called with: type = [" + type + "], " +
                "notificationId = [" + notificationId + "]");

        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(firebaseuser.getUid());

        final DocumentReference userNotification = userData.collection("Incoming Activity").document(notificationId);
        userNotification
                .update("read", NotificationStatus.READ.getValue(),
                        "type", type.getValue())
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

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

}
