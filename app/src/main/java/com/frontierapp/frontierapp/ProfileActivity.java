package com.frontierapp.frontierapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.drive.DriveFile;

public class ProfileActivity extends AppCompatActivity {
    CollapsingToolbarLayout profileCollapsingToolbar;
    Toolbar profileToolbar;
    ImageView profileBackgroundImageView, profilePicCircleImageView;
    TextView userTitleTextView, userAboutMeTextView, locationTextView, goalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileBackgroundImageView = (ImageView) findViewById(R.id.profileBackgroundImageView);
        profilePicCircleImageView = (ImageView) findViewById(R.id.profilePicCircleImageView);
        userTitleTextView = (TextView) findViewById(R.id.titleTextView) ;
        userAboutMeTextView = (TextView) findViewById(R.id.aboutMeTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);
        goalTextView = (TextView) findViewById(R.id.goalsTextView);


        profileCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.profileCollapsingToolbar);

        profileToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.profileToolbar);
        loadProfileData();
        setSupportActionBar(profileToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);;

    }

    public void loadProfileData(){
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
            SQLiteDatabase database = openOrCreateDatabase("User_Data",
                    MODE_PRIVATE,
                    null);

            String selectAll = "SELECT * FROM user_profile";

            Cursor cursor = database.rawQuery(selectAll, null);
            int profileUrlIndex = cursor.getColumnIndex("profile_url");
            int profileBackgroundUrlIndex = cursor.getColumnIndex("profile_background_url");
            int titleIndex = cursor.getColumnIndex("title");
            int aboutMeIndex = cursor.getColumnIndex("about_me");
            int cityIndex = cursor.getColumnIndex("city");
            int stateIndex = cursor.getColumnIndex("state");
            int goalIndex = cursor.getColumnIndex("goal");
            int firstNameIndex = cursor.getColumnIndex("first_name");
            int lastNameIndex = cursor.getColumnIndex("last_name");

            cursor.moveToFirst();
            first_name = cursor.getString(firstNameIndex);
            last_name = cursor.getString(lastNameIndex);
            username = first_name + " " + last_name;
            profileUrl = cursor.getString(profileUrlIndex);
            backgroundUrl = cursor.getString(profileBackgroundUrlIndex);
            title = cursor.getString(titleIndex);
            about_me = cursor.getString(aboutMeIndex);
            city = cursor.getString(cityIndex);
            state = cursor.getString(stateIndex);
            goals = cursor.getString(goalIndex);
            location = city + ", " + state;

            database.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        profileCollapsingToolbar.setTitle(username);
        userTitleTextView.setText(title);
        userAboutMeTextView.setText(about_me);
        goalTextView.setText(goals);
        locationTextView.setText(location);

        //Load image into the backgound
        Glide.with(this)
                .load(backgroundUrl)
                .into(profileBackgroundImageView);

        //Input image into profile pic view
        Glide.with(this)
                .load(profileUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(profilePicCircleImageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //go back to the home activity

                Intent homeIntent = new Intent(this, Home.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
