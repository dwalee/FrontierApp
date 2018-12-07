package com.frontierapp.frontierapp;

import android.content.Context;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class CurrentConnectionsProfileActivity extends AppCompatActivity {
    private static final String TAG = "CurrentPartnerProfileA";
    private final String CURRENT_CONNECTIONS_ID = "current_connections_id";
    private final String CURRENT_NOTIFICATION_ID = "current_notification_id";

    FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
    CollapsingToolbarLayout partnerProfileCollapsingToolbar;
    Toolbar partnerProfileToolbar;
    ImageView partnerProfileBackgroundImageView, partnerProfilePicCircleImageView;
    TextView partnerTitleTextView, partnerAboutMeTextView, partnerLocationTextView, partnerGoalTextView;

    private final UserFirestore userFirestore = new UserFirestore();
    protected DocumentReference userData = userFirestore.getUserData(userFirestore.getCurrentUserId());
    private final Context context = this;
    private final CurrentConnectionsFirestore currentPartnerFirestore = new CurrentConnectionsFirestore(context);
    private final NotificationFirestore notificationFirestore = new NotificationFirestore();
    private String connectionId;
    private String notificationId;

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


        setSupportActionBar(partnerProfileToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCurrentConnectionsUserData();
    }

    public void getCurrentConnectionsUserData() {
        connectionId = getIntent().getStringExtra(CURRENT_CONNECTIONS_ID);

        userFirestore.getUserData(connectionId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String backgroundUrl = documentSnapshot.getString(userFirestore.PROFILE_BACKGROUND);
                        String profileUrl = documentSnapshot.getString(userFirestore.PROFILE_AVATAR);
                        String title = documentSnapshot.getString(userFirestore.PROFILE_TITLE);
                        String about_me = documentSnapshot.getString(userFirestore.ABOUT_ME);
                        String city = documentSnapshot.getString(userFirestore.CITY);
                        String state = documentSnapshot.getString(userFirestore.STATE);
                        String location = city + ", " + state;
                        String goals = documentSnapshot.getString(userFirestore.GOAL);
                        String first_name = documentSnapshot.getString(userFirestore.FIRST_NAME);
                        String last_name = documentSnapshot.getString(userFirestore.LAST_NAME);
                        String username = first_name + " " + last_name;

                        partnerProfileCollapsingToolbar.setTitle(username);
                        partnerTitleTextView.setText(title);
                        partnerAboutMeTextView.setText(about_me);
                        partnerGoalTextView.setText(goals);
                        partnerLocationTextView.setText(location);

                        //Load image into the backgound
                        Glide.with(context)
                                .load(backgroundUrl)
                                .into(partnerProfileBackgroundImageView);

                        //Input image into profile pic view
                        Glide.with(context)
                                .load(profileUrl)
                                .apply(RequestOptions.circleCropTransform())
                                .into(partnerProfilePicCircleImageView);
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.partner_menu, menu);
        addMenuItem = menu.findItem(R.id.addMenu);
        denyMenuItem = menu.findItem(R.id.denyMenu);

        checkFavoriteStatus(menu);
        checkPartnerStatus(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //go back
                finish();
                break;
            case R.id.addMenu:
                changePartnerStatus(item);
                break;
            case R.id.denyMenu:
                changePartnerStatus(item);
                break;
            case R.id.favoriteMenu:
                changeFavoriteStatus(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkPartnerStatus(final Menu menu) {
        connectionId = getIntent().getStringExtra(CURRENT_CONNECTIONS_ID);

        DocumentReference userConnections = userData
                .collection(userFirestore.CONNECTIONS)
                .document(connectionId);

        userConnections
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String partnerStatus = (String) documentSnapshot.getString(userFirestore.PARTNER);
                        MenuItem menuItem = menu.findItem(R.id.addMenu);
                        if (partnerStatus.equals(PartnerStatus.False.getStatus())) {
                            menuItem.setIcon(R.drawable.account_plus_white);
                        } else if (partnerStatus.equals(PartnerStatus.Pending_Sent.getStatus())) {
                            menuItem.setIcon(R.drawable.account_remove);
                        } else if (partnerStatus.equals(PartnerStatus.Pending_Response.getStatus())) {
                            menuItem.setIcon(R.drawable.account_plus);
                            menu.findItem(R.id.denyMenu).setVisible(true);
                        } else if (partnerStatus.equals(PartnerStatus.True.getStatus())) {
                            menuItem.setIcon(R.drawable.account_check_green);
                        }
                    }
                });
    }

    public void checkFavoriteStatus(final Menu menu) {
        connectionId = getIntent().getStringExtra(CURRENT_CONNECTIONS_ID);

        DocumentReference userConnections = userData
                .collection(userFirestore.CONNECTIONS)
                .document(connectionId);

        userConnections
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Boolean favoriteStatus = documentSnapshot.getBoolean(userFirestore.FAVORITE);
                        MenuItem menuItem = menu.findItem(R.id.favoriteMenu);
                        if (favoriteStatus.equals(false)) {
                            menuItem.setIcon(R.drawable.star);
                            isFav = false;
                        } else if (favoriteStatus.equals(true)) {
                            menuItem.setIcon(R.drawable.star_yellow);
                            isFav = true;
                        }
                    }
                });
    }

    public void changePartnerStatus(final MenuItem item) {
        connectionId = getIntent().getStringExtra(CURRENT_CONNECTIONS_ID);
        notificationId = getIntent().getStringExtra(CURRENT_NOTIFICATION_ID);

        DocumentReference userConnections = userData
                .collection(userFirestore.CONNECTIONS)
                .document(connectionId);

        userConnections
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String partnerStatus = (String) documentSnapshot.getString(userFirestore.PARTNER);
                        int itemSelected = item.getItemId();
                        switch (itemSelected) {
                            case R.id.addMenu:
                                if (partnerStatus.equals(PartnerStatus.Pending_Sent.getStatus()) ||
                                        partnerStatus.equals(PartnerStatus.True.getStatus())) {
                                    currentPartnerFirestore.initiateNewPartnershipToFirestore(userFirestore.getCurrentUserId(), connectionId,
                                            PartnerStatus.False);
                                    if(notificationId != null)
                                        notificationFirestore.updateNotification(notificationId,true);
                                    item.setIcon(R.drawable.account_plus_white);
                                } else if (partnerStatus.equals(PartnerStatus.False.getStatus())) {
                                    currentPartnerFirestore.initiateNewPartnershipToFirestore(userFirestore.getCurrentUserId(), connectionId,
                                            PartnerStatus.Pending_Sent);
                                    item.setIcon(R.drawable.account_remove);
                                } else if (partnerStatus.equals(PartnerStatus.Pending_Response.getStatus())) {
                                    currentPartnerFirestore.initiateNewPartnershipToFirestore(userFirestore.getCurrentUserId(), connectionId,
                                            PartnerStatus.True);
                                    if(notificationId != null)
                                        notificationFirestore.updateNotification(notificationId,true);
                                    denyMenuItem.setVisible(false);
                                    item.setIcon(R.drawable.account_check_green);
                                } else {
                                    currentPartnerFirestore.initiateNewPartnershipToFirestore(userFirestore.getCurrentUserId(), connectionId,
                                            PartnerStatus.Pending_Sent);
                                    item.setIcon(R.drawable.account_remove);
                                }
                                break;
                            case R.id.denyMenu:
                                currentPartnerFirestore.initiateNewPartnershipToFirestore(userFirestore.getCurrentUserId(), connectionId,
                                        PartnerStatus.False);
                                item.setVisible(false);
                                notificationFirestore.updateNotification(notificationId,true);
                                addMenuItem.setIcon(R.drawable.account_plus_white);
                                break;
                        }
                    }
                });
    }

    public void changeFavoriteStatus(final MenuItem item) {
        connectionId = getIntent().getStringExtra(CURRENT_CONNECTIONS_ID);

        DocumentReference userConnections = userData
                .collection(userFirestore.CONNECTIONS)
                .document(connectionId);

        userConnections
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Boolean favoriteStatus = documentSnapshot.getBoolean(userFirestore.FAVORITE);
                        isFav = favoriteStatus;
                        if (isFav) {
                            currentPartnerFirestore.removeFavoriteIdFromFirestore(userFirestore.getCurrentUserId(), connectionId);
                            item.setIcon(R.drawable.star);
                        } else if (!isFav) {
                            currentPartnerFirestore.addFavoriteIdToFirestore(userFirestore.getCurrentUserId(), connectionId);
                            item.setIcon(R.drawable.star_yellow);
                        }
                    }
                });
    }

    private Boolean isFav = false;
    MenuItem addMenuItem, denyMenuItem;
}

