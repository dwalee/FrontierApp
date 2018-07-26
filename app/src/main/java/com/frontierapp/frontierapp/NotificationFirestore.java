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
    private List<Notification> notificationList = new ArrayList<>();

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
                                    Boolean notificationStatus = documentChange.getDocument().getBoolean("read");
                                    String notificationType = documentChange.getDocument().getString("type");
                                    String notificationSenderId = documentChange.getDocument().getString("senderId");

                                    Log.i(TAG, "onEvent: notificationId = " + notificationId);
                                    Log.i(TAG, "onEvent: notificationStatus = " + notificationStatus);
                                    Log.i(TAG, "onEvent: notificationType = " + notificationType);
                                    Log.i(TAG, "onEvent: notificationSenderId = " + notificationSenderId);

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

                                    notification.setSenderId(notificationSenderId);

                                    notificationList.add(notification);
                                }

                            }
                        });

        //Log.i(TAG, "receiveNotification: type = " + notificationList.get(0).getNotificationType().getValue());

        Log.i(TAG, "receiveNotification: getNotificationList = " + getNotificationList());
        return true;
    }

    @Override
    public Boolean sendNotification(NotificationType type, String senderId, String receiverId) {
        Log.d(TAG, "sendNotification() called with: type = [" + type + "], " +
                "senderId = [" + senderId + "], receiverId = [" + receiverId + "]");

        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(receiverId);

        final Map<String, Object> notificationData = new HashMap<>();
        notificationData.put("read", NotificationStatus.UNREAD.getValue());
        notificationData.put("timestamp", FieldValue.serverTimestamp());
        notificationData.put("type", type.getValue());
        notificationData.put("senderId", senderId);

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
