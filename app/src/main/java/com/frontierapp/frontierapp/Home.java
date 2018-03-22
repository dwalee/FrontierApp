package com.frontierapp.frontierapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);

        //ListView feedListView = (ListView) findViewById(R.id.feedListView);

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


        adapter.addFragment(new homePage(),"Home");
        adapter.addFragment(new Hub(),"Hub");
        adapter.addFragment(new Partners(),"Partners");

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(adapter);

        for(int i =1; i <=5; i++){
            Map<String, String> postInfo = new HashMap<String, String>();
            postInfo.put("context", "Tweet Content" + Integer.toString(i));
            postInfo.put("username", "User" + Integer.toString(i));
            postData.add(postInfo);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,postData, android.R.layout.simple_list_item_1, new String[] {"content", "username"}, new int [] {android.R.id.text1, android.R.id.text2});

        //feedListView.setAdapter(simpleAdapter);
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
            Toast.makeText(getApplicationContext(), "Selected Settings menu",Toast.LENGTH_LONG).show();
            Log.i("Information","Options menu clicked");
        }

        return super.onOptionsItemSelected(item);
    }


}
