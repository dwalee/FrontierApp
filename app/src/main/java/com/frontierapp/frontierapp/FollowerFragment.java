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
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
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

    Context context;
    View view;
    TextView defaultTextView;

    private FollowerItemRecyclerAdapter followerItemRecyclerAdapter;

    private final UserFirestore userFirestore = new UserFirestore();
    private final DocumentReference userData = userFirestore.getUserData(userFirestore.getCurrentUserId());
    private final CollectionReference userConnectionsDatabase = userData.collection(userFirestore.CONNECTIONS);

    public FollowerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_follower, container, false);
        //startBackgroundService();

        return instantiateViews(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        getFollowerList();
        followerItemRecyclerAdapter.startListening();
        Log.i(TAG, "onStart: ");

    }

    @Override
    public void onStop() {
        super.onStop();
        followerItemRecyclerAdapter.stopListening();
        Log.i(TAG, "onStop: ");
    }

    public View instantiateViews(View view){
        defaultTextView = (TextView) view.findViewById(R.id.followerDefaultTextView);

        followerRecyclerView = (RecyclerView) view.findViewById(R.id.followerRecyclerview);

        followerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        followerRecyclerView.setHasFixedSize(true);

        return view;
    }

    //Get the list of users and apply it to the partners recyclerview
    public void getFollowerList(){

        Query query = userConnectionsDatabase
                .whereEqualTo(userFirestore.FOLLOWER, true)
                .orderBy(userFirestore.CONNECTIONS_FIRST_NAME)
                .orderBy(userFirestore.CONNECTIONS_LAST_NAME);

        FirestoreRecyclerOptions<ConnectionsViewDataModel> options =
                new FirestoreRecyclerOptions.Builder<ConnectionsViewDataModel>()
                        .setQuery(query, ConnectionsViewDataModel.class)
                        .build();

        followerItemRecyclerAdapter = new FollowerItemRecyclerAdapter(options);
        followerRecyclerView.setAdapter(followerItemRecyclerAdapter);

    }
}
