/**
 *
 */
package com.frontierapp.frontierapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentPartnersFirestore extends UserDB{
    private static final String TAG = "CurrentPartnersFS";
    private Users users;
    private CurrentPartnersDB currentPartnersDB;
    private List<String> partnerIDList;
    private List<String> followerIDList;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private Boolean collectedFavorite = false;
    private Boolean collectedPartner = false;
    private Boolean collectedFollower = false;

    private NotificationFirestore notificationFirestore = new NotificationFirestore();

    public CurrentPartnersFirestore(Context context) {
        super(context);
    }

    public Boolean requestNewPartnerToFirestore(final String currentUserID, final String newPartnerId,
                                                final PartnerStatus status){
        Log.d(TAG, "addNewPartnerToFirestore() called with: currentUserID = ["
                + currentUserID + "], newPartnerId = [" + newPartnerId + "]");
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserID);

        final Map<String, Object> partnered = new HashMap<>();
        partnered.put("partner", status.getStatus());

        final DocumentReference userConnections = userData.collection("Connections").document(newPartnerId);
        Log.i(TAG, "addNewPartnerToFirestore: userConnections = "
                + userConnections.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d(TAG, "onSuccess() called with: documentSnapshot = ["
                        + documentSnapshot.exists() + "]");
                if(documentSnapshot.exists()){
                    userConnections
                            .update(partnered)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    sendRequestToNewPartnerToFirestore(currentUserID, newPartnerId, status);
                                    Log.i(TAG, "onSuccess: true");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "onFailure: ", e);
                                }
                            });
                }else{
                    partnered.put("favorite", false);
                    partnered.put("follower", false);
                    partnered.put("timestamp", FieldValue.serverTimestamp());
                    userConnections
                            .set(partnered)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    sendRequestToNewPartnerToFirestore(currentUserID, newPartnerId, status);
                                    Log.i(TAG, "onSuccess: true");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "onFailure: ", e);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "onFailure: ", e);
            }
        }));

        return true;
    }

    public Boolean sendRequestToNewPartnerToFirestore(final String currentUserID, final String newPartnerId,
                                                      final PartnerStatus status){
        Log.d(TAG, "sendRequestToNewPartnerToFirestore() called with: currentUserID = [" + currentUserID + "], " +
                "newPartnerId = [" + newPartnerId + "], status = [" + status + "]");
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(newPartnerId);

        final Map<String, Object> partnered = new HashMap<>();
        if(status.equals(PartnerStatus.Pending_Sent)) {
            notificationFirestore.sendNotification(NotificationType.PARTNERSHIP_REQUEST, currentUserID, newPartnerId);
            partnered.put("partner", PartnerStatus.Pending_Response.getStatus());
        }
        else {
            if(status.equals(PartnerStatus.True))
                notificationFirestore.sendNotification(NotificationType.PARTNERSHIP_ACCEPTED, currentUserID, newPartnerId);
            partnered.put("partner", status.getStatus());
        }

        final DocumentReference userConnections = userData.collection("Connections").document(currentUserID);

        Log.i(TAG, "addNewPartnerToFirestore: userConnections = "
                + userConnections.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d(TAG, "onSuccess() called with: documentSnapshot = ["
                        + documentSnapshot.exists() + "]");
                if(documentSnapshot.exists()){
                    userConnections
                            .set(partnered, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.i(TAG, "onSuccess: true");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "onFailure: ", e);
                                }
                            });
                }else{
                    partnered.put("favorite", false);
                    partnered.put("follower", false);
                    partnered.put("timestamp", FieldValue.serverTimestamp());
                    userConnections
                            .set(partnered)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.i(TAG, "onSuccess: true");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "onFailure: ", e);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "onFailure: ", e);
            }
        }));

        return true;
    }

    public Boolean removePartnerFromFirestore(final String currentUserID, final String removePartnerID){


        return true;
    }

    public Boolean addFavoriteIdToFirestore(final String currentUserID, final String newFavId){
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserID);

        final Map<String, Object> favorited = new HashMap<>();
        favorited.put("favorite", true);

        final DocumentReference userConnections = userData.collection("Connections").document(newFavId);
        Log.i(TAG, "addFavoriteIdToFirestore: userConnections = "
                + userConnections.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d(TAG, "onSuccess() called with: documentSnapshot = ["
                                + documentSnapshot.exists() + "]");
                        if(documentSnapshot.exists()){
                            userConnections
                                    .set(favorited, SetOptions.merge())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            addFollowerIdToFirestore(currentUserID, newFavId);
                                            notificationFirestore.sendNotification(NotificationType.FOLLOW, currentUserID, newFavId);
                                            currentPartnersDB = new CurrentPartnersDB(context);
                                            currentPartnersDB.addFavoriteIdToSQLite(newFavId);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "onFailure: ", e);
                                        }
                                    });
                        }else{
                            favorited.put("partner", "False");
                            favorited.put("follower", false);
                            favorited.put("timestamp", FieldValue.serverTimestamp());
                            userConnections
                                    .set(favorited)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            addFollowerIdToFirestore(currentUserID, newFavId);
                                            notificationFirestore.sendNotification(NotificationType.FOLLOW, currentUserID, newFavId);
                                            currentPartnersDB = new CurrentPartnersDB(context);
                                            currentPartnersDB.addFavoriteIdToSQLite(newFavId);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "onFailure: ", e);
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "onFailure: ", e);
            }
        }));

        return true;
    }

    public Boolean removeFavoriteIdFromFirestore(final String currentUserID, final String removeFavId){
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserID);

        userData.collection("Connections").document(removeFavId)
                .update("favorite", false)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        removeFollowerIdFromFirestore(currentUserID, removeFavId);
                        currentPartnersDB = new CurrentPartnersDB(context);
                        currentPartnersDB.removeFavoriteIdFromSQLite(removeFavId);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "onFailure: ", e);
            }
        });

        return true;
    }

    public Boolean addFollowerIdToFirestore(final String currentUserID, final String currentFavID){
        Log.d(TAG, "addFollowerIdToFirestore() called with: currentUserID = " +
                "[" + currentUserID + "], currentFavID = [" + currentFavID + "]");
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentFavID);

        final Map<String, Object> followed = new HashMap<>();
        followed.put("follower", true);

        final DocumentReference userConnections = userData.collection("Connections").document(currentUserID);
        Log.i(TAG, "addFavoriteIdToFirestore: userConnections = "
                + userConnections.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d(TAG, "onSuccess() called with: documentSnapshot = ["
                        + documentSnapshot.exists() + "]");
                if(documentSnapshot.exists()){
                    userConnections
                            .set(followed, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.i(TAG, "onSuccess: true");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "onFailure: ", e);
                                }
                            });
                }else{
                    followed.put("partner", "False");
                    followed.put("favorite", false);
                    followed.put("timestamp", FieldValue.serverTimestamp());
                    userConnections
                            .set(followed)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.i(TAG, "onSuccess: true");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "onFailure: ", e);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "onFailure: ", e);
            }
        }));

        Log.d(TAG, "addFollowerIdToFirestore() returned: " + true);
        return true;

    }

    public Boolean removeFollowerIdFromFirestore(final String currentUserID, final String currentFavID){
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentFavID);

        userData.collection("Connections").document(currentUserID)
                .update("follower", false)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "onSuccess: true");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "onFailure: ", e);
            }
        });

        return true;
    }

    public Boolean getPartnersIDsFromFireStore(final String currentUserId){
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserId);

        CollectionReference connectionsData = userData.collection("Connections");

        connectionsData
                .whereEqualTo("partner", PartnerStatus.True.getStatus())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                        if(e != null){
                            Log.w(TAG, "onEvent: ", e);
                        }

                        partnerIDList = new ArrayList<>();
                        DocumentChange.Type updateType = null;

                        for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            Log.d(TAG, "onEvent: docId = " + doc.getDocument().getId());
                            updateType = doc.getType();
                            Log.d(TAG, "onEvent: docType = " + updateType.toString());
                            partnerIDList.add(doc.getDocument().getId());
                        }

                        String[] currentPartnerIdArray;
                        if(partnerIDList == null){
                            currentPartnersDB = new CurrentPartnersDB(context);
                            currentPartnersDB.deleteCurrentPartnerIDTableDataFromSQLite();
                            currentPartnersDB.deleteCurrentPartnerTableDataFromSQLite();
                        }else if(collectedPartner == true){
                            switch(updateType){
                                case ADDED:
                                    currentPartnerIdArray = new String[partnerIDList.size()];
                                    getPartnersUserDataFromFirestore(partnerIDList.toArray(currentPartnerIdArray), DBType.ADDED);

                                    currentPartnersDB = new CurrentPartnersDB(context);
                                    currentPartnersDB.addCurrentPartnersIdToSQLite(partnerIDList);
                                    break;
                                case REMOVED:
                                    currentPartnerIdArray = new String[partnerIDList.size()];
                                    getPartnersUserDataFromFirestore(partnerIDList.toArray(currentPartnerIdArray), DBType.REMOVED);
                                    break;
                                case MODIFIED:
                                    break;
                            }
                        }else{
                            currentPartnerIdArray = new String[partnerIDList.size()];
                            getPartnersUserDataFromFirestore(partnerIDList.toArray(currentPartnerIdArray), DBType.NORMAL);

                            currentPartnersDB = new CurrentPartnersDB(context);
                            currentPartnersDB.deleteCurrentPartnerTableDataFromSQLite();
                            currentPartnersDB.addCurrentPartnersIdToSQLite(followerIDList);
                            collectedPartner = true;
                        }
                    }
                });

        return true;
    }

    public Boolean getFavoritesIDsFromFireStore(final String currentUserId){
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserId);

        CollectionReference connectionsData = userData.collection("Connections");

        connectionsData
                .whereEqualTo("favorite", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                        if(e != null){
                            Log.w(TAG, "onEvent: ", e);
                            return;
                        }

                        followerIDList = new ArrayList<>();
                        DocumentChange.Type updateType = null;

                        for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            Log.d(TAG, "onEvent: docId = " + doc.getDocument().getId());
                            updateType = doc.getType();
                            Log.d(TAG, "onEvent: docType = " + updateType.toString());
                            followerIDList.add(doc.getDocument().getId());
                        }

                        String[] favIdArray;
                        if(followerIDList == null){
                            currentPartnersDB = new CurrentPartnersDB(context);
                            currentPartnersDB.deleteFavoriteIDTableDataFromSQLite();
                            currentPartnersDB.deleteFavoriteTableDataFromSQLite();
                        }else if(collectedFavorite == true){
                            switch(updateType){
                                case ADDED:
                                    favIdArray = new String[followerIDList.size()];
                                    getFavoriteUserDataFromFirestore(followerIDList.toArray(favIdArray), DBType.ADDED);

                                    currentPartnersDB = new CurrentPartnersDB(context);
                                    currentPartnersDB.addFavoriteIdsToSQLite(followerIDList);
                                    break;
                                case REMOVED:
                                    favIdArray = new String[followerIDList.size()];
                                    getFavoriteUserDataFromFirestore(followerIDList.toArray(favIdArray), DBType.REMOVED);

                                    currentPartnersDB = new CurrentPartnersDB(context);
                                    currentPartnersDB.removeFavoriteIdFromSQLite(followerIDList.get(0));
                                    break;
                                case MODIFIED:
                                    break;
                            }
                        }else{
                            favIdArray = new String[followerIDList.size()];
                            getFavoriteUserDataFromFirestore(followerIDList.toArray(favIdArray), DBType.NORMAL);

                            currentPartnersDB = new CurrentPartnersDB(context);
                            currentPartnersDB.deleteFavoriteTableDataFromSQLite();
                            currentPartnersDB.addFavoriteIdsToSQLite(followerIDList);
                            collectedFavorite = true;
                        }
                    }
                });

        return true;
    }

    public Boolean getFollowersIDsFromFireStore(final String currentUserId){
        Log.d(TAG, "getFollowersIDsFromFireStore() called with: currentUserId = " +
                "[" + currentUserId + "]");

        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserId);

        CollectionReference connectionsData = userData.collection("Connections");

        connectionsData
                .whereEqualTo("follower", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                        if(e != null){
                            Log.w(TAG, "onEvent: ", e);
                            return;
                        }

                        followerIDList = new ArrayList<>();
                        DocumentChange.Type updateType = null;

                        for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            Log.d(TAG, "onEvent: docId = " + doc.getDocument().getId());
                            updateType = doc.getType();
                            Log.d(TAG, "onEvent: docType = " + updateType.toString());
                            followerIDList.add(doc.getDocument().getId());
                        }

                        String[] followerIdArray;
                        if(followerIDList == null){
                            currentPartnersDB = new CurrentPartnersDB(context);
                            currentPartnersDB.deleteFollowerIDTableDataFromSQLite();
                            currentPartnersDB.deleteFollowerTableDataFromSQLite();
                        }else if(collectedFollower == true){
                            switch(updateType){
                                case ADDED:
                                    followerIdArray = new String[followerIDList.size()];
                                    getFollowerUserDataFromFirestore(followerIDList.toArray(followerIdArray), DBType.ADDED);

                                    currentPartnersDB = new CurrentPartnersDB(context);
                                    currentPartnersDB.addFollowerIdsToSQLite(followerIDList);
                                    break;
                                case REMOVED:
                                    followerIdArray = new String[followerIDList.size()];
                                    getFollowerUserDataFromFirestore(followerIDList.toArray(followerIdArray), DBType.REMOVED);
                                    break;
                                case MODIFIED:
                                    break;
                            }
                        }else{
                            followerIdArray = new String[followerIDList.size()];
                            getFollowerUserDataFromFirestore(followerIDList.toArray(followerIdArray), DBType.NORMAL);

                            currentPartnersDB = new CurrentPartnersDB(context);
                            currentPartnersDB.deleteFollowerTableDataFromSQLite();
                            currentPartnersDB.addFollowerIdsToSQLite(followerIDList);
                            collectedFollower = true;
                        }
                    }
                });

        return true;
    }

    public Boolean getPartnersUserDataFromFirestore(final String[] partnerIDs, DBType type){
        currentPartnersDB = new CurrentPartnersDB(context);

        switch(type){
            case NORMAL:
                currentPartnersDB.deleteCurrentPartnerTableDataFromSQLite();;
                break;
            case REMOVED:
                return true;
            case ADDED:
                break;
            case MODIFIED:
                break;
            default:

        }

        for(final String id : partnerIDs){
            userData = userInfo.document("Users").collection("User").document(id);
            userData.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            //Get data from the current user
                            user = new User();
                            profile = new Profile();
                            String first_name = "";
                            String last_name = "";
                            //String profileUrl = document.getString("userAvatarUrl");
                            String email = "";
                            String aboutMe = "";
                            String city = "";
                            String state = "";
                            String goal = "";
                            String profileUrl = "";
                            String profileBackgroundUrl = "";
                            String title = "";

                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                //Get data from the current user
                                first_name = document.getString("User.first_name");
                                last_name = document.getString("User.last_name");
                                Log.i("PartnerLastName", "onComplete: " + last_name);
                                //String profileUrl = document.getString("userAvatarUrl");
                                email = document.getString("User.email");
                                aboutMe = document.getString("Profile.about_me");
                                city = document.getString("Profile.city");
                                state = document.getString("Profile.state");
                                goal = document.getString("Profile.goal");
                                profileUrl = document.getString("Profile.profile_avatar");
                                profileBackgroundUrl = document.getString(
                                        "Profile.profile_background_image_url");
                                title = document.getString("Profile.title");


                                Log.i("City", "onComplete: " + city);
                                user.setUid(id);
                                user.setFirst_name(first_name);
                                user.setLast_name(last_name);
                                user.setEmail(email);

                                profile.setUserTitle(title);
                                profile.setAboutMe(aboutMe);
                                profile.setGoal(goal);
                                profile.setCity(city);
                                profile.setState(state);
                                profile.setProfileAvatarUrl(profileUrl);
                                profile.setProfileBackgroundUrl(profileBackgroundUrl);

                                currentPartnersDB.addCurrentPartnerToSQLite(user, profile);
                            }}
                    });
        }

        return true;
    }

    public Boolean getFavoriteUserDataFromFirestore(final String[] favoriteIDs, DBType type){
        Log.d(TAG, "getFavoriteUserDataFromFirestore() called with: favoriteIDs = " +
                "[" + favoriteIDs + "], type = [" + type + "]");
        currentPartnersDB = new CurrentPartnersDB(context);
        switch(type){
            case NORMAL:
                currentPartnersDB.deleteFavoriteTableDataFromSQLite();
                break;
            case REMOVED:
                return currentPartnersDB.removeFavoriteFromSQLite(favoriteIDs[0]);
            case ADDED:
                break;
            case MODIFIED:
                break;
            default:

        }

        for(final String id : favoriteIDs){
            userData = userInfo.document("Users").collection("User").document(id);
            userData.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            //Get data from the current user
                            user = new User();
                            profile = new Profile();
                            String first_name = "";
                            String last_name = "";
                            String email = "";
                            String aboutMe = "";
                            String city = "";
                            String state = "";
                            String goal = "";
                            String profileUrl = "";
                            String profileBackgroundUrl = "";
                            String title = "";

                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                //Get data from the current user
                                first_name = document.getString("User.first_name");
                                last_name = document.getString("User.last_name");
                                Log.i("FavoriteLastName", "onComplete: " + last_name);
                                //String profileUrl = document.getString("userAvatarUrl");
                                email = document.getString("User.email");
                                aboutMe = document.getString("Profile.about_me");
                                city = document.getString("Profile.city");
                                state = document.getString("Profile.state");
                                goal = document.getString("Profile.goal");
                                profileUrl = document.getString("Profile.profile_avatar");
                                profileBackgroundUrl = document.getString(
                                        "Profile.profile_background_image_url");
                                title = document.getString("Profile.title");


                                Log.i("City", "onComplete: " + city);
                                user.setUid(id);
                                user.setFirst_name(first_name);
                                user.setLast_name(last_name);
                                user.setEmail(email);

                                profile.setUserTitle(title);
                                profile.setAboutMe(aboutMe);
                                profile.setGoal(goal);
                                profile.setCity(city);
                                profile.setState(state);
                                profile.setProfileAvatarUrl(profileUrl);
                                profile.setProfileBackgroundUrl(profileBackgroundUrl);

                                currentPartnersDB.addFavoriteToSQLite(user, profile);
                            }}
                    });
        }

        return true;
    }

    public Boolean getFollowerUserDataFromFirestore(final String[] followerIDs, DBType type){
        Log.d(TAG, "getFollowerUserDataFromFirestore() called with: followerIDs = ["
                + followerIDs + "], type = [" + type + "]");
        currentPartnersDB = new CurrentPartnersDB(context);
        switch(type){
            case NORMAL:
                currentPartnersDB.deleteFollowerTableDataFromSQLite();
                break;
            case REMOVED:
                return currentPartnersDB.removeFollowerFromSQLite(followerIDs[0]);
            case ADDED:
                break;
            case MODIFIED:
                break;
            default:

        }

        for(final String id : followerIDs){
            userData = userInfo.document("Users").collection("User").document(id);
            userData.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            //Get data from the current user
                            user = new User();
                            profile = new Profile();
                            String first_name = "";
                            String last_name = "";
                            //String profileUrl = document.getString("userAvatarUrl");
                            String email = "";
                            String aboutMe = "";
                            String city = "";
                            String state = "";
                            String goal = "";
                            String profileUrl = "";
                            String profileBackgroundUrl = "";
                            String title = "";

                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                //Get data from the current user
                                first_name = document.getString("User.first_name");
                                last_name = document.getString("User.last_name");
                                Log.i("PartnerLastName", "onComplete: " + last_name);
                                //String profileUrl = document.getString("userAvatarUrl");
                                email = document.getString("User.email");
                                aboutMe = document.getString("Profile.about_me");
                                city = document.getString("Profile.city");
                                state = document.getString("Profile.state");
                                goal = document.getString("Profile.goal");
                                profileUrl = document.getString("Profile.profile_avatar");
                                profileBackgroundUrl = document.getString(
                                        "Profile.profile_background_image_url");
                                title = document.getString("Profile.title");


                                Log.i("City", "onComplete: " + city);
                                user.setUid(id);
                                user.setFirst_name(first_name);
                                user.setLast_name(last_name);
                                user.setEmail(email);

                                profile.setUserTitle(title);
                                profile.setAboutMe(aboutMe);
                                profile.setGoal(goal);
                                profile.setCity(city);
                                profile.setState(state);
                                profile.setProfileAvatarUrl(profileUrl);
                                profile.setProfileBackgroundUrl(profileBackgroundUrl);

                                currentPartnersDB.addFollowerToSQLite(user, profile);
                            }}
                    });
        }

        return true;
    }

}
