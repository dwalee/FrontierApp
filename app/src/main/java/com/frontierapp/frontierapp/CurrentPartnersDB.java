/**
 * <h1>Current Partner Database</h1>
 * The CurrentPartnerDB class creates tables and manipulates the data for
 * current partners, favorites, and followers
 *
 * @author Yoshua Isreal
 * @version 1.0
 * @since 2018-07-01
 */

package com.frontierapp.frontierapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CurrentPartnersDB extends UserDB{
    private static final String TAG = "CurrentPartnersDB";
    Users users;
    Profiles profiles;
    List<String> favIdList = new ArrayList<>();

    public CurrentPartnersDB(Context context, User user, Profile profile) {
        super(context, user, profile);
    }

    public CurrentPartnersDB(Context context, User user) {
        super(context, user);
    }

    public CurrentPartnersDB(Context context, Profile profile) {
        super(context, profile);
    }

    public CurrentPartnersDB(Context context) {
        super(context);
    }

    /**
     * This method method deletes all records from the current_partners table
     * @return Boolean This returns a value of true if the records are deleted successfully
     * but if any exceptions occur the value will be false
     */
    public Boolean deleteCurrentPartnerTableDataFromSQLite(){
        Log.d(TAG, "deleteCurrentPartnerTableDataFromSQLite() called");
        try{
            /**
             * Open User_Data database
             */
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS current_partners ";
            String userProfileDataFormat = "(partner_id VARCHAR, " +
                    "first_name VARCHAR, last_name VARCHAR,email VARCHAR, about_me VARCHAR, " +
                    "city VARCHAR, state VARCHAR(2), goal VARCHAR,profile_url VARCHAR, " +
                    "profile_background_url VARCHAR, title VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);

            //Delete all records in the table
            userDatabase.execSQL("DELETE FROM current_partners");
            userDatabase.close();
        }catch(Exception e){
            Log.w(TAG, "deleteCurrentPartnerTableDataFromSQLite: ", e);
            Log.d(TAG, "deleteCurrentPartnerTableDataFromSQLite() returned: " + false);
            return false;
        }

        Log.d(TAG, "deleteCurrentPartnerTableDataFromSQLite() returned: " + true);
        return true;
    }

    /**
     * This method method deletes all records from the favorite table
     * @return Boolean This returns a value of true if completed successfully
     * but if any exceptions occur the value will be false
     */
    public Boolean deleteFavoriteTableDataFromSQLite(){
        Log.d(TAG, "deleteFavoriteTableDataFromSQLite() called");
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS favorite";
            String userProfileDataFormat = "(favorite_id VARCHAR, " +
                    "first_name VARCHAR, last_name VARCHAR,email VARCHAR, about_me VARCHAR, " +
                    "city VARCHAR, state VARCHAR(2), goal VARCHAR,profile_url VARCHAR, " +
                    "profile_background_url VARCHAR, title VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);
            //Delete all records in the table
            userDatabase.execSQL("DELETE FROM favorite");
            userDatabase.close();
        }catch(Exception e){
            Log.w(TAG, "deleteFavoriteTableDataFromSQLite: ",e);
            Log.d(TAG, "deleteFavoriteTableDataFromSQLite() returned: " + false);
            return false;
        }

        Log.d(TAG, "deleteFavoriteTableDataFromSQLite() returned: " + true);
        return true;
    }

    /**
     * This method method deletes all records from the follower table
     * @return Boolean This returns a value of true if completed successfully
     * but if any exceptions occur the value will be false
     */
    public Boolean deleteFollowerTableDataFromSQLite(){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS follower ";
            String userProfileDataFormat = "(follower_id VARCHAR, " +
                    "first_name VARCHAR, last_name VARCHAR,email VARCHAR, about_me VARCHAR, " +
                    "city VARCHAR, state VARCHAR(2), goal VARCHAR,profile_url VARCHAR, " +
                    "profile_background_url VARCHAR, title VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);
            userDatabase.execSQL("DELETE FROM follower");
            userDatabase.close();
        }catch(Exception e){
            Log.w(TAG, "deleteFollowerTableDataFromSQLite: ", e);
            return false;
        }

        Log.d(TAG, "deleteFollowerTableDataFromSQLite() called");
        return true;
    }

    /**
     * This method method deletes all records from the current_partners_ids table
     * @return Boolean This returns a value of true if completed successfully
     * but if any exceptions occur the value will be false
     */
    public Boolean deleteCurrentPartnerIDTableDataFromSQLite(){
        Log.d(TAG, "deleteCurrentPartnerIDTableDataFromSQLite() called");
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS current_partners ";
            String userProfileDataFormat = "(partner_id VARCHAR, " +
                    "first_name VARCHAR, last_name VARCHAR,email VARCHAR, about_me VARCHAR, " +
                    "city VARCHAR, state VARCHAR(2), goal VARCHAR,profile_url VARCHAR, " +
                    "profile_background_url VARCHAR, title VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);
            userDatabase.execSQL("DELETE FROM current_partners");
            userDatabase.close();
        }catch(Exception e){
            Log.w(TAG, "deleteCurrentPartnerIDTableDataFromSQLite: ", e);
            Log.d(TAG, "deleteCurrentPartnerIDTableDataFromSQLite() returned: " + false);
            return false;
        }

        Log.d(TAG, "deleteCurrentPartnerIDTableDataFromSQLite() returned: " + true);
        return true;
    }

    /**
     * This method method deletes all records from the favorite_ids table
     * @return Boolean This returns a value of true if completed successfully
     * but if any exceptions occur the value will be false
     */
    public Boolean deleteFavoriteIDTableDataFromSQLite(){
        Log.d(TAG, "deleteFavoriteIDTableDataFromSQLite() called");
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS favorite_ids";
            String userProfileDataFormat = "(favorite_id VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);
            userDatabase.execSQL("DELETE FROM favorite_ids");
            userDatabase.close();
        }catch(Exception e){
            Log.w(TAG, "deleteFavoriteIDTableDataFromSQLite: ", e);
            Log.d(TAG, "deleteFavoriteIDTableDataFromSQLite() returned: " + false);
            return false;
        }

        Log.d(TAG, "deleteFavoriteIDTableDataFromSQLite() returned: " + true);
        return true;
    }

    /**
     * This method method deletes all records from the follower_ids table
     * @return Boolean This returns a value of true if completed successfully
     * but if any exceptions occur the value will be false
     */
    public Boolean deleteFollowerIDTableDataFromSQLite(){
        Log.d(TAG, "deleteFollowerIDTableDataFromSQLite() called");
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS follower ";
            String userProfileDataFormat = "(follower_id VARCHAR, " +
                    "first_name VARCHAR, last_name VARCHAR,email VARCHAR, about_me VARCHAR, " +
                    "city VARCHAR, state VARCHAR(2), goal VARCHAR,profile_url VARCHAR, " +
                    "profile_background_url VARCHAR, title VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);
            userDatabase.execSQL("DELETE FROM follower");
            userDatabase.close();
        }catch(Exception e){
            Log.w(TAG, "deleteFollowerIDTableDataFromSQLite: ", e);
            Log.d(TAG, "deleteFollowerIDTableDataFromSQLite() returned: " + false);
            return false;
        }

        Log.d(TAG, "deleteFollowerIDTableDataFromSQLite() returned: " + true);
        return true;
    }

    /**
     * This method creates the current_partners table if it doesn't exist and
     * adds a list of the user's current partners to the current_partners table
     * @param users This parameter takes all the user data and adds it
     * @param profiles This parameter takes all the profile data and adds it along
     *                 the same record of the user data.
     * @param limit This parameter is used to limit the amount of current partners that can
     *              be added
     */
    public Boolean addCurrentPartnersToSQLite(Users users, Profiles profiles, int limit){
        Log.d(TAG, "addCurrentPartnersToSQLite() called with: users = [" + users + "], profiles = [" + profiles + "], limit = [" + limit + "]");
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS current_partners ";
            String userProfileDataFormat = "(partner_id VARCHAR, first_name VARCHAR, last_name VARCHAR," +
                    "email VARCHAR, about_me VARCHAR, city VARCHAR, state VARCHAR(2), goal VARCHAR," +
                    "profile_url VARCHAR, profile_background_url VARCHAR, title VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);
            userDatabase.execSQL("DELETE FROM current_partners");

            for(int i=0;i<users.size();i++) {
                User user = users.get(i);
                Profile profile = profiles.get(i);

                ContentValues insertValues = new ContentValues();
                insertValues.put("partner_id", user.getUid());
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

                userDatabase.insert("current_partners", null, insertValues);
                Log.i(TAG, "addCurrentPartnersToSQLite: insert " + insertValues);
            }

            Cursor c = userDatabase.rawQuery("SELECT * FROM current_partners", null);

            c.moveToFirst();
            /*while(c != null){
                Log.i("FirstName: ", c.getString(2));
                Log.i("ProfileUrl: ", c.getString(9));
                c.moveToNext();
            }*/

            c.close();
            userDatabase.close();
        }
        catch(Exception e){
            Log.w(TAG, "addCurrentPartnersToSQLite: ", e);
            Log.d(TAG, "addCurrentPartnersToSQLite() returned: " + false);
            return false;
        }

        Log.d(TAG, "addCurrentPartnersToSQLite() returned: " + true);
        return true;
    }

    /*I stopped here*/

    /**
     * This method adds a record to the current_partners table with the user's current partner
     * @param user This parameter requires the data of the partner's user data
     * @param profile This parameter requires the data of the partner's user data
     */
    public void addCurrentPartnerToSQLite(User user, Profile profile){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS current_partners ";
            String userProfileDataFormat = "(partner_id VARCHAR, " +
                    "first_name VARCHAR, last_name VARCHAR,email VARCHAR, about_me VARCHAR, " +
                    "city VARCHAR, state VARCHAR(2), goal VARCHAR,profile_url VARCHAR, " +
                    "profile_background_url VARCHAR, title VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);

            ContentValues insertValues = new ContentValues();
            insertValues.put("partner_id", user.getUid());
            insertValues.put("first_name", user.getFirst_name());
            insertValues.put("last_name", user.getLast_name());
            insertValues.put("email", user.getEmail());
            Log.i("PartnerEmail", "addCurrentPartnerToSQLite: " + user.getEmail());
            insertValues.put("about_me", profile.getAboutMe());
            insertValues.put("city", profile.getCity());
            insertValues.put("state", profile.getState());
            insertValues.put("goal", profile.getGoal());
            insertValues.put("profile_url", profile.getProfileAvatarUrl());
            insertValues.put("profile_background_url", profile.getProfileBackgroundUrl());
            insertValues.put("title", profile.getUserTitle());

            userDatabase.insert("current_partners", null, insertValues);

            Cursor c = userDatabase.rawQuery("SELECT * FROM current_partners", null);

            int profileUrlIndex = c.getColumnIndex("profile_url");

            c.moveToFirst();
            /*while(c != null){
                Log.i("FirstName2: ", c.getString(1));
                Log.i("ProfileUrl2: ", c.getString(8));
                c.moveToNext();
            }*/

            c.close();
            userDatabase.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method creates the favorite table if it doesn't exist and adds
     * a record of the favorite user's data to the table
     * @param user This parameter requires the value of the favorite's user data
     * @param profile This parameter requires the value of the favorite's profile data
     */
    public void addFavoriteToSQLite(User user, Profile profile){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS favorite ";
            String userProfileDataFormat = "(favorite_id VARCHAR, " +
                    "first_name VARCHAR, last_name VARCHAR,email VARCHAR, about_me VARCHAR, " +
                    "city VARCHAR, state VARCHAR(2), goal VARCHAR,profile_url VARCHAR, " +
                    "profile_background_url VARCHAR, title VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);

            ContentValues insertValues = new ContentValues();
            insertValues.put("favorite_id", user.getUid());
            insertValues.put("first_name", user.getFirst_name());
            insertValues.put("last_name", user.getLast_name());
            insertValues.put("email", user.getEmail());
            Log.i("PartnerEmail", "addCurrentPartnerToSQLite: " + user.getEmail());
            insertValues.put("about_me", profile.getAboutMe());
            insertValues.put("city", profile.getCity());
            insertValues.put("state", profile.getState());
            insertValues.put("goal", profile.getGoal());
            insertValues.put("profile_url", profile.getProfileAvatarUrl());
            insertValues.put("profile_background_url", profile.getProfileBackgroundUrl());
            insertValues.put("title", profile.getUserTitle());

            userDatabase.insert("favorite", null, insertValues);

            Cursor c = userDatabase.rawQuery("SELECT * FROM favorite", null);

            int profileUrlIndex = c.getColumnIndex("profile_url");

            c.moveToFirst();
            /*while(c != null){
                Log.i("FirstName2: ", c.getString(1));
                Log.i("ProfileUrl2: ", c.getString(8));
                c.moveToNext();
            }*/

            c.close();
            userDatabase.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method creates the follower table if it doesn't exist and adds
     * a record of the favorite user's data to the table
     * @param user This parameter requires the value of the favorite's user data
     * @param profile This parameter requires the value of the favorite's profile data
     */
    public void addFollowerToSQLite(User user, Profile profile){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS follower ";
            String userProfileDataFormat = "(follower_id VARCHAR, " +
                    "first_name VARCHAR, last_name VARCHAR,email VARCHAR, about_me VARCHAR, " +
                    "city VARCHAR, state VARCHAR(2), goal VARCHAR,profile_url VARCHAR, " +
                    "profile_background_url VARCHAR, title VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);

            ContentValues insertValues = new ContentValues();
            insertValues.put("follower_id", user.getUid());
            insertValues.put("first_name", user.getFirst_name());
            insertValues.put("last_name", user.getLast_name());
            insertValues.put("email", user.getEmail());
            Log.i("PartnerEmail", "addCurrentPartnerToSQLite: " + user.getEmail());
            insertValues.put("about_me", profile.getAboutMe());
            insertValues.put("city", profile.getCity());
            insertValues.put("state", profile.getState());
            insertValues.put("goal", profile.getGoal());
            insertValues.put("profile_url", profile.getProfileAvatarUrl());
            insertValues.put("profile_background_url", profile.getProfileBackgroundUrl());
            insertValues.put("title", profile.getUserTitle());

            userDatabase.insert("follower", null, insertValues);

            Cursor c = userDatabase.rawQuery("SELECT * FROM follower", null);

            int profileUrlIndex = c.getColumnIndex("profile_url");

            c.moveToFirst();
            /*while(c != null){
                Log.i("FirstName2: ", c.getString(1));
                Log.i("ProfileUrl2: ", c.getString(8));
                c.moveToNext();
            }*/

            c.close();
            userDatabase.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method creates the current_partners_ids table if it doesn't exist and adds
     * a list of ids from current partners to the table
     * @param partnerIds This parameter requires a String list value of the current
     *                   partners
     */
    public void addCurrentPartnersIdToSQLite(List<String> partnerIds){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS current_partners_ids ";
            String userProfileDataFormat = "(partner_index int, partner_id VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);
            userDatabase.execSQL("DELETE FROM current_partners_ids");

            for(int i=0;i<partnerIds.size();i++) {
                String id = partnerIds.get(i);

                ContentValues insertValues = new ContentValues();
                insertValues.put("partner_index", i);
                Log.i("PartnerIDIndex", "addCurrentPartnersIdToSQLite: " + i);
                insertValues.put("partner_id", id);

                userDatabase.insert("current_partners_ids", null, insertValues);
            }

            Cursor c = userDatabase.rawQuery("SELECT * FROM current_partners_ids", null);

            c.moveToFirst();
            /*while(c != null){
                Log.i("PartnerID2/ ", c.getString(0));
                c.moveToNext();
            }*/

            c.close();
            userDatabase.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * This method creates the favorite_ids table if it doesn't exist and adds
     * a list of ids from user's favorites to the table
     * @param favoriteIds This parameter requires a String list value of the user's favorite ids
     */
    public void addFavoriteIdsToSQLite(List<String> favoriteIds){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS favorite_ids ";
            String userProfileDataFormat = "(favorite_id VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);
            userDatabase.execSQL("DELETE FROM favorite_ids");

            for(int i=0;i<favoriteIds.size();i++) {
                String id = favoriteIds.get(i);

                ContentValues insertValues = new ContentValues();
                Log.i("FavoriteIDIndex", "addFavoriteIdToSQLite: " + i);
                insertValues.put("favorite_id", id);

                userDatabase.insert("favorite_ids", null, insertValues);
            }

            Cursor c = userDatabase.rawQuery("SELECT * FROM favorite_ids", null);

            c.moveToFirst();
            /*while(c != null){
                Log.i("FavoriteId ", c.getString(0));
                c.moveToNext();
            }*/

            c.close();
            userDatabase.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * This method creates the favorite_ids table if it doesn't exist and adds
     * a new record of the favorite user's id to the table
     * @param favId This parameter requires a String value of the user's favorite id
     */
    public void addFavoriteIdToSQLite(String favId){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS favorite_ids ";
            String userProfileDataFormat = "(favorite_id VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);

            ContentValues insertValues = new ContentValues();
            insertValues.put("favorite_id", favId);
            userDatabase.insert("favorite_ids", null, insertValues);


            Cursor c = userDatabase.rawQuery("SELECT * FROM favorite_ids", null);

            c.moveToLast();
            /*while(c != null){
                Log.i("FavoriteId ", c.getString(0));
                c.moveToNext();
            }*/

            c.close();
            userDatabase.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * This method creates the follower_ids table if it doesn't exist and adds
     * a list of ids from user's follower to the table
     * @param followerIds This parameter requires a String list value of the user's follower ids
     */
    public void addFollowerIdsToSQLite(List<String> followerIds){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READWRITE
            );

            String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS follower_ids ";
            String userProfileDataFormat = "(follower_id VARCHAR)";

            String createQuery = createUserProfileTableSQL + userProfileDataFormat;

            userDatabase.execSQL(createQuery);
            userDatabase.execSQL("DELETE FROM follower_ids");

            for(int i=0;i<followerIds.size();i++) {
                String id = followerIds.get(i);

                ContentValues insertValues = new ContentValues();
                Log.i("FollowerIDIndex", "addFollowerIdToSQLite: " + i);
                insertValues.put("follower_id", id);

                userDatabase.insert("follower_ids", null, insertValues);
            }

            Cursor c = userDatabase.rawQuery("SELECT * FROM follower_ids", null);

            c.moveToFirst();
            /*while(c != null){
                Log.i("FavoriteId ", c.getString(0));
                c.moveToNext();
            }*/

            c.close();
            userDatabase.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * This method retrieves user data for the current partner specified in
     * the current_partners table
     * @param position This parameter requires an integer value to select
     *                 which user data to retreive from the table
     * @return User This returns user data if any is found
     * otherwise it will return null if there's an exception or there's no value
     */
    public User getUserDataFromSQLite(int position){

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

            String selectAll = "SELECT * FROM current_partners";

            database.rawQuery(selectAll, null);
            Cursor cursor = database.rawQuery(selectAll, null);
            int firstNameIndex = cursor.getColumnIndex("first_name");
            int lastNameIndex = cursor.getColumnIndex("last_name");
            int emailIndex = cursor.getColumnIndex("email");
            int idIndex = cursor.getColumnIndex("partner_id");

            cursor.moveToPosition(position);
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

    /**
     * This method retrieves profile data for the current partner specified in
     * the current_partners table
     * @param position This parameter requires an integer value to select
     *                 which profile data to retrieve from the table
     * @return Profile This returns profile data if any is found in the table
     * otherwise it will return null if there's an exception or there's no value
     */
    public Profile getCurrentPartnerProfileFromSQLLite(int position){
        String backgroundUrl = "";
        String profileUrl = "";
        String title = "n\\a";
        String about_me = "n\\a";
        String city = "";
        String state = "";
        String location = "n\\a";
        String goals = "n\\a";

        try{

            profile = new Profile();

            SQLiteDatabase database = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READONLY
            );

            String selectAll = "SELECT * FROM current_partners";

            database.rawQuery(selectAll, null);
            Cursor cursor = database.rawQuery(selectAll, null);
            int titleIndex = cursor.getColumnIndex("title");
            int aboutMeIndex = cursor.getColumnIndex("about_me");
            int cityIndex = cursor.getColumnIndex("city");
            int stateIndex = cursor.getColumnIndex("state");
            int goalIndex = cursor.getColumnIndex("goal");
            int avatarIndex = cursor.getColumnIndex("profile_url");
            int backgroundIndex = cursor.getColumnIndex("profile_background_url");

            cursor.moveToPosition(position);

            title = cursor.getString(titleIndex);
            Log.i("CurrentPartnerDB", "getCurrentPartnerProfileFromSQLLite: " + title);
            about_me = cursor.getString(aboutMeIndex);
            city = cursor.getString(cityIndex);
            state = cursor.getString(stateIndex);
            goals = cursor.getString(goalIndex);
            profileUrl = cursor.getString(avatarIndex);
            Log.i("CurrentPartnerDB", "getCurrentPartnerProfileFromSQLLite: Avatar " + profileUrl);
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

    /**
     * This method retrieves all user data for the current partners from the
     * current_partners table
     * @return User This returns Users object(a User List) data from all the records
     * in the table otherwise it will return null if there's no records or an exception occurs
     */
    public Users getCurrentPartnersDataFromSQLite(){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READONLY
            );

            userDatabase.rawQuery("SELECT * FROM current_partners", null);
            Cursor cursor = userDatabase.rawQuery("SELECT * FROM current_partners", null);
            int partnerIdIndex = cursor.getColumnIndex("partner_id");
            int firstNameIndex = cursor.getColumnIndex("first_name");
            int lastNameIndex = cursor.getColumnIndex("last_name");
            int emailIndex = cursor.getColumnIndex("email");
            Log.i("PartnerEmailIndex", "getCurrentPartnersDataFromSQLite: " + emailIndex);

            users = new Users();

            cursor.moveToFirst();
            if(cursor != null) {
                do {
                    User user = new User();
                    Log.i("PartnerID/ ", cursor.getString(1));
                    user.setUid(cursor.getString(partnerIdIndex));
                    user.setFirst_name(cursor.getString(firstNameIndex));
                    user.setLast_name(cursor.getString(lastNameIndex));
                    user.setEmail(cursor.getString(emailIndex));
                    user.setGender("");
                    user.setAvatar("");
                    user.setBirthdate(null);
                    user.setPassword("");

                    users.add(user);

                } while (cursor.moveToNext());
            }

            cursor.close();
            userDatabase.close();

            return users;
        }
        catch(Exception e){
            e.printStackTrace();
        }finally {
            return users;
        }
    }

    /**
     * This method retrieves all profile data for the current partners from the
     * current_partners table
     * @return Profiles This returns Profiles object(a Profile List) data from all the records
     * in the table otherwise it will return null if there isn't any records or an exception occurs
     */
    public Profiles getCurrentPartnersProfileFromSQLite(){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READONLY
            );

            Cursor cursor = userDatabase.rawQuery("SELECT * FROM current_partners", null);
            int profileUrlIndex = cursor.getColumnIndex("profile_url");

            profiles = new Profiles();

            cursor.moveToFirst();
            if(cursor != null){
                do {
                    Profile profile = new Profile();
                    profile.setProfileAvatarUrl(cursor.getString(profileUrlIndex));

                    profiles.add(profile);
                }while(cursor.moveToNext());
            }

            cursor.close();
            userDatabase.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }finally {
            return profiles;
        }
    }

    /**
     * This method retrieves all users data for the user's favorite from the
     * favorite table
     * @return Users This returns Users object(a User ArrayList object) data from all the records
     * in the table otherwise it will return null if there isn't any records or an exception occurs
     */
    public Users getFavoritesUserDataFromSQLite(){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READONLY
            );

            Cursor cursor = userDatabase.rawQuery("SELECT * FROM favorite", null);
            int favoriteIdIndex = cursor.getColumnIndex("favorite_id");
            int firstNameIndex = cursor.getColumnIndex("first_name");
            int lastNameIndex = cursor.getColumnIndex("last_name");
            int emailIndex = cursor.getColumnIndex("email");
            Log.i("FavoriteEmailIndex", "getFavoritesUserDataFromSQLite: " + emailIndex);

            users = new Users();

            cursor.moveToFirst();
            if(cursor != null){
                do {
                    User user = new User();
                    Log.i("FavoriteID/ ", cursor.getString(1));
                    user.setUid(cursor.getString(favoriteIdIndex));
                    user.setFirst_name(cursor.getString(firstNameIndex));
                    user.setLast_name(cursor.getString(lastNameIndex));
                    user.setEmail(cursor.getString(emailIndex));
                    user.setGender("");
                    user.setAvatar("");
                    user.setBirthdate(null);
                    user.setPassword("");

                    users.add(user);
                }while(cursor.moveToNext());
            }

            cursor.close();
            userDatabase.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }finally {
            Log.i("TestBefore", "getCurrentPartnersDataFromSQLite: " + users.get(0).getFirst_name());
            return users;
        }

    }

    /**
     * This method retrieves all profile data for the user's favorite from the
     * favorite table
     * @return Profiles This returns Profiles object(a Profile ArrayList object) data from all the records
     * in the table otherwise it will return null if there isn't any records or an exception occurs
     */
    public Profiles getFavoritesProfileFromSQLite(){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READONLY
            );

            Cursor cursor = userDatabase.rawQuery("SELECT * FROM follower", null);
            int profileUrlIndex = cursor.getColumnIndex("profile_url");

            profiles = new Profiles();

            cursor.moveToFirst();
            if(cursor != null){
                do {
                    Profile profile = new Profile();
                    Log.i("FollowerID/ ", cursor.getString(1));
                    profile.setProfileAvatarUrl(cursor.getString(profileUrlIndex));

                    profiles.add(profile);


                }while(cursor.moveToNext());
            }

            cursor.close();
            userDatabase.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }finally {
            return profiles;
        }
    }

    /**
     * This method retrieves all user data for the user's follower from the
     * follower table
     * @return Users This returns Users object(a User ArrayList object) data from all the records
     * in the table otherwise it will return null if there isn't any records or an exception occurs
     */
    public Users getFollowersUserDataFromSQLite(){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READONLY
            );

            Cursor cursor = userDatabase.rawQuery("SELECT * FROM follower", null);
            int partnerIdIndex = cursor.getColumnIndex("follower_id");
            int firstNameIndex = cursor.getColumnIndex("first_name");
            int lastNameIndex = cursor.getColumnIndex("last_name");
            int emailIndex = cursor.getColumnIndex("email");
            Log.i("FollowerEmailIndex", "getFollowerUserDataFromSQLite: " + emailIndex);

            users = new Users();

            cursor.moveToFirst();
            if(cursor != null){
                do {
                    User user = new User();
                    Log.i("FollowerID/ ", cursor.getString(1));
                    user.setUid(cursor.getString(partnerIdIndex));
                    user.setFirst_name(cursor.getString(firstNameIndex));
                    user.setLast_name(cursor.getString(lastNameIndex));
                    user.setEmail(cursor.getString(emailIndex));
                    user.setGender("");
                    user.setAvatar("");
                    user.setBirthdate(null);
                    user.setPassword("");

                    users.add(user);

                }while(cursor.moveToNext());
            }

            cursor.close();
            userDatabase.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }finally {
            Log.i("TestBefore", "getCurrentPartnersDataFromSQLite: " + users.get(0).getFirst_name());
            return users;
        }
    }

    /**
     * This method retrieves all profile data for the user's follower from the
     * follower table
     * @return Profiles This returns Profiles object(a Profile ArrayList object) data from all the records
     * in the table otherwise it will return null if there isn't any records or an exception occurs
     */
    public Profiles getFollowersProfileFromSQLite(){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READONLY
            );

            Cursor cursor = userDatabase.rawQuery("SELECT * FROM follower", null);
            int profileUrlIndex = cursor.getColumnIndex("profile_url");

            profiles = new Profiles();

            cursor.moveToFirst();
            if(cursor != null){
                do {
                    Profile profile = new Profile();
                    Log.i("FollowerID/ ", cursor.getString(1));
                    profile.setProfileAvatarUrl(cursor.getString(profileUrlIndex));

                    profiles.add(profile);

                }while(cursor.moveToNext());
            }

            cursor.close();
            userDatabase.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }finally {
            return profiles;
        }
    }

    /**
     * This method retrieves all favorite ids for the user's favorite from the
     * favorite_ids table
     * @return List(String) This returns all the favorite ids in a String List from all the records
     * in the table otherwise it will return null if there isn't any records or an exception occurs
     */
    public List<String> getFavoritesIdFromSQLite(){
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READONLY
            );

            Cursor cursor = userDatabase.rawQuery("SELECT * FROM favorite_ids", null);
            int favoriteIdIndex = cursor.getColumnIndex("favorite_id");

            cursor.moveToFirst();
            favIdList.clear();
            if(cursor != null){
                do {
                    favIdList.add(cursor.getString(favoriteIdIndex));

                }while(cursor.moveToNext());
            }

            cursor.close();
            userDatabase.close();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }finally {
            return favIdList;
        }
    }

    /**
     * This method finds whether a favorite id is in the favorite_ids table
     * @param id This parameter requires a String value to search
     *           whether the id exist in the table
     * @return Boolean This returns true if the id is found and false if it is not
     */
    public Boolean findFavoriteById(String id){
        Log.d(TAG, "findFavoriteById() called with: id = [" + id + "]");
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READONLY
            );
            String arg = "'" + id + "'";
            String sql = "SELECT * FROM favorite_ids " +
                        "Where favorite_id=" + arg;

            Cursor cursor = userDatabase.rawQuery(sql, null);

            Boolean exists = cursor.moveToFirst();

            cursor.close();
            userDatabase.close();

            Log.d(TAG, "findFavoriteById() returned: " + exists);
            return exists;
        }
        catch(Exception e){
            Log.w(TAG, "findFavoriteById: ",e );
            Log.d(TAG, "findFavoriteById() returned: " + false);
            return false;
        }
    }

    /**
     * This method removes a record from the favorite_ids table
     * @param favId This parameter requires a String value to search
     *              and remove an id from the table
     * @return Boolean This returns true if the id is removed successfully
     * otherwise it returns false if the id isn't found or an exception occurs
     */
    public Boolean removeFavoriteIdFromSQLite(String favId){
        Log.d(TAG, "removeFavoriteIdFromSQLite() called with: favId = [" + favId + "]");
        try{
            SQLiteDatabase userDatabase = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("User_Data").toString(),
                    null, SQLiteDatabase.OPEN_READONLY
            );
            String arg = "'" + favId + "'";
            String sql = "DELETE FROM favorite_ids " +
                    "Where favorite_id=" + arg;

            userDatabase.rawQuery(sql, null);
            userDatabase.close();

            Log.d(TAG, "removeFavoriteIdFromSQLite() returned: " + true);
            return true;
        }
        catch(Exception e){
            Log.w(TAG, "removeFavoriteIdFromSQLite: ",e);
            Log.d(TAG, "removeFavoriteIdFromSQLite() returned: " + false);
            return false;
        }

    }

    /**
     * This method removes favorite user and profile data from the favorite table
     * @param favId This parameter requires a String value to search
     *              and remove the favorite user and profile data from favorite table
     * @return Boolean This returns true if the user is removed successfully
     * otherwise it returns false if the user isn't found or an exception occurs
     */
    public Boolean removeFavoriteFromSQLite(String favId){


        return true;
    }
}
