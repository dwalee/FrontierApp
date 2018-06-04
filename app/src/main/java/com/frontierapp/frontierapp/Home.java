package com.frontierapp.frontierapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ListView feedListView;
    FirebaseFirestore mfirestore;
    FirebaseUser firebaseuser;
    ImageView profilePicImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        mfirestore = FirebaseFirestore.getInstance();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);

        List<Map<String, String>> postData = new ArrayList<Map<String, String>>();

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

        for(int i =1; i <=5; i++){
            Map<String, String> postInfo = new HashMap<String, String>();
            postInfo.put("context", "Tweet Content" + Integer.toString(i));
            postInfo.put("username", "User" + Integer.toString(i));
            postData.add(postInfo);
        }

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
//

        //Navigation Drawer data
        firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = firebaseuser.getUid();

        mfirestore.collection("UserInformation")
                .document("Users")
                .collection("User")
                .document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    //User user = new User();

                    //Get data from the current user
                    String first_name = "";
                    String last_name;
                    //String profileUrl = document.getString("userAvatarUrl");
                    String email = "";
                    String aboutMe = "";
                    String city = "";
                    String state = "";
                    String goal = "";
                    String profileUrl = "";
                    String profileBackgroundUrl = "";
                    String title = "";

                    //Create new String to spell out full name
                    String userName = "";
                    //Combine city and state
                    String location;


                    try{
                        //Get data from the current user
                        first_name = document.getString("User.first_name");
                        last_name = document.getString("User.last_name");
                        //String profileUrl = document.getString("userAvatarUrl");
                        email = document.getString("User.email");
                        aboutMe = document.getString("Profile.about_me");
                        city = document.getString("Profile.city");
                        state = document.getString("Profile.state");
                        goal = document.getString("Profile.goal");
                        profileUrl = document.getString("Profile.profile_avatar");
                        profileBackgroundUrl = document.getString(
                                "Profile.profile_background_image_url");
                        title = document.getString("Profile.title");

                        //Create new String to spell out full name
                        userName = first_name + " " + last_name;
                        //Combine city and state
                        location = city + ", " + state;

                        SQLiteDatabase database = openOrCreateDatabase(
                                "User_Data",
                                MODE_PRIVATE,
                                null);

                        database.execSQL("DROP TABLE user_profile");
                        SQLiteDatabase userDatabase = openOrCreateDatabase(
                                "User_Data",MODE_PRIVATE,
                                null);


                        String createUserProfileTableSQL = "CREATE TABLE IF NOT EXISTS user_profile ";
                        String userProfileDataFormat = "(user_id VARCHAR, first_name VARCHAR, last_name VARCHAR," +
                                "email VARCHAR, about_me VARCHAR, city VARCHAR, state VARCHAR(2), goal VARCHAR," +
                                "profile_url VARCHAR, profile_background_url VARCHAR, title VARCHAR)";

                        String createQuery = createUserProfileTableSQL + userProfileDataFormat;

                        userDatabase.execSQL(createQuery);

                        String user_id = userId.toString();

                        ContentValues insertValues = new ContentValues();
                        insertValues.put("user_id", user_id);
                        insertValues.put("first_name", first_name);
                        insertValues.put("last_name", last_name);
                        insertValues.put("email", email);
                        insertValues.put("about_me", aboutMe);
                        insertValues.put("city", city);
                        insertValues.put("state", state);
                        insertValues.put("goal", goal);
                        insertValues.put("profile_url", profileUrl);
                        insertValues.put("profile_background_url", profileBackgroundUrl);
                        insertValues.put("title", title);

                        userDatabase.insert("user_profile", null, insertValues);

                        Cursor c = userDatabase.rawQuery("SELECT * FROM user_profile", null);

                        c.moveToFirst();
                        /*while(c != null){
                            Log.i("FirstName: ", c.getString(firstNameIndex));
                            Log.i("ProfileUrl: ", c.getString(profileUrlIndex));
                            c.moveToNext();
                        }*/

                        database.close();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                    //Connect Views of Navigation bar
                    View headerView = navigationView.getHeaderView(0);
                    TextView navName = (TextView) headerView.findViewById(R.id.userName);
                    TextView navEmail = (TextView) headerView.findViewById(R.id.email);
                    navName.setText(userName);
                    navEmail.setText(email);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.welcome_menu, menu);

        profilePicImageView = (ImageView) findViewById(R.id.profilePic);
        //Go to profile activity if profile pic is clicked
        profilePicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileScreen = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profileScreen);
            }
        });
        Glide.with(this)
                .load("https://pbs.twimg.com/media/DXVX493U8AAvqLf.jpg")
                .apply(RequestOptions.circleCropTransform())
                .into(profilePicImageView);
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

    }

