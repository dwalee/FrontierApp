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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentPartnersFirestore extends UserDB{
    private static final String TAG = "CurrentPartnersFS";
    Users users;
    Profiles profiles;
    CurrentPartnersDB currentPartnersDB;
    List<String> partnerIDList;
    List<String> favIDList;
    List<String> followerIDList;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public CurrentPartnersFirestore(Context context, User user, Profile profile) {
        super(context, user, profile);
    }

    public CurrentPartnersFirestore(Context context, User user) {
        super(context, user);
    }

    public CurrentPartnersFirestore(Context context, Profile profile) {
        super(context, profile);
    }

    public CurrentPartnersFirestore(Context context) {
        super(context);
    }

    public Boolean addFavoriteIdToFirestore(final String currentUserID, final List<String> favIdList,
                                            final String newFavId){
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserID);

        favIdList.add(newFavId);

        Map<String, List<String>> favList = new HashMap<>();
        favList.put("ListOfFavs", favIdList);
        userData
                .update("ListOfFavs", favIdList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("CurrentPartners", "onSuccess: " + favIdList);
                        addFollowerIdToFirestore(currentUserID, newFavId);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("CurrentPartners", "onFailure: " + favIdList);
            }
        });

        return true;
    }

    public Boolean addFavoriteIdToFirestore(final String currentUserID, final String newFavId){
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserID);

        final List<String> favIdList = new ArrayList<>();
        favIdList.add(newFavId);

        Map<String, List<String>> favList = new HashMap<>();
        favList.put("ListOfFavs", favIdList);
        userData
                .set(favList, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addFollowerIdToFirestore(currentUserID, newFavId);
                        currentPartnersDB = new CurrentPartnersDB(context);;
                        currentPartnersDB.addFavoriteIdToSQLite(newFavId);
                        Log.i("CurrentPartners", "onSuccess: " + favIdList);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("CurrentPartners", "onFailure: " + favIdList);
            }
        });

        return true;
    }

    public Boolean removeFavoriteIdFromFirestore(final String currentUserID,
                     final List<String> favIdList, final String removeFavId){
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserID);
        Log.i("BeforeRemove", "removeFavoriteIdFromFirestore: " + favIdList);
        favIdList.remove(removeFavId);
        Log.i("AfterRemove", "removeFavoriteIdFromFirestore: " + favIdList);

        Map<String, List<String>> favList = new HashMap<>();
        favList.put("ListOfFavs", favIdList);
        userData
                .update("ListOfFavs", favIdList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        removeFollowerIdFromFirestore(currentUserID, removeFavId);
                        currentPartnersDB = new CurrentPartnersDB(context);
                        currentPartnersDB.removeFavoriteIdFromSQLite(removeFavId);
                        Log.i("CurrentPartners", "onSuccess: Remove" + favIdList);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("CurrentPartners", "onFailure: Remove" + favIdList);
            }
        });


        return true;
    }

    public Boolean addFollowerIdToFirestore(final String currentUserID, final String currentFavID){
        Log.d(TAG, "addFollowerIdToFirestore() called with: currentUserID = " +
                "[" + currentUserID + "], currentFavID = [" + currentFavID + "]");
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentFavID);

        userData
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d(TAG, "onSuccess() called with: documentSnapshot = [" + documentSnapshot + "]");
                        followerIDList = (List<String>) documentSnapshot.get("ListOfFollowers");
                        if(followerIDList == null){
                            followerIDList = new ArrayList<>();
                            followerIDList.add(currentUserID);
                            Map<String, List<String>> followerList = new HashMap<>();
                            followerList.put("ListOfFollowers", followerIDList);

                            userData
                                    .set(followerList, SetOptions.merge())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess() called with: aVoid = [" + aVoid + "]");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "onFailure: ", e);
                                }
                            });
                        }else{
                            followerIDList.add(currentUserID);

                            userData
                                    .update("ListOfFollowers", followerIDList)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.i(TAG, "onSuccess: update successful");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e(TAG, "onFailure: ", e);
                                }
                            });
                        }
                    }
                });

        Log.d(TAG, "addFollowerIdToFirestore() returned: " + true);
        return true;

    }

    public Boolean removeFollowerIdFromFirestore(final String currentUserID, final String currentFavID){
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentFavID);

        userData
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Log.i("removeFollowerIdToFS", "onComplete: " +
                                documentSnapshot.get("ListOfFollowers"));
                        followerIDList = (List<String>) documentSnapshot.get("ListOfFollowers");
                        followerIDList.remove(currentUserID);
                        Log.i("removeFollowerIdToFS", "onEvent: inside event" + followerIDList);

                        userData
                                .update("ListOfFollowers", followerIDList)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i("removeFollowerIdToFS", "onSuccess: " + currentFavID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("removeFollowerIdToFS", "onFailure: " + currentFavID);
                            }
                        });
                    }
                });

        Log.i("removeFollowerIdToFS", "onEvent: outside of event" + followerIDList);
        return true;
    }

    public Boolean getPartnersIDsFromFireStore(final String currentUserId){
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserId);

        userData.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(e != null){
                    Log.i("getPartnersIDsFromFS", "onEvent: " + e);
                }

                if(documentSnapshot != null && documentSnapshot.exists()){
                    Log.i("getPartnersIDsFromFS", "onEvent: " + documentSnapshot.get("ListOfPartners"));

                    Log.i("getPartnersIDsFromFS", "onComplete: " + documentSnapshot.getString("User.first_name"));

                    try{
                        partnerIDList = (List<String>) documentSnapshot.get("ListOfPartners");

                        if(partnerIDList == null){
                            currentPartnersDB = new CurrentPartnersDB(context);
                            currentPartnersDB.deleteCurrentPartnerIDTableDataFromSQLite();
                        }else{
                            String[] partnerIdArray = new String[partnerIDList.size()];
                            getPartnersUserDataFromFirestore(partnerIDList.toArray(partnerIdArray));

                            currentPartnersDB = new CurrentPartnersDB(context);
                            currentPartnersDB.deleteCurrentPartnerTableDataFromSQLite();
                            currentPartnersDB.addCurrentPartnersIdToSQLite(partnerIDList);
                        }
                    }catch(ClassCastException c){
                        Log.i("getPartnersIDsFromFS", "onEvent: " + c.toString());
                    }catch(Exception error){

                    }

                }
            }
        });

        return true;
    }

    public Boolean getFavoritesIDsFromFireStore(final String currentUserId){
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserId);

        userData.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(e != null){
                    Log.i("getFavoritesIDsFromFS", "onEvent: " + e);
                }

                if(documentSnapshot != null && documentSnapshot.exists()){
                    Log.i("getFavoritesIDsFromFS", "onEvent: " + documentSnapshot.get("ListOfFavs"));

                    //Get favorited ids from firestore
                    //Any exception, catch and do nothing
                    //No data, don't add to sqlite
                    try{
                        favIDList = (List<String>) documentSnapshot.get("ListOfFavs");

                        if(favIDList == null){
                            currentPartnersDB = new CurrentPartnersDB(context);
                            currentPartnersDB.deleteFavoriteIDTableDataFromSQLite();
                            currentPartnersDB.deleteFavoriteTableDataFromSQLite();
                        }else{
                            String[] favIdArray = new String[favIDList.size()];
                            getFavoriteUserDataFromFirestore(favIDList.toArray(favIdArray));

                            currentPartnersDB = new CurrentPartnersDB(context);
                            currentPartnersDB.deleteFavoriteTableDataFromSQLite();
                            currentPartnersDB.addFavoriteIdsToSQLite(favIDList);
                        }
                    }catch(ClassCastException c){
                        Log.i("getFavoritesIDsFromFS", "onEvent: " + c.toString());
                    }
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

        userData.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                Log.d(TAG, "onEvent() called with: documentSnapshot = " +
                        "[" + documentSnapshot + "], e = [" + e + "]");
                if(e != null){
                    Log.i("getFollowersIDsFromFS", "onEvent: " + e);
                }

                if(documentSnapshot != null && documentSnapshot.exists()){
                    Log.i("getFollowersIDsFromFS", "onEvent: " +
                            documentSnapshot.get("ListOfFollowers"));

                    //Get follower IDs from Firestore
                    try{
                        followerIDList = (List<String>) documentSnapshot.get("ListOfFollowers");

                        if(followerIDList == null){

                        }else{
                            String[] followerIDArray = new String[followerIDList.size()];
                            getFollowerUserDataFromFirestore(followerIDList.toArray(followerIDArray));

                            currentPartnersDB = new CurrentPartnersDB(context);
                            currentPartnersDB.deleteFollowerTableDataFromSQLite();
                            currentPartnersDB.addFollowerIdsToSQLite(followerIDList);
                        }
                    }catch(ClassCastException c){
                        Log.w(TAG, "onEvent: ", c);
                    }catch(Exception error){
                        Log.w(TAG, "onEvent: ",error );
                    }

                }
            }
        });

        return true;
    }

    public Boolean getPartnersUserDataFromFirestore(final String[] partnerIDs){
        currentPartnersDB = new CurrentPartnersDB(context);
        currentPartnersDB.deleteCurrentPartnerTableDataFromSQLite();
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

    public Boolean getFavoriteUserDataFromFirestore(final String[] favoriteIDs){
        currentPartnersDB = new CurrentPartnersDB(context);
        currentPartnersDB.deleteFavoriteTableDataFromSQLite();
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

    public Boolean getFollowerUserDataFromFirestore(final String[] followerIDs){
        currentPartnersDB = new CurrentPartnersDB(context);
        currentPartnersDB.deleteFollowerTableDataFromSQLite();
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
