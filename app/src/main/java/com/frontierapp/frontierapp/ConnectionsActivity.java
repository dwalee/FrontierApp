package com.frontierapp.frontierapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConnectionsActivity extends AppCompatActivity {
    ViewPager connectsViewPager;
    TabLayout connectsTabLayout;
    Toolbar connectionsToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);

        instantiateViews();
    }

    public void instantiateViews(){
        connectionsToolbar = (Toolbar) findViewById(R.id.connectionsToolbar);
        setSupportActionBar(connectionsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        connectsViewPager = (ViewPager) findViewById(R.id.connectionsViewPager);
        connectsTabLayout = (TabLayout) findViewById(R.id.connectionsTabLayout);

        ViewPagerAdapter connectsViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        connectsViewPagerAdapter.addFragment(new PartnerFragment(), "Partners" );
        connectsViewPagerAdapter.addFragment(new FavoriteFragment(), "Favorites");
        connectsViewPagerAdapter.addFragment(new FollowerFragment(), "Followers");

        connectsViewPager.setAdapter(connectsViewPagerAdapter);
        connectsTabLayout.setupWithViewPager(connectsViewPager, true);

    }

    /**
     * This method is used to add controls to the menus in tool bar
     * @param item This parameter captures the value of the menu item selected
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //go back
                finish();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

}
