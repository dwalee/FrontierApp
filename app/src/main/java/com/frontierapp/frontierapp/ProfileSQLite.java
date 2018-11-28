package com.frontierapp.frontierapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class ProfileSQLite implements ProfileDAO {
    private static final String TAG = "ProfileSQLite";
    Context context;
    String whereId = "WHERE user_id='?'";

    public ProfileSQLite(Context context) {
        this.context = context;
    }

    @Override
    public Profile getProfile() {
        Log.d(TAG, "getProfile() called");
        String user_id = "";
        String first_name = "";
        String last_name = "";
        String email = "";
        String backgroundUrl = "";
        String profileUrl = "";
        String title = "";
        String about_me = "";
        String city = "";
        String state = "";
        String location = "";
        String goals = "";

        try{
            Profile profile = new Profile();

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
            int titleIndex = cursor.getColumnIndex("title");
            int aboutMeIndex = cursor.getColumnIndex("about_me");
            int cityIndex = cursor.getColumnIndex("city");
            int stateIndex = cursor.getColumnIndex("state");
            int goalIndex = cursor.getColumnIndex("goal");
            int avatarIndex = cursor.getColumnIndex("profile_url");
            int backgroundIndex = cursor.getColumnIndex("profile_background_url");

            cursor.moveToFirst();
            user_id = cursor.getString(idIndex);
            first_name = cursor.getString(firstNameIndex);
            last_name = cursor.getString(lastNameIndex);
            email = cursor.getString(emailIndex);
            title = cursor.getString(titleIndex);
            about_me = cursor.getString(aboutMeIndex);
            city = cursor.getString(cityIndex);
            state = cursor.getString(stateIndex);
            goals = cursor.getString(goalIndex);
            profileUrl = cursor.getString(avatarIndex);
            backgroundUrl = cursor.getString(backgroundIndex);

            //Log.i("City", "getProfileDataFromSQLite: " + city);
            profile.setUid(user_id);
            profile.setFirst_name(first_name);
            profile.setLast_name(last_name);
            profile.setEmail(email);
            profile.setTitle(title);
            profile.setAbout_me(about_me);
            profile.setCity(city);
            profile.setState(state);
            profile.setGoal(goals);
            profile.setProfile_avatar(profileUrl);
            profile.setProfile_background_image_url(backgroundUrl);

            cursor.close();
            database.close();

            Log.d(TAG, "getProfile() returned: " + profile);
            return profile;
        }catch(Exception e){
            Log.w(TAG, "getProfile: ", e);
            Log.d(TAG, "getProfile() returned: " + null);
            return null;
        }

    }

    @Override
    public boolean addProfile(Profile profile) {
        Log.d(TAG, "addProfile() called with: profile = [" + profile + "]");
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
            insertValues.put("user_id", profile.getUid());
            insertValues.put("first_name", profile.getFirst_name());
            insertValues.put("last_name", profile.getLast_name());
            insertValues.put("email", profile.getEmail());
            insertValues.put("about_me", profile.getAbout_me());
            insertValues.put("city", profile.getCity());
            insertValues.put("state", profile.getState());
            insertValues.put("goal", profile.getGoal());
            insertValues.put("profile_url", profile.getProfile_avatar());
            insertValues.put("profile_background_url", profile.getProfile_background_image_url());
            insertValues.put("title", profile.getTitle());

            Log.i(TAG, "addProfile: insertValues = " + insertValues);
            userDatabase.insert("user_profile", null, insertValues);

            userDatabase.close();
            Log.d(TAG, "addProfile() returned: " + true);
            return true;
        }
        catch(Exception e){
            Log.w(TAG, "addProfile: ", e);
            Log.d(TAG, "addProfile() returned: " + false);
            return false;
        }

    }

    @Override
    public boolean updateProfile(Profile profile) {
        Log.d(TAG, "updateProfile() called with: profile = [" + profile + "]");
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READONLY
            );

            ContentValues updateValues = new ContentValues();
            updateValues.put("user_id", profile.getUid());
            updateValues.put("first_name", profile.getFirst_name());
            updateValues.put("last_name", profile.getLast_name());
            updateValues.put("email", profile.getEmail());
            updateValues.put("about_me", profile.getAbout_me());
            updateValues.put("city", profile.getCity());
            updateValues.put("state", profile.getState());
            updateValues.put("goal", profile.getGoal());
            updateValues.put("profile_url", profile.getProfile_avatar());
            updateValues.put("profile_background_url", profile.getProfile_background_image_url());
            updateValues.put("title", profile.getTitle());

            String[] id = new String[1];
            id[0] = profile.getUid();

            Log.i(TAG, "updateProfile: updateValues = " + updateValues);
            userDatabase.update("user_profile", updateValues,whereId,id);

            userDatabase.close();
            Log.d(TAG, "updateProfile() returned: " + true);
            return true;
        }
        catch(Exception e){
            Log.w(TAG, "updateProfile: ", e);
            Log.d(TAG, "updateProfile() returned: " + false);
            return false;
        }
    }

    @Override
    public boolean deleteProfile(Profile profile) {
        Log.d(TAG, "deleteProfile() called with: profile = [" + profile + "]");
        return false;
    }
}
