package com.frontierapp.frontierapp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDB {
    User user;
    final Profile profile = new Profile();

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference userInfo = firebaseFirestore.collection("UserInformation");
    DocumentReference userData;

    public UserDB(User user, Profile profile) {
        this.user = user;
        //this.profile = profile;
    }

    public UserDB(User user) {
        this.user = user;
        //profile = null;
    }

    public UserDB(Profile profile) {
        //this.profile = profile;
        this.user = null;
    }

    public UserDB() {
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

    public void updateProfileToFireStore(){
        try{
            userData = userInfo.document("Users").collection("User").document(user.getUid());
            userData.update(
                    "Profile.about_me", profile.getAboutMe(),
                    "Profile.title", profile.getUserTitle(),
                    "Profile.goal", profile.getGoal(),
                    "Profile.city", profile.getCity(),
                    "Profile.state", profile.getState(),
                    "Profile.profile_avatar", profile.getProfileAvatarUrl(),
                    "Profile.profile_background_image_url",profile.getProfileBackgroundUrl()
            );
        }
        catch(Exception e){

        }
    }

    public void addUserProfileToSQLite(){

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

    public Profile getProfileDataFromFireStore(String userID){

        try{
            final Profile profile = new Profile();
            userData = userInfo.document("Users").collection("User").document(userID);
            userData.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if(documentSnapshot.exists()){
                            profile.setAboutMe(documentSnapshot.getString("Profile.about_me"));
                            Log.i("UserDB/", "onComplete: " + profile.getAboutMe());
                            profile.setUserTitle(documentSnapshot.getString("Profile.title"));
                            profile.setGoal(documentSnapshot.getString("Profile.goal"));
                            profile.setCity(documentSnapshot.getString("Profile.city"));
                            profile.setState(documentSnapshot.getString("Profile.state"));
                            profile.setProfileAvatarUrl(documentSnapshot.getString("Profile.profile_avatar"));
                            profile.setProfileBackgroundUrl(documentSnapshot.getString("Profile.profile_background_image_url"));
                        }

                    }
                }
            });

            return profile;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Profile getUserDataFromSQLite(){

        return null;
    }

    public Profile getProfileDataFromSQLite(){

        return null;
    }
}
