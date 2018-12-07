/**
 * <h1>Current Partner Activity</h1>
 * The CurrentPartnerActivity class loads a list of the user's current partners
 *
 * @author Yoshua Isreal
 * @version 1.0
 * @since 2018-07-01
 */

package com.frontierapp.frontierapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class CurrentPartnersActivity extends AppCompatActivity {
    private static final String TAG = "CurrentPartnerActivity";
    RecyclerView currentPartnerRecyclerView;
    List<UserInformation> userInformationList;
    SwipeRefreshLayout currentPartnerSwipeRefreshLayout;
    Toolbar currentPartnersToolbar;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    CurrentPartnersDB currentPartnersDB;
    final Context context = this;

    private final List<User> userList = new ArrayList<>();
    private final List<ConnectionsViewDataModel> connectionsViewDataModelList = new ArrayList<>();
    private CurrentPartnerItemRecyclerAdapter currentPartnerItemRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_partners);

        //instantiateViews();
    }

    /**
     * This method is used to instantiate all views
     * in the activity_current_partners xml to variables in this class
     */
   /* public void instantiateViews(){
        currentPartnersToolbar = (Toolbar) findViewById(R.id.currentPartnersToolbar);
        currentPartnersToolbar.setTitle("Partners");

        setSupportActionBar(currentPartnersToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        currentPartnerRecyclerView = (RecyclerView) findViewById(R.id.currentPartnerRecyclerView);
        userInformationList = new ArrayList<>();

        currentPartnerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        currentPartnerRecyclerView.setHasFixedSize(true);
        currentPartnerItemRecyclerAdapter = new CurrentPartnerItemRecyclerAdapter(this,
                connectionsViewDataModelList);
        currentPartnerRecyclerView.setAdapter(currentPartnerItemRecyclerAdapter);

        currentPartnerSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(
                R.id.currentPartnerSwipeRefreshLayout);
        currentPartnerSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCurrentPartnerList();
            }
        });

        getCurrentPartnerList();
    }

    /**
     * This method is used to get the list of partners from the current_partners table
     * in sqlite and load each item in the recyclerview
     */
    /*public void getCurrentPartnerList(){
        //Collect data all the User IDs(Doc ID) from the User collection
        userList.clear();
        connectionsViewDataModelList.clear();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPartnersDB = new CurrentPartnersDB(context);

                Users users = currentPartnersDB.getCurrentPartnersDataFromSQLite();
                Profiles profiles = currentPartnersDB.getCurrentPartnersProfileFromSQLite();
                for(int i=0; i<users.size();i++) {
                    User user = users.get(i);
                    Profile profile = profiles.get(i);
                    user.setAvatar(profile.getProfile_avatar());
                    userList.add(user);
                }


                for(int j=0;j<userList.size();j++){
                    User user = userList.get(j);
                    ConnectionsViewDataModel currentPartnershipViewData = new ConnectionsViewDataModel();
                    String full_name = user.getFirst_name() +
                            " " + user.getLast_name();
                    currentPartnershipViewData.setCurrentPartnerName(full_name);
                    currentPartnershipViewData.setCurrentPartnerProfilePicUrl(user.getAvatar());
                    currentPartnershipViewData.setCurrentPartnerId(user.getUid());

                    connectionsViewDataModelList.add(currentPartnershipViewData);

                    currentPartnerItemRecyclerAdapter.notifyDataSetChanged();
                }

            }
        }, 1000);

    }*/

    /**
     * This method is used to refresh the list of partners from the current_partners table
     * in sqlite and load each item in the recyclerview when swipeRefreshLayout is used
     */
    /*public void refreshCurrentPartnerList(){
        currentPartnerSwipeRefreshLayout.setColorSchemeResources(R.color.colorLoadRefresh);
        //Collect data all the User IDs(Doc ID) from the User collection
        userList.clear();
        connectionsViewDataModelList.clear();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String first_name;
                String last_name;

                currentPartnersDB = new CurrentPartnersDB(context);

                Users users = currentPartnersDB.getCurrentPartnersDataFromSQLite();
                Profiles profiles = currentPartnersDB.getCurrentPartnersProfileFromSQLite();
                for(int i=0; i<users.size();i++) {
                    User user = users.get(i);
                    Profile profile = profiles.get(i);
                    user.setAvatar(profile.getProfile_avatar());
                    userList.add(user);
                }


                for(int j=0;j<userList.size();j++){
                    User user = userList.get(j);
                    ConnectionsViewDataModel currentPartnershipViewData = new ConnectionsViewDataModel();
                    String full_name = user.getFirst_name() +
                            " " + user.getLast_name();
                    currentPartnershipViewData.setCurrentPartnerName(full_name);
                    currentPartnershipViewData.setCurrentPartnerProfilePicUrl(user.getAvatar());
                    currentPartnershipViewData.setCurrentPartnerId(user.getUid());

                    connectionsViewDataModelList.add(currentPartnershipViewData);

                    currentPartnerSwipeRefreshLayout.setColorSchemeResources(R.color.colorFinishRefresh);
                    currentPartnerItemRecyclerAdapter.notifyDataSetChanged();
                }

                currentPartnerSwipeRefreshLayout.setRefreshing(false);
            }
        }, 500);

    }*/

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

                Intent profileIntent = new Intent(this, ProfileActivity.class);
                profileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(profileIntent);
                finish();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }
}
