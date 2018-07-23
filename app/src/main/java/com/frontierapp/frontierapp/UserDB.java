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
    protected Profile profile;
    protected Context context;

    public static final int ADD = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;

    protected static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    protected static CollectionReference userInfo = firebaseFirestore.collection("UserInformation");
    protected static DocumentReference userData;

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

    public Boolean addUserProfileToSQLite(User user, Profile profile){
        try{
            SQLiteDatabase userDatabase = context.openOrCreateDatabase("User_Data", MODE_PRIVATE,
                    null);
                    /*SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );*/

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS user_profile ";
            String userProfileDataFormat = "(user_id VARCHAR, first_name VARCHAR, last_name VARCHAR," +
                    "email VARCHAR, about_me VARCHAR, city VARCHAR, state VARCHAR(2), goal VARCHAR," +
                    "profile_url VARCHAR, profile_background_url VARCHAR, title VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);
            userDatabase.execSQL("DELETE FROM user_profile");

            ContentValues insertValues = new ContentValues();
            insertValues.put("user_id", user.getUid());
            insertValues.put("first_name", user.getFirst_name());
            insertValues.put("last_name", user.getLast_name());
            insertValues.put("email", user.getEmail());
            insertValues.put("about_me", profile.getAboutMe());
            insertValues.put("city", profile.getCity());
            insertValues.put("state", profile.getState());
            insertValues.put("goal", profile.getGoal());
            insertValues.put("profile_url", profile.getProfileAvatarUrl());
            insertValues.put("profile_background_url", profile.getProfileBackgroundUrl());
            insertValues.put("title", profile.getUserTitle());

            userDatabase.insert("user_profile", null, insertValues);

            Cursor c = userDatabase.rawQuery("SELECT * FROM user_profile", null);

            int profileUrlIndex = c.getColumnIndex("profile_url");

            c.moveToFirst();
            while(c != null){
                Log.i("FirstName: ", c.getString(1));
                Log.i("ProfileUrl: ", c.getString(8));
                c.moveToNext();
            }

            c.close();
            userDatabase.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return true;
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

    //Update only the profile columns in user_profile table
    @NonNull
    public Boolean updateProfileToSQLite(){
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

        String user_id = "";
        String first_name = "";
        String last_name = "";
        String email = "";

        try{
            user = new User();

            SQLiteDatabase database = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READONLY
            );

            String selectAll = "SELECT * FROM user_profile";

            database.rawQuery(selectAll, null);
            Cursor cursor = database.rawQuery(selectAll, null);
            int firstNameIndex = cursor.getColumnIndex("first_name");
            int lastNameIndex = cursor.getColumnIndex("last_name");
            int emailIndex = cursor.getColumnIndex("email");
            int idIndex = cursor.getColumnIndex("user_id");

            cursor.moveToFirst();
            user_id = cursor.getString(idIndex);
            first_name = cursor.getString(firstNameIndex);
            last_name = cursor.getString(lastNameIndex);
            email = cursor.getString(emailIndex);

            //Log.i("City", "getProfileDataFromSQLite: " + city);
            user.setUid(user_id);
            user.setFirst_name(first_name);
            user.setLast_name(last_name);
            user.setEmail(email);

            cursor.close();
            database.close();

            return user;
        }catch(Exception e){
            e.printStackTrace();

        return null;
        }
    }

    //Get the profile data from sqlite and return the profile object
    @Nullable
    public Profile getProfileDataFromSQLite(){

        String backgroundUrl = "";
        String profileUrl = "";
        String title = "";
        String about_me = "";
        String city = "";
        String state = "";
        String location = "";
        String goals = "";
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
            int avatarIndex = cursor.getColumnIndex("profile_url");
            int backgroundIndex = cursor.getColumnIndex("profile_background_url");

            cursor.moveToFirst();
            title = cursor.getString(titleIndex);
            about_me = cursor.getString(aboutMeIndex);
            city = cursor.getString(cityIndex);
            state = cursor.getString(stateIndex);
            goals = cursor.getString(goalIndex);
            profileUrl = cursor.getString(avatarIndex);
            backgroundUrl = cursor.getString(backgroundIndex);

            //Log.i("Avatar_/", "getProfileDataFromSQLite: " + );
            profile.setUserTitle(title);
            profile.setAboutMe(about_me);
            profile.setCity(city);
            profile.setState(state);
            profile.setGoal(goals);
            profile.setProfileAvatarUrl(profileUrl);
            profile.setProfileBackgroundUrl(backgroundUrl);

            cursor.close();
            database.close();

            return profile;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
