package com.frontierapp.frontierapp;

import android.content.Intent;
import android.os.Handler;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CurrentPartnerProfileActivity extends AppCompatActivity {
    FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
    CollapsingToolbarLayout partnerProfileCollapsingToolbar;
    Toolbar partnerProfileToolbar;
    ImageView partnerProfileBackgroundImageView, partnerProfilePicCircleImageView;
    TextView partnerTitleTextView, partnerAboutMeTextView, partnerLocationTextView, partnerGoalTextView;
    CurrentPartnersFirestore currentPartnerFirestore;
    CurrentPartnersDB currentPartnerDB;
    String currentPartnerId;
    Profile profile;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_partner_profile);

        partnerProfileBackgroundImageView = (ImageView) findViewById(R.id.partnerProfileBackgroundImageView);
        partnerProfilePicCircleImageView = (ImageView) findViewById(R.id.partnerProfilePicCircleImageView);
        partnerTitleTextView = (TextView) findViewById(R.id.partnerTitleTextView);
        partnerAboutMeTextView = (TextView) findViewById(R.id.partnerAboutMeTextView);
        partnerLocationTextView = (TextView) findViewById(R.id.partnerLocationTextView);
        partnerGoalTextView = (TextView) findViewById(R.id.partnerGoalsTextView);

        partnerProfileCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.partnerProfileCollapsingToolbar);

        partnerProfileToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.partnerProfileToolbar);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadProfileDataFromSQLite();
            }
        }, 300);

        setSupportActionBar(partnerProfileToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void loadProfileDataFromSQLite(){
        currentPartnerDB = new CurrentPartnersDB(getApplicationContext());
        int position = getIntent().getIntExtra("CurrentPartnerIdIndex", -1);
        Log.i("CurrentPartnerProfile", "loadProfileDataFromSQLite: position " + position);
        profile = currentPartnerDB.getCurrentPartnerProfileFromSQLLite(position);
        user = currentPartnerDB.getUserDataFromSQLite(position);

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

        partnerProfileCollapsingToolbar.setTitle(username);
        partnerTitleTextView.setText(title);
        partnerAboutMeTextView.setText(about_me);
        partnerGoalTextView.setText(goals);
        partnerLocationTextView.setText(location);

        //Load image into the backgound
        Glide.with(this)
                .load(backgroundUrl)
                .into(partnerProfileBackgroundImageView);

        //Input image into profile pic view
        Glide.with(this)
                .load(profileUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(partnerProfilePicCircleImageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.partner_menu, menu);
        menu.findItem(R.id.addMenu).setIcon(R.drawable.account_check_green);
        currentPartnerDB = new CurrentPartnersDB(getApplicationContext());
        int position = getIntent().getIntExtra("CurrentPartnerIdIndex", -1);
        user = currentPartnerDB.getUserDataFromSQLite(position);

        currentPartnerFirestore = new CurrentPartnersFirestore(this);
        currentPartnerId = user.getUid();
        Log.i("CurrentPartnerProfile", "loadProfileDataFromSQLite: ID " + currentPartnerId);
        isFav = currentPartnerDB.findFavoriteById(user.getUid());

        if(isFav){
            menu.findItem(R.id.favoriteMenu).setIcon(R.drawable.star_yellow);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final String id = firebaseuser.getUid();
        switch (item.getItemId()) {
            case android.R.id.home:
                //go back to the Current Partners Activity
                Intent currentPartnersActivityIntent = new Intent(this,
                        CurrentPartnersActivity.class);
                //startActivity(currentPartnersActivityIntent);
                finish();
                break;
            case R.id.addMenu:
                if (added == 2) {
                    item.setIcon(R.drawable.account_plus_white);
                    added = 0;
                } else if(added == 0){
                    item.setIcon(R.drawable.account_remove);
                    added = 1;
                }else{
                    item.setIcon(R.drawable.account_plus_white);
                    added = 0;
                }
                break;
            case R.id.favoriteMenu:
                if (isFav) {

                    currentFavIdsList = currentPartnerDB.getFavoritesIdFromSQLite();
                    isFav = !(currentPartnerFirestore.removeFavoriteIdFromFirestore(id,
                            currentFavIdsList, currentPartnerId));
                    if(!isFav)
                        item.setIcon(R.drawable.star);

                } else if(!isFav){

                    currentFavIdsList = currentPartnerDB.getFavoritesIdFromSQLite();
                    if(currentFavIdsList == null){
                        isFav = currentPartnerFirestore.addFavoriteIdToFirestore(id, currentPartnerId);
                        if(isFav)
                            item.setIcon(R.drawable.star_yellow);
                    }else{
                        isFav = currentPartnerFirestore.addFavoriteIdToFirestore(id,
                            currentFavIdsList, currentPartnerId);
                        if(isFav)
                            item.setIcon(R.drawable.star_yellow);
                    }


                }

                break;
        }
            return super.onOptionsItemSelected(item);
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private List<String> currentFavIdsList;
    private Boolean isFav = false;
    private int added = -1;
    }

