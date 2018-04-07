package com.frontierapp.frontierapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    private final List<User> userList = new ArrayList<>();
    private final List<PartnershipViewData> partnershipViewDataList = new ArrayList<>();
    private PartnerItemRecyclerAdapter partnerItemRecyclerAdapter;
    FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();

    public Partners() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_partners, container, false);

        partnerRecyclerView = (RecyclerView) view.findViewById(R.id.partnerRecyclerView);
        userInformationList = new ArrayList<>();

        partnerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        partnerRecyclerView.setHasFixedSize(true);
        partnerItemRecyclerAdapter = new PartnerItemRecyclerAdapter(getContext(),
                                                                        partnershipViewDataList);
        partnerRecyclerView.setAdapter(partnerItemRecyclerAdapter);

        partnerSwipeRefreshLayout = (SwipeRefreshLayout)  view.findViewById(
                R.id.partnerSwipeRefreshLayout);
        partnerSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPartnerList();
                partnerSwipeRefreshLayout.setRefreshing(false);
            }
        });

        databaseReferenceUser = FirebaseDatabase.getInstance().
                getReference("UserInformation/user");
        getPartnerList();
        //getUserDataFromDatabase();
        return view;
    }

    //Get the list of users and apply it to the partners recyclerview
    public void getPartnerList(){
        CollectionReference fireStoreUserList = firebaseFirestore.collection("UserInformation").document("Users")
                .collection("User");
        final List<String> userIdList = new ArrayList<>();
        //Collect data all the User IDs(Doc ID) from the User collection
        fireStoreUserList.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                userList.clear();
                partnershipViewDataList.clear();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        User user = new User();
                        String first_name = document.getString("User.first_name");
                        String last_name = document.getString("User.last_name");
                        String profileUrl = document.getString("userAvatarUrl");

                        user.setFirst_name(first_name);
                        user.setLast_name(last_name);
                        user.setAvatar(profileUrl);
                        userList.add(user);
                    }

                    for(int j=0;j<userList.size();j++){
                        User user = userList.get(j);
                        PartnershipViewData partnershipViewData = new PartnershipViewData();
                        String full_name = user.getFirst_name() +
                                " " + user.getLast_name();
                        partnershipViewData.setPartnerName(full_name);
                        partnershipViewData.setPartnerAvatarUrl(user.getAvatar());

                        partnershipViewDataList.add(partnershipViewData);

                        partnerItemRecyclerAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

