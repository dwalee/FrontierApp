package com.frontierapp.frontierapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class UserFirestore extends Firestore implements UserFirestoreConstants {
    private static final String TAG = "UserFirestore";

    protected Context context;
    protected UserFirestoreListener userFirestoreListener;

    protected CollectionReference userInformationDatabase = FirebaseFirestore.getInstance()
            .collection("UserInformation");
    protected CollectionReference userDatabase = userInformationDatabase
            .document("Users")
            .collection("User");
    protected DocumentReference userData = userDatabase
            .document(currentUserId);
    protected Map<String, Object> userInfo;

    public UserFirestore() {
    }

    public UserFirestore(Context context) {
        this.context = context;
    }

    public UserFirestore(Context context, UserFirestoreListener userFirestoreListener) {
        this.userFirestoreListener = userFirestoreListener;
    }

    public void add(String userId) {
        userDatabase
                .document(userId);
    }

    public void update(String key, String value) {
        userInfo = new HashMap<>();
        userInfo.put(key, value);

        userDatabase
                .document(currentUserId)
                .update(userInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Profile updated!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public CollectionReference getUserConnections(String userId) {
        CollectionReference userConnections = userDatabase
                .document(userId)
                .collection("Connections");

        return userConnections;
    }

    public DocumentReference getUserData(String userId) {
        DocumentReference userData = userDatabase
                .document(userId);

        return userData;
    }

    public void retrieve() {
        Log.i(TAG, "retrieve: id + " + currentUserId);
        userData
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "listen:error ", e);
                            return;
                        }
                        Profile profile = new Profile();

                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            profile.setTitle(documentSnapshot.getString("Profile.title"));
                            profile.setGoal(documentSnapshot.getString("Profile.goal"));
                            profile.setAbout_me(documentSnapshot.getString("Profile.about_me"));
                            profile.setCity(documentSnapshot.getString("Profile.city"));
                            profile.setState(documentSnapshot.getString("Profile.state"));
                            profile.setProfile_avatar(documentSnapshot.getString("Profile.profile_avatar"));
                            profile.setProfile_background_image_url(documentSnapshot.getString("Profile.profile_background_image_url"));
                            profile.setFirst_name(documentSnapshot.getString("User.first_name"));
                            profile.setLast_name(documentSnapshot.getString("User.last_name"));

                            Log.i(TAG, "Background Image: " + profile.getProfile_background_image_url());
                            userFirestoreListener.getProfile(profile);
                        } else {
                            Log.d(TAG, "Profile Data: null");
                        }
                    }
                });
    }

    public interface UserFirestoreListener {
        void getProfile(Profile profile);
    }
}
