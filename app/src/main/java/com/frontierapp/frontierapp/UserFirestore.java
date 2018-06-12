package com.frontierapp.frontierapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;

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

    public void addUserToFireStore(){

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
