package com.frontierapp.frontierapp;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity implements UserFirestore.UserFirestoreListener{
    CollapsingToolbarLayout profileCollapsingToolbar;
    Toolbar profileToolbar;
    ImageView profileBackgroundImageView, profilePicCircleImageView;
    TextView userTitleTextView, userAboutMeTextView, locationTextView, goalTextView;

    UserFirestore userFirestore = new UserFirestore(this, this);
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

        setSupportActionBar(profileToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        userFirestore.retrieve();
    }

    public void loadUserData(){
        String backgroundUrl = profile.getProfile_background_image_url();
        String profileUrl = profile.getProfile_avatar();
        String title = profile.getTitle();
        String about_me = profile.getAbout_me();
        String city = profile.getCity();
        String state = profile.getState();
        String location = city + ", " + state;
        String goals = profile.getGoal();
        String first_name = profile.getFirst_name();
        String last_name = profile.getLast_name();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return true;
    }

    public void goToMessages(View view){
        switch (view.getId()){
            case R.id.messagePic:
            case R.id.messageText:
                Intent messagesIntent = new Intent(ProfileActivity.this, ChatsActivity.class);
                startActivity(messagesIntent);
            }


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
                break;
            case R.id.editMenu:
                Intent profileEditIntent = new Intent(this, ProfileEditActivity.class);
                startActivity(profileEditIntent);
                break;
            case R.id.partnersMenu:
                Intent connectionsIntent = new Intent(this,
                        ConnectionsActivity.class);
                startActivity(connectionsIntent);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent signout = new Intent(this, MainActivity.class);
                startActivity(signout);
                finish();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void getProfile(Profile profile) {
        this.profile = profile;
        loadUserData();
    }
}
