package com.frontierapp.frontierapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerFragment extends Fragment {
    private static final String TAG = "PartnerFragment";
    RecyclerView partnerRecyclerView;
    List<UserInformation> userInformationList;
    SwipeRefreshLayout partnerSwipeRefreshLayout;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    CurrentPartnersDB currentPartnersDB;
    Context context;

    private final List<User> userList = new ArrayList<>();
    private final List<CurrentPartnershipViewData> currentPartnershipViewDataList = new ArrayList<>();
    private CurrentPartnerItemRecyclerAdapter currentPartnerItemRecyclerAdapter;
    View view;

    public PartnerFragment() {
        // Required empty public constructor
    }

    public PartnerFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_partner, container, false);
        //startBackgroundService();

        instantiateViews(view);
        return view;
    }

    /**
     * This method is used to instantiate all views
     * in the activity_current_partners xml to variables in this class
     */
    public void instantiateViews(View view){

        partnerRecyclerView = (RecyclerView) view.findViewById(R.id.partnerRecyclerView);
        userInformationList = new ArrayList<>();

        partnerRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        partnerRecyclerView.setHasFixedSize(true);
        currentPartnerItemRecyclerAdapter = new CurrentPartnerItemRecyclerAdapter(context,
                currentPartnershipViewDataList);
        partnerRecyclerView.setAdapter(currentPartnerItemRecyclerAdapter);

        partnerSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(
                R.id.partnerRefreshLayout);
        partnerSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
    public void getCurrentPartnerList(){
        //Collect data all the User IDs(Doc ID) from the User collection
        userList.clear();
        currentPartnershipViewDataList.clear();

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
                    user.setAvatar(profile.getProfileAvatarUrl());
                    userList.add(user);
                }


                for(int j=0;j<userList.size();j++){
                    User user = userList.get(j);
                    CurrentPartnershipViewData currentPartnershipViewData = new CurrentPartnershipViewData();
                    String full_name = user.getFirst_name() +
                            " " + user.getLast_name();
                    currentPartnershipViewData.setCurrentPartnerName(full_name);
                    currentPartnershipViewData.setCurrentPartnerProfilePicUrl(user.getAvatar());
                    currentPartnershipViewData.setCurrentPartnerId(user.getUid());

                    currentPartnershipViewDataList.add(currentPartnershipViewData);

                    currentPartnerItemRecyclerAdapter.notifyDataSetChanged();
                }

            }
        }, 5000);

    }

    /**
     * This method is used to refresh the list of partners from the current_partners table
     * in sqlite and load each item in the recyclerview when swipeRefreshLayout is used
     */
    public void refreshCurrentPartnerList(){
        partnerSwipeRefreshLayout.setColorSchemeResources(R.color.colorLoadRefresh);
        //Collect data all the User IDs(Doc ID) from the User collection
        userList.clear();
        currentPartnershipViewDataList.clear();

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
                    user.setAvatar(profile.getProfileAvatarUrl());
                    userList.add(user);
                }


                for(int j=0;j<userList.size();j++){
                    User user = userList.get(j);
                    CurrentPartnershipViewData currentPartnershipViewData = new CurrentPartnershipViewData();
                    String full_name = user.getFirst_name() +
                            " " + user.getLast_name();
                    currentPartnershipViewData.setCurrentPartnerName(full_name);
                    currentPartnershipViewData.setCurrentPartnerProfilePicUrl(user.getAvatar());
                    currentPartnershipViewData.setCurrentPartnerId(user.getUid());

                    currentPartnershipViewDataList.add(currentPartnershipViewData);

                    partnerSwipeRefreshLayout.setColorSchemeResources(R.color.colorFinishRefresh);
                    currentPartnerItemRecyclerAdapter.notifyDataSetChanged();
                }

                partnerSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);

    }

    @Override
    public void onDestroyView() {
        //stopBackgroundService();
        super.onDestroyView();
    }

    /**
     * This method starts the UserPartnerFirestoreBackgroundService and
     * the UserFavFirestoreBackgrounService background services
     */
    public void startBackgroundService(){
        Intent intent = new Intent(context, UserPartnersFirestoreBackgroundService.class);
        intent.putExtra("UserId", firebaseuser.getUid());
        context.startService(intent);

        Intent favIntent = new Intent(context, UserFavFirestoreBackgroundService.class);
        favIntent.putExtra("UserId", firebaseuser.getUid());
        context.startService(favIntent);
    }

    /**
     * This method stops the UserPartnerFirestoreBackgroundService and
     * the UserFavFirestoreBackgrounService background services
     */
    public void stopBackgroundService(){
        Intent intent = new Intent(context, UserPartnersFirestoreBackgroundService.class);
        context.stopService(intent);

        Intent favIntent = new Intent(context, UserFavFirestoreBackgroundService.class);
        context.stopService(favIntent);
    }


    private final FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
}
