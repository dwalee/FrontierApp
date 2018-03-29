package com.frontierapp.frontierapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import java.util.List;


public class Partners extends Fragment {
    View view;
    FloatingActionButton post;
    AlertDialog.Builder builder;
    RecyclerView partnerRecyclerView;
    DatabaseReference databaseReferenceUser;
    List<UserInformation> userInformationList = new ArrayList<>();

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
                return view;
            }

    @Override
    public void onStart() {
        super.onStart();

        getDatabaseReferenceUsers();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        partnerRecyclerView = (RecyclerView) view.findViewById(R.id.partnerRecyclerView);
        databaseReferenceUser = FirebaseDatabase.getInstance().
                getReference("UserInformation/user");

        List<PartnershipViewData> partnershipViewDataList = new ArrayList<>();
        for(int i=0;i<userInformationList.size();i++){
            PartnershipViewData partnershipViewData = new PartnershipViewData();
            Log.i("onActivityCreated: ", userInformationList.get(i).getName());
            partnershipViewData.setPartnerName(userInformationList.get(i).getName());

            partnershipViewDataList.add(partnershipViewData);
        }


        partnerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        partnerRecyclerView.setHasFixedSize(true);
        PartnerItemRecyclerAdapter partnerItemRecyclerAdapter = new
                PartnerItemRecyclerAdapter(partnershipViewDataList);
        partnerRecyclerView.setAdapter(partnerItemRecyclerAdapter);
    }

    public void getDatabaseReferenceUsers(){
        databaseReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInformationList.clear();
                for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){
                    UserInformation userInformation = userSnapShot.
                            getValue(UserInformation.class);
                    Log.i("onDataChange: ",userInformation.getName());
                    userInformationList.add(userInformation);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    String[] username = {
            "Dwaine Lee Jr",
            "Yoshua Isreal",
            "Denzel Something",
            "Levonte Something Else",
            "Other People",
            "Life in the D",
            "Fuck I ain't got no limits"
    };
}

