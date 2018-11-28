package com.frontierapp.frontierapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
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

public class SpaceFirestore extends Firestore implements SpaceFirestoreConstants{
    private static final String TAG = "SpaceFirestore";
    protected CollectionReference spaceData = userInformationDatabase
            .document("Spaces")
            .collection("Space");
    UserFirestore userFirestore = new UserFirestore();
    NotificationFirestore notificationFirestore = new NotificationFirestore();

    protected Context context;
    protected String currentSpaceId;
    protected String currentSpaceName;

    public SpaceFirestore(Context context) {
        this.context = context;
    }

    public void createSpace(Space space, final String currentUserId) {
        final Map<String, Object> space_details = new HashMap<>();
        space_details.put(NAME, space.getName());
        space_details.put(PURPOSE, space.getPurpose());
        space_details.put(IS_PRIVATE, space.getPrivate());
        space_details.put(DATE_CREATED, FieldValue.serverTimestamp());

        final DocumentReference spaceDocumentReference = spaceData.document();
        String generatedSpaceId = spaceDocumentReference.getId();
        setCurrentSpaceId(generatedSpaceId);
        setCurrentSpaceName(space_details.get(NAME).toString());
        Log.i(TAG, "createSpace: spaceId = " + generatedSpaceId);
        spaceDocumentReference
                .set(space_details)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addSpaceCreator(currentUserId, space_details);
                        Toast.makeText(context, "Space has been created.", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show();
                Log.w(TAG, "onFailure: ", e);
            }
        });
    }

    public void addSpaceCreator(final String userId, final Map<String, Object> space_details) {
        final Map<String, Object> member_details = new HashMap<>();

        final DocumentReference spaceDocumentReference = spaceData.document(getCurrentSpaceId());
        final DocumentReference userDocumentReference = userFirestore.getUserData(userId);
        userDocumentReference
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String full_name = documentSnapshot.getString("User.first_name") +
                                " " + documentSnapshot.getString("User.last_name");
                        member_details.put(MEMBER_NAME, full_name);
                        member_details.put(MEMBER_JOINED, FieldValue.serverTimestamp());
                        member_details.put(MEMBER_TYPE, "creator");
                        member_details.put(MEMBER_PROFILE_URL, documentSnapshot.getString("Profile.profile_avatar"));

                        spaceDocumentReference
                                .collection(MEMBERS)
                                .document(userId)
                                .set(member_details)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i(TAG, "onSuccess: space creator added!");
                                    }
                                });

                        userDocumentReference
                                .collection("Spaces")
                                .document(getCurrentSpaceId())
                                .set(space_details)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i(TAG, "onSuccess: space name added to current user info!");
                                    }
                                });
                    }
                });

    }

    public void addSpaceMember(final String userId, final NotificationViewData notificationViewData) {
        final Map<String, Object> member_details = new HashMap<>();
        final String spaceId = notificationViewData.getMiscId();
        final String receiverId = notificationViewData.getSender_id();
        final String spaceName = notificationViewData.getMiscName();

        final DocumentReference spaceDocumentReference = spaceData.document(spaceId);
        final DocumentReference userDocumentReference = userFirestore.getUserData(userId);
        userDocumentReference
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String full_name = documentSnapshot.getString("User.first_name") +
                                " " + documentSnapshot.getString("User.last_name");
                        member_details.put(MEMBER_NAME, full_name);
                        member_details.put(MEMBER_JOINED, FieldValue.serverTimestamp());
                        member_details.put(MEMBER_TYPE, "normal");
                        member_details.put(MEMBER_PROFILE_URL, documentSnapshot.getString("Profile.profile_avatar"));

                        //Add user as a member to space
                        spaceDocumentReference
                                .collection(MEMBERS)
                                .document(userId)
                                .set(member_details)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i(TAG, "onSuccess: space member joined!");

                                        final DocumentReference userDocumentReference = userFirestore.getUserData(userFirestore.getCurrentUserId());
                                        userDocumentReference
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        String full_name = documentSnapshot.getString("User.first_name")
                                                                + " " + documentSnapshot.getString("User.last_name");
                                                        String profile_url = documentSnapshot.getString("Profile.profile_avatar");

                                                        final Map<String, Object> notificationData = new HashMap<>();
                                                        notificationData.put(NotificationFirestore.READ, NotificationStatus.UNREAD.getValue());
                                                        notificationData.put(NotificationFirestore.TIMESTAMP, FieldValue.serverTimestamp());
                                                        notificationData.put(NotificationFirestore.TYPE, NotificationType.JOINED_SPACE.getValue());
                                                        Log.i(TAG, "onSuccess: senderId = " + userFirestore.getCurrentUserId());
                                                        notificationData.put(NotificationFirestore.SENDER_ID, userFirestore.getCurrentUserId());
                                                        notificationData.put(NotificationFirestore.FULL_NAME, full_name);
                                                        notificationData.put(NotificationFirestore.PROFILE_URL, profile_url);
                                                        notificationData.put("misc_id", spaceId);
                                                        notificationData.put("misc_name", spaceName);
                                                        notificationData.put("ignore", false);

                                                        DocumentReference sendNotification = notificationFirestore.sendCustomNotification(receiverId);
                                                        sendNotification
                                                                .set(notificationData)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Log.i(TAG, "onSuccess: joined space " + receiverId);
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });

                        //Remove user from pending
                        spaceDocumentReference
                                .collection(PENDING)
                                .document(userId)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i(TAG, "onSuccess: pending deleted!");

                                    }
                                });

                        //add space info to users space collection
                        spaceDocumentReference
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        final Map<String, Object> space_details = new HashMap<>();
                                        space_details.put(NAME, documentSnapshot.getString(NAME));
                                        space_details.put(PURPOSE, documentSnapshot.getString(PURPOSE));
                                        space_details.put(IS_PRIVATE, documentSnapshot.getBoolean(IS_PRIVATE));
                                        space_details.put(DATE_CREATED, FieldValue.serverTimestamp());

                                        userDocumentReference
                                                .collection("Spaces")
                                                .document(spaceId)
                                                .set(space_details)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.i(TAG, "onSuccess: space name added to current user info!");
                                                    }
                                                });
                                    }
                                });


                    }
                });

    }

    public void removeSpacePendingMember(final String spaceId, final String pendingId){
        final DocumentReference spaceDocumentReference = spaceData.document(spaceId);

        //Remove user from pending
        spaceDocumentReference
                .collection(PENDING)
                .document(pendingId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "onSuccess: pending deleted!");

                    }
                });
    }

    public void inviteSpaceMembers(final String currentSpaceId, final List<String> ids, final String currentSpaceName) {

        final Map<String, String> pending_details = new HashMap<>();
        pending_details.put("Status", "Pending");

        final DocumentReference spaceDocumentReference = spaceData.document(currentSpaceId);

        for (final String pending_id : ids) {
            spaceDocumentReference.collection(PENDING)
                    .document(pending_id)
                    .set(pending_details)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            final DocumentReference userDocumentReference = userFirestore.getUserData(userFirestore.getCurrentUserId());
                            userDocumentReference
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            String full_name = documentSnapshot.getString("User.first_name")
                                                    + " " + documentSnapshot.getString("User.last_name");
                                            String profile_url = documentSnapshot.getString("Profile.profile_avatar");

                                            final Map<String, Object> notificationData = new HashMap<>();
                                            notificationData.put(NotificationFirestore.READ, NotificationStatus.UNREAD.getValue());
                                            notificationData.put(NotificationFirestore.TIMESTAMP, FieldValue.serverTimestamp());
                                            notificationData.put(NotificationFirestore.TYPE, NotificationType.SPACE_INVITE.getValue());
                                            Log.i(TAG, "onSuccess: senderId = " + userFirestore.getCurrentUserId());
                                            notificationData.put(NotificationFirestore.SENDER_ID, userFirestore.getCurrentUserId());
                                            notificationData.put(NotificationFirestore.FULL_NAME, full_name);
                                            notificationData.put(NotificationFirestore.PROFILE_URL, profile_url);
                                            notificationData.put("misc_id", currentSpaceId);
                                            notificationData.put("misc_name", currentSpaceName);
                                            notificationData.put("ignore", false);

                                            DocumentReference sendNotification = notificationFirestore.sendCustomNotification(pending_id);
                                            sendNotification
                                                    .set(notificationData)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.i(TAG, "onSuccess: invite sent to " + pending_id);
                                                        }
                                                    });
                                        }
                                    });
                        }
                    });
        }

        Toast.makeText(context, "Invite(s) Sent!", Toast.LENGTH_LONG).show();
    }

    public List<Task<DocumentSnapshot>> getSpaces(List<String> userSpaceIds) {
        List<Task<DocumentSnapshot>> taskAll = new ArrayList<>();
        for (String id : userSpaceIds) {
            Log.i(TAG, "getSpaces: id = " + id);
            taskAll.add(spaceData.document(id).get());
        }

        return taskAll;
    }

    public Task<DocumentSnapshot> getSpace(String spaceId){
        return spaceData.document(spaceId).get();
    }

    public String getCurrentSpaceId() {
        return currentSpaceId;
    }

    public void setCurrentSpaceId(String currentSpaceId) {
        this.currentSpaceId = currentSpaceId;
    }

    public String getCurrentSpaceName() {
        return currentSpaceName;
    }

    public void setCurrentSpaceName(String currentSpaceName) {
        this.currentSpaceName = currentSpaceName;
    }

    public CollectionReference getSpaceCollectionRef(String spaceId, String collectionRef){
        return spaceData.document(spaceId).collection(collectionRef);
    }

}

