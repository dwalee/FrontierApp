package com.frontierapp.frontierapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.frontierapp.frontierapp.R;
import com.frontierapp.frontierapp.adapter.ViewPagerAdapter;
import com.frontierapp.frontierapp.databinding.ActivityConnectionsBinding;
import com.frontierapp.frontierapp.viewmodel.FavoriteViewModel;
import com.frontierapp.frontierapp.viewmodel.FollowerViewModel;
import com.frontierapp.frontierapp.viewmodel.PartnerViewModel;

public class ConnectionsActivity extends AppCompatActivity {
    private static final String TAG = "ConnectionsActivity";
    private ActivityConnectionsBinding activityConnectionsBinding;
    private ViewPager connectsViewPager;
    private TabLayout connectsTabLayout;
    private Toolbar connectionsToolbar;
    private FollowerViewModel followerViewModel;
    private PartnerViewModel partnerViewModel;
    private FavoriteViewModel favoriteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityConnectionsBinding = DataBindingUtil.setContentView(this, R.layout.activity_connections);
        init();
        followerViewModel = ViewModelProviders.of(this).get(FollowerViewModel.class);
        partnerViewModel = ViewModelProviders.of(this).get(PartnerViewModel.class);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
    }

    public void init() {
        connectionsToolbar = activityConnectionsBinding.connectionsToolbar;
        setSupportActionBar(connectionsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        connectsViewPager = activityConnectionsBinding.connectionsViewPager;
        connectsTabLayout = activityConnectionsBinding.connectionsTabLayout;

        ViewPagerAdapter connectsViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        connectsViewPagerAdapter.addFragment(new PartnerFragment(), "Partners");
        connectsViewPagerAdapter.addFragment(new FavoriteFragment(), "Favorites");
        connectsViewPagerAdapter.addFragment(new FollowerFragment(), "Followers");

        connectsViewPager.setAdapter(connectsViewPagerAdapter);
        connectsTabLayout.setupWithViewPager(connectsViewPager, true);

    }

    /**
     * This method is used to add controls to the menus in tool bar
     *
     * @param item This parameter captures the value of the menu item selected
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //go back
                finish();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

}
