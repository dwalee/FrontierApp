package com.frontierapp.frontierapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserFirestore {

    private User user;
    private Profile profile;
    private Context context;
    private UserDB userDB;

    public static final int ADD = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;

    private static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private static CollectionReference userInfo = firebaseFirestore.collection("UserInformation");
    private static DocumentReference userData;

    public UserFirestore() {
    }

    public UserFirestore(Context context, User user, Profile profile) {
        this.user = user;
        this.profile = profile;
        this.context = context;
    }

    public UserFirestore(Context context, User user) {
        this.user = user;
        this.context = context;
    }

    public UserFirestore(Context context, Profile profile) {
        this.profile = profile;
        this.context = context;
    }

    public UserFirestore(Context context) {
        this.context = context;
    }

    public void addUserToFireStore(final String userId){

    }

    public void addProfileToFireStore(){
        try{
            userData = userInfo.document("Users").collection("User").document(user.getUid());

        }
        catch(Exception e){

        }
    }

    public void updateProfileToFireStore(String userId, Profile profile){
        try{
            userData = userInfo.document("Users").collection("User").document(userId);
            userData.update(
                    "Profile.about_me", profile.getAboutMe(),
                    "Profile.title", profile.getUserTitle(),
                    "Profile.goal", profile.getGoal(),
                    "Profile.city", profile.getCity(),
                    "Profile.state", profile.getState()
            );
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public Boolean getUserProfileDataFromFirestore(final String userId){
        userData = userInfo.document("Users").collection("User").document(userId);
        userData.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        userDB = new UserDB(context);
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
                        user.setUid(userId);
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

                        userDB.addUserProfileToSQLite(user, profile);
                    }}
                });
        return true;
    }

    public User getUserDataFromFireStore(){

        try{
            userData = userInfo.document("Users").collection("User").document(user.getUid());
            userData.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                }
            });

            return user;
        }
        catch (Exception e){

        }

        return null;
    }

    //Get the profile object and values from the firestore for current user
    //Pass data to sqlite to add or update
    @Nullable
    public final Boolean getProfileDataFromFireStore(String userID, final int queryType){
        userDB = new UserDB(context);
        userData = userInfo.document("Users").collection("User").document(userID);
        userData.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Profile getProfile = new Profile();
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        getProfile.setAboutMe(documentSnapshot.getString("Profile.about_me"));
                        //Log.i("UserDB/", "onComplete: " + profile.getAboutMe());
                        getProfile.setUserTitle(documentSnapshot.getString("Profile.title"));
                        getProfile.setGoal(documentSnapshot.getString("Profile.goal"));
                        getProfile.setCity(documentSnapshot.getString("Profile.city"));
                        Log.i("City", "getProfileDataFromFireStore: " + getProfile.getCity());
                        getProfile.setState(documentSnapshot.getString("Profile.state"));
                        getProfile.setProfileAvatarUrl(documentSnapshot.getString("Profile.profile_avatar"));
                        getProfile.setProfileBackgroundUrl(documentSnapshot.getString("Profile.profile_background_image_url"));
                    }
                    switch(queryType){
                        case 1:
                            //Log.i("UserDB/", "Goal: " + getProfile.getGoal());
                            userDB.updateProfileToSQLite(getProfile);
                            break;
                    }

                }

            }

        });

        return true;
    }

}
