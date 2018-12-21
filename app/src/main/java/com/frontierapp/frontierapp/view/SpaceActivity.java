package com.frontierapp.frontierapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.frontierapp.frontierapp.R;
import com.frontierapp.frontierapp.databinding.ActivitySpaceBinding;
import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.model.Space;
import com.frontierapp.frontierapp.viewmodel.FeedViewModel;
import com.frontierapp.frontierapp.viewmodel.SpaceViewModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

public class SpaceActivity extends AppCompatActivity {
    private ActivitySpaceBinding spaceBinding;
    private Toolbar spaceToolbar;
    private FeedViewModel feedViewModel;
    private SpaceViewModel spaceViewModel;
    private Query feedQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spaceBinding = DataBindingUtil.setContentView(this, R.layout.activity_space);


        Intent intent = getIntent();
        String path = intent.getStringExtra("PATH");

        DocumentReference spaceReference = Firestore.myFirestore.document(path);

        spaceViewModel = ViewModelProviders.of(this).get(SpaceViewModel.class);
        spaceViewModel.retrieveSpace(spaceReference);

        feedQuery = spaceReference.collection(Firestore.FEED).orderBy("created", Query.Direction.DESCENDING);
        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
        feedViewModel.retrieveFeed(feedQuery);

        init();

    }

    public void init(){
        spaceViewModel.getSpace().observe(this, new Observer<Space>() {
            @Override
            public void onChanged(@Nullable Space space) {
                spaceToolbar = spaceBinding.spaceToolbar;
                spaceToolbar.setTitle(space.getName());
                spaceToolbar.setTitleTextColor(Color.WHITE);
                setSupportActionBar(spaceToolbar);
            }
        });


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
                //setFragment(new PartnerFragment());
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
         fragmentTransaction.replace(spaceBinding.spaceFrameLayout.getId(), fragment);
         fragmentTransaction.commit();
    }

    private Menu menu = null;
}
