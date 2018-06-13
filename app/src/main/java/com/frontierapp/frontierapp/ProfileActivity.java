package com.frontierapp.frontierapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    CollapsingToolbarLayout profileCollapsingToolbar;
    Toolbar profileToolbar;
    ImageView profileBackgroundImageView, profilePicCircleImageView;
    TextView userTitleTextView, userAboutMeTextView, locationTextView, goalTextView;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    UserFirestore userFirestore;
    UserDB userDB;
    Profile profile;
    User user;

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
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadProfileDataFromSQLite();
            }
        }, 300);

        setSupportActionBar(profileToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public void loadProfileDataFromSQLite(){
        userDB = new UserDB(getApplicationContext());
        profile = userDB.getProfileDataFromSQLite();
        user = userDB.getUserDataFromSQLite();


        String backgroundUrl = profile.getProfileBackgroundUrl();
        String profileUrl = profile.getProfileAvatarUrl();
        String title = profile.getUserTitle();
        String about_me = profile.getAboutMe();
        String city = profile.getCity();
        String state = profile.getState();
        String location = city + ", " + state;
        String goals = profile.getGoal();
        String first_name = user.getFirst_name();
        String last_name = user.getLast_name();
        String username = first_name + " " + last_name;

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

    public void updateSQLiteFromFirestore(){
        String userId = currentFirebaseUser.getUid();
        userFirestore = new UserFirestore(getApplicationContext());
        //Log.i("ProfileEditActivity/", "loadEditTextFromFireStore: " + userId);
        userFirestore.getProfileDataFromFireStore(userId, UserDB.UPDATE);
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
            case R.id.editMenu:
                Intent profileEditIntent = new Intent(this, ProfileEditActivity.class);
                updateSQLiteFromFirestore();
                startActivity(profileEditIntent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
