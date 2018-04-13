package com.frontierapp.frontierapp;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

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
        navigationView= (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);


        //Navigation Drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
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
            case R.id.partners:
                Intent partnerScreen = new Intent(this, Partners.class);
                startActivity(partnerScreen);
                break;

            case R.id.inbox:

                break;

            case R.id.outbox:

                break;

            case R.id.notifications:

                break;

            case R.id.vbc:

                break;


        }

        return true;
    }

    }

