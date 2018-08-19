package com.frontierapp.frontierapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private static final String TAG = "FavoriteFragment";
    RecyclerView favoriteRecyclerView;
    List<UserInformation> userInformationList;
    SwipeRefreshLayout favoriteSwipeRefreshLayout;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    CurrentPartnersDB currentPartnersDB;
    Context context;
    View view;
    TextView defaultTextView;

    private final List<User> userList = new ArrayList<>();
    private final List<FavoriteViewData> favoriteViewDataList = new ArrayList<>();
    private FavoriteItemRecyclerAdapter favoriteItemRecyclerAdapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public FavoriteFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorite, container, false);
        //startBackgroundService();

        instantiateViews(view);
        return view;
    }

    public View instantiateViews(View view){
        defaultTextView = (TextView) view.findViewById(R.id.favoriteDefaultTextView);
        favoriteRecyclerView = (RecyclerView) view.findViewById(R.id.favoriteRecyclerview);
        userInformationList = new ArrayList<>();

        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        favoriteRecyclerView.setHasFixedSize(true);
        favoriteItemRecyclerAdapter = new FavoriteItemRecyclerAdapter(context, favoriteViewDataList);
        favoriteRecyclerView.setAdapter(favoriteItemRecyclerAdapter);

        favoriteSwipeRefreshLayout = (SwipeRefreshLayout)  view.findViewById(
                R.id.favoriteSwipeRefreshLayout);
        favoriteSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFavoriteList();
                favoriteSwipeRefreshLayout.setRefreshing(false);
            }
        });

        getFavoriteList();

        return view;
    }

    //Get the list of users and apply it to the partners recyclerview
    public void getFavoriteList(){
        //Collect data all the User IDs(Doc ID) from the User collection
        userList.clear();
        favoriteViewDataList.clear();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                currentPartnersDB = new CurrentPartnersDB(context);

                Users users = null;
                Profiles profiles = null;
                int timeout = 0;

                do{
                    users = currentPartnersDB.getFavoritesUserDataFromSQLite();
                    profiles = currentPartnersDB.getFavoritesProfileFromSQLite();
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        Log.w(TAG, "run: ", e);
                    }
                    timeout++;
                }while((users == (null) || profiles == (null)) && !(timeout >= 3));

                if(users.size() > 0 && profiles.size() > 0) {
                    for (int i = 0; i < users.size(); i++) {
                        User user = users.get(i);
                        Profile profile = profiles.get(i);
                        user.setAvatar(profile.getProfileAvatarUrl());
                        userList.add(user);
                    }

                    for (int j = 0; j < userList.size(); j++) {
                        User user = userList.get(j);
                        FavoriteViewData favoriteViewData = new FavoriteViewData();
                        String full_name = user.getFirst_name() +
                                " " + user.getLast_name();
                        favoriteViewData.setFavoriteName(full_name);
                        favoriteViewData.setFavoriteProfilePicUrl(user.getAvatar());
                        favoriteViewData.setFavoriteId(user.getUid());

                        favoriteViewDataList.add(favoriteViewData);

                        favoriteItemRecyclerAdapter.notifyDataSetChanged();

                        defaultTextView.setVisibility(View.GONE);
                        favoriteRecyclerView.setVisibility(View.VISIBLE);
                    }
                }else if (favoriteViewDataList.size() == 0){
                    defaultTextView.setVisibility(View.VISIBLE);
                    favoriteRecyclerView.setVisibility(View.GONE);
                }
            }
        }, 400);
    }

    public void refreshFavoriteList(){
        favoriteSwipeRefreshLayout.setColorSchemeResources(R.color.colorLoadRefresh);
        //Collect data all the User IDs(Doc ID) from the User collection
        userList.clear();
        favoriteViewDataList.clear();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                currentPartnersDB = new CurrentPartnersDB(context);

                Users users = null;
                Profiles profiles = null;
                int timeout = 0;

                do {
                    users = currentPartnersDB.getFavoritesUserDataFromSQLite();
                    profiles = currentPartnersDB.getFavoritesProfileFromSQLite();
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        Log.w(TAG, "run: ", e);
                    }
                    timeout++;
                } while ((users == (null) || profiles == (null)) && !(timeout >= 3));

                if (users != (null) && profiles != (null)) {
                    for (int i = 0; i < users.size(); i++) {
                        User user = users.get(i);
                        Profile profile = profiles.get(i);
                        user.setAvatar(profile.getProfileAvatarUrl());
                        userList.add(user);
                    }


                    for (int j = 0; j < userList.size(); j++) {
                        User user = userList.get(j);
                        FavoriteViewData favoriteViewData = new FavoriteViewData();
                        String full_name = user.getFirst_name() +
                                " " + user.getLast_name();
                        favoriteViewData.setFavoriteName(full_name);
                        favoriteViewData.setFavoriteProfilePicUrl(user.getAvatar());
                        favoriteViewData.setFavoriteId(user.getUid());

                        favoriteViewDataList.add(favoriteViewData);

                        favoriteItemRecyclerAdapter.notifyDataSetChanged();
                    }
                    favoriteSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 400);
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
