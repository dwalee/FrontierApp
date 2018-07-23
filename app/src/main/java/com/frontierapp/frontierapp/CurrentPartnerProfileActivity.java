package com.frontierapp.frontierapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CurrentPartnerProfileActivity extends AppCompatActivity {
    private static final String TAG = "CurrentPartnerProfileA";
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
    SwipeRefreshLayout currentPartnerProfileSwipeRefreshLayout;

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    protected DocumentReference userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_partner_profile);

        currentPartnerProfileSwipeRefreshLayout = (SwipeRefreshLayout)
                findViewById(R.id.currentPartnerProfileSwipeRefreshLayout);
        currentPartnerProfileSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshProfileDataFromSQLite();
            }
        });

        partnerProfileBackgroundImageView = (ImageView) findViewById(R.id.partnerProfileBackgroundImageView);
        partnerProfilePicCircleImageView = (ImageView) findViewById(R.id.partnerProfilePicCircleImageView);
        partnerTitleTextView = (TextView) findViewById(R.id.partnerTitleTextView);
        partnerAboutMeTextView = (TextView) findViewById(R.id.partnerAboutMeTextView);
        partnerLocationTextView = (TextView) findViewById(R.id.partnerLocationTextView);
        partnerGoalTextView = (TextView) findViewById(R.id.partnerGoalsTextView);

        partnerProfileCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.partnerProfileCollapsingToolbar);

        partnerProfileToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.partnerProfileToolbar);


        setSupportActionBar(partnerProfileToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void refreshProfileDataFromSQLite(){
        currentPartnerProfileSwipeRefreshLayout.setColorSchemeResources(R.color.colorLoadRefresh);
        loadProfileDataFromSQLite();
        currentPartnerProfileSwipeRefreshLayout.setRefreshing(false);
    }
    public void loadProfileDataFromSQLite() {
        currentPartnerDB = new CurrentPartnersDB(getApplicationContext());
        String partnerId = getIntent().getStringExtra("CurrentPartnerId");
        String className = getIntent().getStringExtra("ClassName");
        Log.i(TAG, "loadProfileDataFromSQLite: Calling Class = "
                + className);
        Log.i(TAG, "loadProfileDataFromSQLite: partnerId " + partnerId);

        if(className.equals("Partner")) {
            profile = currentPartnerDB.getCurrentPartnerProfileFromSQLLite(partnerId);
            user = currentPartnerDB.getUserDataFromSQLite(partnerId);
        }else if(className.equals("Favorite")){
            profile = currentPartnerDB.getFavoriteProfileFromSQLLite(partnerId);
            user = currentPartnerDB.getFavoriteUserDataFromSQLite(partnerId);
        }else if(className.equals("Follower")){
            profile = currentPartnerDB.getFollowerProfileFromSQLLite(partnerId);
            user = currentPartnerDB.getFollowerUserDataFromSQLite(partnerId);
        }

        Log.i(TAG, "loadProfileDataFromSQLite: profile = " + profile +
        " user = " + user);
        if (user != null) {
            Log.i(TAG, "loadProfileDataFromSQLite: if statement called");
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
        }else{
            Log.i(TAG, "loadProfileDataFromSQLite: else");
            userData = firebaseFirestore.collection("UserInformation")
                    .document("Users").collection("User").document(partnerId);

            userData.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            //user = (User) documentSnapshot.getString("User.");
                            //profile = (Profile) documentSnapshot.get("Profile");
                            Log.i(TAG, "onSuccess: ");
                            //Log.i(TAG, "onSuccess: user" + user + " profile = " + profile);
                            String backgroundUrl = documentSnapshot.getString("Profile.profile_background_image_url");
                            String profileUrl = documentSnapshot.getString("Profile.profile_avatar");
                            String title = documentSnapshot.getString("Profile.title");
                            String about_me = documentSnapshot.getString("Profile.about_me");
                            String city = documentSnapshot.getString("Profile.city");
                            String state = documentSnapshot.getString("Profile.state");
                            String location = city + ", " + state;
                            String goals = documentSnapshot.getString("Profile.goal");
                            String first_name = documentSnapshot.getString("User.first_name");
                            String last_name = documentSnapshot.getString("User.last_name");
                            String username = first_name + " " + last_name;

                            partnerProfileCollapsingToolbar.setTitle(username);
                            partnerTitleTextView.setText(title);
                            partnerAboutMeTextView.setText(about_me);
                            partnerGoalTextView.setText(goals);
                            partnerLocationTextView.setText(location);

                            //Load image into the backgound
                            Glide.with(getApplicationContext())
                                    .load(backgroundUrl)
                                    .into(partnerProfileBackgroundImageView);

                            //Input image into profile pic view
                            Glide.with(getApplicationContext())
                                    .load(profileUrl)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(partnerProfilePicCircleImageView);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "onFailure: ",e );
                }
            });


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.partner_menu, menu);
        addMenuItem = menu.findItem(R.id.addMenu);
        denyMenuItem = menu.findItem(R.id.denyMenu);
        currentPartnerDB = new CurrentPartnersDB(getApplicationContext());
        partnerId = getIntent().getStringExtra("CurrentPartnerId");
        //String className = getIntent().getStringExtra("ClassName");
        //user = currentPartnerDB.getUserDataFromSQLite(partnerId);

        /*if(className.equals("Partner")) {
            profile = currentPartnerDB.getCurrentPartnerProfileFromSQLLite(partnerId);
            user = currentPartnerDB.getUserDataFromSQLite(partnerId);
        }else if(className.equals("Favorite")){
            profile = currentPartnerDB.getFavoriteProfileFromSQLLite(partnerId);
            user = currentPartnerDB.getFavoriteUserDataFromSQLite(partnerId);
        }else if(className.equals("Follower")){
            profile = currentPartnerDB.getFollowerProfileFromSQLLite(partnerId);
            user = currentPartnerDB.getFollowerUserDataFromSQLite(partnerId);
        }*/

        loadProfileDataFromSQLite();

        currentPartnerFirestore = new CurrentPartnersFirestore(this);
        currentPartnerId = user.getUid();
        Log.i("CurrentPartnerProfile", "loadProfileDataFromSQLite: ID " + currentPartnerId);

        checkFavoriteStatus(menu, currentFirebaseUser.getUid(),user.getUid());
        checkPartnerStatus(menu, currentFirebaseUser.getUid(),user.getUid());

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
                changePartnerStatus(item, id);
                break;
            case R.id.denyMenu:
                changePartnerStatus(item, id);
                break;
            case R.id.favoriteMenu:
                if (isFav) {

                    currentFavIdsList = currentPartnerDB.getFavoritesIdFromSQLite();
                    isFav = !(currentPartnerFirestore.removeFavoriteIdFromFirestore(id, currentPartnerId));
                    if(!isFav)
                        item.setIcon(R.drawable.star);

                } else if(!isFav){

                    currentFavIdsList = currentPartnerDB.getFavoritesIdFromSQLite();
                    isFav = currentPartnerFirestore.addFavoriteIdToFirestore(id, currentPartnerId);
                    if(isFav)
                        item.setIcon(R.drawable.star_yellow);
                }
                break;
        }
            return super.onOptionsItemSelected(item);
        }

    public void checkPartnerStatus(final Menu menu, String currentUserID, String partnerId){
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserID);

        DocumentReference userConnections = userData.collection("Connections").document(partnerId);

        userConnections
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String partnerStatus = (String) documentSnapshot.getString("partner");
                        MenuItem menuItem = menu.findItem(R.id.addMenu);
                        if(partnerStatus.equals(PartnerStatus.False.getStatus())){
                            menuItem.setIcon(R.drawable.account_plus_white);
                        }else if(partnerStatus.equals(PartnerStatus.Pending_Sent.getStatus())){
                            menuItem.setIcon(R.drawable.account_remove);
                        }else if(partnerStatus.equals(PartnerStatus.Pending_Response.getStatus())){
                            menuItem.setIcon(R.drawable.account_plus);
                            menu.findItem(R.id.denyMenu).setVisible(true);
                        }else if(partnerStatus.equals(PartnerStatus.True.getStatus())){
                            menuItem.setIcon(R.drawable.account_check_green);
                        }
                    }
                });
    }

    public void checkFavoriteStatus(final Menu menu, String currentUserID, String partnerId){
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserID);

        DocumentReference userConnections = userData.collection("Connections").document(partnerId);

        userConnections
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Boolean favoriteStatus = documentSnapshot.getBoolean("favorite");
                        MenuItem menuItem = menu.findItem(R.id.favoriteMenu);
                        if(favoriteStatus.equals(false)){
                            menuItem.setIcon(R.drawable.star);
                            isFav = false;
                        }else if(favoriteStatus.equals(true)){
                            menuItem.setIcon(R.drawable.star_yellow);
                            isFav = true;
                        }
                    }
                });
    }

    public void changePartnerStatus(final MenuItem item, final String currentUserID){
        userData = firebaseFirestore.collection("UserInformation")
                .document("Users").collection("User").document(currentUserID);

        DocumentReference userConnections = userData.collection("Connections").document(partnerId);

        userConnections
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String partnerStatus = (String) documentSnapshot.getString("partner");
                        int itemSelected = item.getItemId();
                        switch(itemSelected) {
                            case R.id.addMenu:
                                if (partnerStatus.equals(PartnerStatus.Pending_Sent.getStatus()) ||
                                        partnerStatus.equals(PartnerStatus.True.getStatus())) {
                                    currentPartnerFirestore.requestNewPartnerToFirestore(currentUserID, currentPartnerId,
                                            PartnerStatus.False);
                                    item.setIcon(R.drawable.account_plus_white);
                                } else if (partnerStatus.equals(PartnerStatus.False.getStatus())) {
                                    currentPartnerFirestore.requestNewPartnerToFirestore(currentUserID, currentPartnerId,
                                            PartnerStatus.Pending_Sent);
                                    item.setIcon(R.drawable.account_remove);
                                } else if (partnerStatus.equals(PartnerStatus.Pending_Response.getStatus())) {
                                    currentPartnerFirestore.requestNewPartnerToFirestore(currentUserID, currentPartnerId,
                                            PartnerStatus.True);
                                    denyMenuItem.setVisible(false);
                                    item.setIcon(R.drawable.account_check_green);
                                } else {
                                    currentPartnerFirestore.requestNewPartnerToFirestore(currentUserID, currentPartnerId,
                                            PartnerStatus.Pending_Sent);
                                    item.setIcon(R.drawable.account_remove);
                                }
                                break;
                            case R.id.denyMenu:
                                currentPartnerFirestore.requestNewPartnerToFirestore(currentUserID, currentPartnerId,
                                        PartnerStatus.False);
                                item.setVisible(false);
                                addMenuItem.setIcon(R.drawable.account_plus_white);
                                break;
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private List<String> currentFavIdsList;
    private Boolean isFav = false;
    private String isPartner;
    private int added = -1;
    private String partnerId;
    MenuItem addMenuItem, denyMenuItem;
    }

