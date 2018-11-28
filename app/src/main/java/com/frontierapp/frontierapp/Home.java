package com.frontierapp.frontierapp;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ListView feedListView;
    TextView titleTextView;
    ImageView navImageView;
    TextView navEmail;
    TextView navName;
    View headerView;

    UserFirestore userFirestore = new UserFirestore();
    DocumentReference userData = userFirestore.getUserData(userFirestore.getCurrentUserId());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        instantiateViews();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadUserProfileFromFirestore();
    }

    public void instantiateViews(){
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Frontier");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewPager_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //adapter.addFragment(new HomePage(),"Home");
        adapter.addFragment(new Hub(),"Hub");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView= (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Navigation Drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.open_drawer,R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });*/

        headerView = navigationView.getHeaderView(0);
        navName = (TextView) headerView.findViewById(R.id.userName);
        navEmail = (TextView) headerView.findViewById(R.id.email);
        navImageView = (ImageView) headerView.findViewById(R.id.profilePic);
    }

    public void loadUserProfileFromFirestore() {
        userData
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String userName = documentSnapshot.getString(userFirestore.FIRST_NAME) +
                                " "
                                + documentSnapshot.getString(userFirestore.LAST_NAME);
                        String userEmail = documentSnapshot.getString(userFirestore.EMAIL);
                        String userProfilePic = documentSnapshot.getString(userFirestore.PROFILE_AVATAR);


                        //Connect Views of Navigation bar
                        navName.setText(userName);
                        navEmail.setText(userEmail);

                        Glide.with(headerView)
                                .load(userProfilePic)
                                .apply(RequestOptions.circleCropTransform())
                                .into(navImageView);

                        headerView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent profileScreen = new Intent(getApplicationContext(), ProfileActivity.class);
                                startActivity(profileScreen);
                                finish();
                            }
                        });
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.welcome_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.settings) {
            Toast.makeText(getApplicationContext(), "Selected settings menu", Toast.LENGTH_LONG).show();
            Log.i("Information", "Options menu clicked");
        }
        else if (id == R.id.logout){
            userFirestore.getCurrentFirebaseAuth().signOut();

            Intent signout = new Intent(Home.this, MainActivity.class);
            startActivity(signout);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();

        switch(id){
            case R.id.profile:
                Intent profileScreen = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profileScreen);
                finish();
                break;
            case R.id.navMessages:
                Intent chatActivity = new Intent(getApplicationContext(), ChatsActivity.class);
                startActivity(chatActivity);
                finish();
                break;
            case R.id.connections:
                Intent connectsIntent = new Intent(this, ConnectionsActivity.class);
                startActivity(connectsIntent);
                break;
            case R.id.notifications:
                Intent notificationIntent = new Intent(this, NotificationActivity.class);
                startActivity(notificationIntent);
                break;
            case R.id.Circles:
                Intent spaceIntent = new Intent(this, SpacesActivity.class);
                startActivity(spaceIntent);
                finish();
                break;

            case R.id.vbc:

                break;


        }

        drawerLayout.closeDrawer(Gravity.START);
        return true;
    }

}

