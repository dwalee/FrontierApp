package com.frontierapp.frontierapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.Context.MODE_PRIVATE;

public class UserDB {
    User user;
    private Profile profile;
    private Context context;

    public static final int ADD = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;

    private static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private static CollectionReference userInfo = firebaseFirestore.collection("UserInformation");
    private static DocumentReference userData;

    public UserDB(Context context, User user, Profile profile) {
        this.user = user;
        this.profile = profile;
        this.context = context;
    }

    public UserDB(Context context, User user) {
        this.user = user;
        this.context = context;
    }

    public UserDB(Context context, Profile profile) {
        this.profile = profile;
        this.context = context;
    }

    public UserDB(Context context) {
        this.context = context;
    }

    public void addUserProfileToSQLite(){

    }

    //Update only the profile columns in user_profile table
    @NonNull
    public Boolean updateProfileToSQLite(Profile profile){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            ContentValues updateValues = new ContentValues();
            updateValues.put("about_me", profile.aboutMe);
            updateValues.put("city", profile.city);
            updateValues.put("state", profile.state);
            updateValues.put("goal", profile.goal);
            updateValues.put("title", profile.userTitle);

            userDatabase.update("user_profile",updateValues,null,null);

            Cursor c = userDatabase.rawQuery("SELECT * FROM user_profile", null);

            int profileUrlIndex = c.getColumnIndex("profile_url");

            int cityColumnIndex = c.getColumnIndex("city");
            int stateColumnIndex = c.getColumnIndex("state");

            c.moveToFirst();
            while(c != null){
                Log.i("FirstName: ", c.getString(1));
                Log.i("City: ", c.getString(cityColumnIndex));
                Log.i("State: ", c.getString(stateColumnIndex));
                c.moveToNext();
            }

            c.close();
            userDatabase.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    //Get the user data from sqlite and return the user object
    public User getUserDataFromSQLite(){

        return null;
    }

    //Get the profile data from sqlite and return the profile object
    @Nullable
    public Profile getProfileDataFromSQLite(){

        String backgroundUrl = "";
        String profileUrl = "";
        String title = "n\\a";
        String about_me = "n\\a";
        String city = "";
        String state = "";
        String location = "n\\a";
        String goals = "n\\a";
        String first_name = "";
        String last_name = "";
        String username = "";

        try{
            profile = new Profile();

            SQLiteDatabase database = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READONLY
            );

            String selectAll = "SELECT * FROM user_profile";

            database.rawQuery(selectAll, null);
            Cursor cursor = database.rawQuery(selectAll, null);
            int titleIndex = cursor.getColumnIndex("title");
            int aboutMeIndex = cursor.getColumnIndex("about_me");
            int cityIndex = cursor.getColumnIndex("city");
            int stateIndex = cursor.getColumnIndex("state");
            int goalIndex = cursor.getColumnIndex("goal");

            cursor.moveToFirst();
            title = cursor.getString(titleIndex);
            about_me = cursor.getString(aboutMeIndex);
            city = cursor.getString(cityIndex);
            state = cursor.getString(stateIndex);
            goals = cursor.getString(goalIndex);

            Log.i("City", "getProfileDataFromSQLite: " + city);
            profile.setUserTitle(title);
            profile.setAboutMe(about_me);
            profile.setCity(city);
            profile.setState(state);
            profile.setGoal(goals);

            cursor.close();
            database.close();

            return profile;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
