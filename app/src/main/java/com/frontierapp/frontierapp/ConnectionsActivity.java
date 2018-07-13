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
        startBackgroundService();

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

        connectsViewPagerAdapter.addFragment(new PartnerFragment(this), "Partners" );
        connectsViewPagerAdapter.addFragment(new FavoriteFragment(this), "Favorites");
        connectsViewPagerAdapter.addFragment(new FollowerFragment(this), "Followers");

        connectsViewPager.setAdapter(connectsViewPagerAdapter);
        connectsTabLayout.setupWithViewPager(connectsViewPager, true);

    }

    @Override
    protected void onDestroy() {
        stopBackgroundService();
        super.onDestroy();
    }

    /**
     * This method starts the UserPartnerFirestoreBackgroundService and
     * the UserFavFirestoreBackgrounService background services
     */
    public void startBackgroundService(){
        Intent intent = new Intent(this, UserPartnersFirestoreBackgroundService.class);
        intent.putExtra("UserId", firebaseuser.getUid());
        startService(intent);

        Intent favIntent = new Intent(this, UserFavFirestoreBackgroundService.class);
        favIntent.putExtra("UserId", firebaseuser.getUid());
        startService(favIntent);

        Intent followerIntent = new Intent(this, UserFollowerFirestoreBackgroundService.class);
        followerIntent.putExtra("UserId", firebaseuser.getUid());
        startService(followerIntent);
    }

    /**
     * This method stops the UserPartnerFirestoreBackgroundService and
     * the UserFavFirestoreBackgrounService background services
     */
    public void stopBackgroundService(){
        Intent intent = new Intent(this, UserPartnersFirestoreBackgroundService.class);
        stopService(intent);

        Intent favIntent = new Intent(this, UserFavFirestoreBackgroundService.class);
        stopService(favIntent);
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
                //go back to the profile activity

                //Intent profileIntent = new Intent(this, ProfileActivity.class);
                //profileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(profileIntent);
                finish();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    private final FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
}
