package com.frontierapp.frontierapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class SpaceActivity extends AppCompatActivity {
    //BottomNavigationView spaceBottomNavigationView;
    Toolbar spaceToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);
        instantiateViews();
    }

    public void instantiateViews(){
        //spaceBottomNavigationView = (BottomNavigationView) findViewById(R.id.spaceBottomNavigation);
        spaceToolbar = (Toolbar) findViewById(R.id.spaceToolbar);
        spaceToolbar.setTitle("Space");
        spaceToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(spaceToolbar);

        setFragment(new FeedFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.space_bottom_navigation_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.spaceProjectMenuItem:
                menu.findItem(R.id.spaceFeedMenuItem).setVisible(true);
                menu.findItem(R.id.spaceMembersMenuItem).setVisible(true);
                menu.findItem(R.id.spaceProjectMenuItem).setVisible(false);
                setFragment(new ProjectFragment());
                break;
            case R.id.spaceMembersMenuItem:
                menu.findItem(R.id.spaceFeedMenuItem).setVisible(true);
                menu.findItem(R.id.spaceMembersMenuItem).setVisible(false);
                menu.findItem(R.id.spaceProjectMenuItem).setVisible(true);
                setFragment(new PartnerFragment());
                break;
            case R.id.spaceFeedMenuItem:
                menu.findItem(R.id.spaceFeedMenuItem).setVisible(false);
                menu.findItem(R.id.spaceMembersMenuItem).setVisible(true);
                menu.findItem(R.id.spaceProjectMenuItem).setVisible(true);
                setFragment(new FeedFragment());
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void setFragment(Fragment fragment){
         FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
         fragmentTransaction.replace(R.id.spaceFrameLayout, fragment);
         fragmentTransaction.commit();
    }

    private Menu menu = null;
}
