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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar; ViewPager viewPager; TabLayout tabLayout; NavigationView navigationView;
    DrawerLayout drawerLayout; ListView feedListView;
    FirebaseFirestore mfirestore; FirebaseUser firebaseuser;
    TextView titleTextView;
    UserFirestore userFirestore;
    UserDB userDB;
    User user;
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        titleTextView = (TextView) findViewById(R.id.titleTextView);
        mfirestore = FirebaseFirestore.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);

        //CREATE ACTIONBAR FROM WITH TOOLBAR VIEW
        setSupportActionBar(toolbar);
        //SET TITLE FOR ACTIONBAR
        getSupportActionBar().setTitle("Frontier");
        //SET METHOD TO SHOW TITLE IN ACTIONBAR
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //SET METHOD TO SHOW back arrow in Action Bar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewPager_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new HomePage(),"Home");
        adapter.addFragment(new Hub(),"Hub");
        adapter.addFragment(new Partners(),"Partners");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView= (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //View headerView = navigationView.getHeaderView(0);
        //LinearLayout headerImageView = (t) headerView.findViewById(R.id.profileBackground);
        /*Glide.with(this)
                .load("https://vignette.wikia.nocookie.net/dragonball/images/c/c2/" +
                        "Gizard_Wasteland_DBZ_Ep_33_003.png/revision/latest?cb=20170827060816")
                .into(headerImageView);*/

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

        firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseuser.getUid();
        startBackgroundService();
        //downloadUserProfileFromFirestore(userId);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadDataToSQLite();
            }
        }, 2000);

    }

    public void startBackgroundService(){
        Intent intent = new Intent(this, UserProfileFirestoreBackgroundService.class);
        intent.putExtra("UserId", firebaseuser.getUid());
        startService(intent);
    }

    public void stopBackgroundService(){
        Intent intent = new Intent(this, UserProfileFirestoreBackgroundService.class);
        stopService(intent);
    }

    public void downloadUserProfileFromFirestore(String userId){
        userFirestore = new UserFirestore(getApplicationContext());
        userFirestore.getUserProfileDataFromFirestore(userId);
    }

    public void loadDataToSQLite() {

        userDB = new UserDB(getApplicationContext());

        user = userDB.getUserDataFromSQLite();
        profile = userDB.getProfileDataFromSQLite();

        String userName = user.getFirst_name() + " " + user.getLast_name();

        //Connect Views of Navigation bar
        View headerView = navigationView.getHeaderView(0);
        TextView navName = (TextView) headerView.findViewById(R.id.userName);
        TextView navEmail = (TextView) headerView.findViewById(R.id.email);
        ImageView navImageView = (ImageView) headerView.findViewById(R.id.profilePic);
        navName.setText(userName);
        navEmail.setText(user.getEmail());

        Log.i("Avatar/", "loadDataToSQLite: " + profile.getProfileAvatarUrl());
        Glide.with(headerView)
                .load(profile.getProfileAvatarUrl())
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
            FirebaseAuth.getInstance().signOut();
            Intent signout = new Intent(Home.this, MainActivity.class);
            startActivity(signout);
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
            case R.id.partners:
                Intent partnerScreen = new Intent(this, Partners.class);
                startActivity(partnerScreen);
                break;

            case R.id.notifications:

                break;

            case R.id.vbc:

                break;


        }

        drawerLayout.closeDrawer(Gravity.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        stopBackgroundService();
        super.onDestroy();
    }
}

