package com.frontierapp.frontierapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowerFragment extends Fragment {
    private static final String TAG = "FollowerFragment";
    RecyclerView followerRecyclerView;
    List<UserInformation> userInformationList;
    SwipeRefreshLayout followerSwipeRefreshLayout;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    CurrentPartnersDB currentPartnersDB;
    Context context;
    View view;

    private final List<User> userList = new ArrayList<>();
    private final List<FollowerViewData> followerViewDataList = new ArrayList<>();
    private FollowerItemRecyclerAdapter followerItemRecyclerAdapter;

    public FollowerFragment() {
        // Required empty public constructor
    }

    public FollowerFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_follower, container, false);
        //startBackgroundService();

        instantiateViews(view);
        return view;
    }

    public View instantiateViews(View view){
        followerRecyclerView = (RecyclerView) view.findViewById(R.id.followerRecyclerview);
        userInformationList = new ArrayList<>();

        followerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        followerRecyclerView.setHasFixedSize(true);
        followerItemRecyclerAdapter = new FollowerItemRecyclerAdapter(context, followerViewDataList);
        followerRecyclerView.setAdapter(followerItemRecyclerAdapter);

        followerSwipeRefreshLayout = (SwipeRefreshLayout)  view.findViewById(
                R.id.followerSwipeRefreshLayout);
        followerSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFollowerList();
            }
        });

        getFollowerList();

        return view;
    }

    //Get the list of users and apply it to the partners recyclerview
    public void getFollowerList(){
        //Collect data all the User IDs(Doc ID) from the User collection
        userList.clear();
        followerViewDataList.clear();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                currentPartnersDB = new CurrentPartnersDB(context);

                Log.i(TAG, "run: before users = " +
                        currentPartnersDB.getFollowersUserDataFromSQLite());
                Users users = null;
                Profiles profiles = null;
                int timeout = 0;

                do {
                    users = currentPartnersDB.getFollowersUserDataFromSQLite();
                    profiles = currentPartnersDB.getFollowersProfileFromSQLite();
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        Log.w(TAG, "run: ", e);
                    }
                    timeout++;
                }while((users == (null) || profiles == (null)) && !(timeout >= 3));

                Log.i(TAG, "run: users = " + users);
                if(users != (null) && profiles != (null)) {
                    for (int i = 0; i < users.size(); i++) {
                        User user = users.get(i);
                        Log.i(TAG, "run: user.getFirst_name = " + user.getFirst_name());
                        Profile profile = profiles.get(i);
                        user.setAvatar(profile.getProfileAvatarUrl());
                        userList.add(user);
                    }


                    for (int j = 0; j < userList.size(); j++) {
                        User user = userList.get(j);
                        Log.i(TAG, "run: user(userList) = " + user);
                        FollowerViewData followerViewData = new FollowerViewData();
                        String full_name = user.getFirst_name() +
                                " " + user.getLast_name();
                        Log.i(TAG, "run: full_name = " + full_name);
                        followerViewData.setFollowerName(full_name);
                        followerViewData.setFollowerProfilePicUrl(user.getAvatar());
                        followerViewData.setFollowerId(user.getUid());

                        followerViewDataList.add(followerViewData);

                        followerItemRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        }, 400);
    }

    public void refreshFollowerList(){
        followerSwipeRefreshLayout.setColorSchemeResources(R.color.colorLoadRefresh);
        //Collect data all the User IDs(Doc ID) from the User collection
        userList.clear();
        followerViewDataList.clear();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                currentPartnersDB = new CurrentPartnersDB(context);

                Users users = null;
                Profiles profiles = null;
                int timeout = 0;

                do {
                    users = currentPartnersDB.getFollowersUserDataFromSQLite();
                    profiles = currentPartnersDB.getFollowersProfileFromSQLite();
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        Log.w(TAG, "run: ", e);
                    }
                    timeout++;
                } while ((users == (null) || profiles == (null)) && !(timeout >= 3));

                if (users != (null) && profiles != (null)) {
                    for (int j = 0; j < userList.size(); j++) {
                        User user = userList.get(j);
                        FollowerViewData followerViewData = new FollowerViewData();
                        String full_name = user.getFirst_name() +
                                " " + user.getLast_name();
                        followerViewData.setFollowerName(full_name);
                        followerViewData.setFollowerProfilePicUrl(user.getAvatar());
                        followerViewData.setFollowerId(user.getUid());

                        followerViewDataList.add(followerViewData);

                        followerItemRecyclerAdapter.notifyDataSetChanged();
                    }
                    followerSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 500);
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

    @Override
    public void onDestroyView() {
        //stopBackgroundService();
        super.onDestroyView();
    }

    private final FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();

}
