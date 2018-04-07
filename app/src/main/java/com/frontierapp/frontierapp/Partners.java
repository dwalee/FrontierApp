package com.frontierapp.frontierapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class Partners extends Fragment{
    View view;
    FloatingActionButton post;
    AlertDialog.Builder builder;
    RecyclerView partnerRecyclerView;
    DatabaseReference databaseReferenceUser;
    List<UserInformation> userInformationList;
    SwipeRefreshLayout partnerSwipeRefreshLayout;

    public Partners() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_partners, container, false);

        post = (FloatingActionButton) view.findViewById(R.id.post);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Create Post");

                final EditText postContent = new EditText(getActivity());

                builder.setView(postContent);
                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Info","Sending Post");

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
            }
        });

        partnerSwipeRefreshLayout = (SwipeRefreshLayout)  view.findViewById(
                                                            R.id.partnerSwipeRefreshLayout);
        partnerSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserDataFromDatabase();
                partnerSwipeRefreshLayout.setRefreshing(false);
            }
        });

        databaseReferenceUser = FirebaseDatabase.getInstance().
                getReference("UserInformation/user");


                return view;
        }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        partnerRecyclerView = (RecyclerView) view.findViewById(R.id.partnerRecyclerView);
        userInformationList = new ArrayList<>();
        getUserDataFromDatabase();


    }

    public void getUserDataFromDatabase(){
        databaseReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInformationList.clear();
                for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){
                    UserInformation userInformation = userSnapShot.
                            getValue(UserInformation.class);
                    Log.i("onDataChange: ",userInformation.getName());
                    userInformationList.add(userInformation);
                    //Log.i("onDataChange: ",userInformationList.get(i).getName());
                }

                List<PartnershipViewData> partnershipViewDataList = new ArrayList<>();
                Log.i("onActivityCreated: ", Integer.toString(userInformationList.size()));
                for(int i=0;i<userInformationList.size();i++){
                    PartnershipViewData partnershipViewData = new PartnershipViewData();
                    Log.i("onActivityCreated: ", userInformationList.get(i).getName());
                    partnershipViewData.setPartnerName(userInformationList.get(i).getName());

                    partnershipViewDataList.add(partnershipViewData);
                    Collections.sort(partnershipViewDataList, new Comparator<PartnershipViewData>() {
                        @Override
                        public int compare(PartnershipViewData o1, PartnershipViewData o2) {
                            return o1.getPartnerName().compareToIgnoreCase(o2.getPartnerName());
                        }
                    });
                }
                Log.i(TAG, "onActivityCreated: After");
                partnerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                partnerRecyclerView.setHasFixedSize(true);
                PartnerItemRecyclerAdapter partnerItemRecyclerAdapter = new
                        PartnerItemRecyclerAdapter(getContext(), partnershipViewDataList);
                partnerRecyclerView.setAdapter(partnerItemRecyclerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

