package com.frontierapp.frontierapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerFragment extends Fragment {
    private static final String TAG = "PartnerFragment";
    RecyclerView partnerRecyclerView;

    Context context;
    TextView currentPartnerDefaultTextView;

    private final UserFirestore userFirestore = new UserFirestore();
    private final DocumentReference userData = userFirestore.getUserData(userFirestore.getCurrentUserId());
    private final CollectionReference userConnectionsDatabase = userData.collection(userFirestore.CONNECTIONS);

    private CurrentPartnerItemRecyclerAdapter currentPartnerItemRecyclerAdapter;
    View view;

    public PartnerFragment() {
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
        view = inflater.inflate(R.layout.fragment_partner, container, false);

        instantiateViews(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getCurrentPartnerList();
        currentPartnerItemRecyclerAdapter.startListening();
        Log.i(TAG, "onStart: ");

    }

    @Override
    public void onStop() {
        super.onStop();
        currentPartnerItemRecyclerAdapter.stopListening();
        Log.i(TAG, "onStop: ");
    }

    /**
     * This method is used to instantiate all views
     * in the activity_current_partners xml to variables in this class
     */
    public void instantiateViews(View view){
        currentPartnerDefaultTextView = (TextView) view.findViewById(R.id.currentPartnerDefaultTextView);

        partnerRecyclerView = (RecyclerView) view.findViewById(R.id.partnerRecyclerView);

        partnerRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        partnerRecyclerView.setHasFixedSize(true);
    }

    /**
     * This method is used to get the list of partners from the current_partners table
     * in sqlite and load each item in the recyclerview
     */
    public void getCurrentPartnerList(){

       Query query = userConnectionsDatabase
                .whereEqualTo(userFirestore.PARTNER, "True")
                .orderBy(userFirestore.CONNECTIONS_FIRST_NAME)
                .orderBy(userFirestore.CONNECTIONS_LAST_NAME);

        FirestoreRecyclerOptions<ConnectionsViewDataModel> options =
                new FirestoreRecyclerOptions.Builder<ConnectionsViewDataModel>()
                .setQuery(query, ConnectionsViewDataModel.class)
                .build();

        currentPartnerItemRecyclerAdapter = new CurrentPartnerItemRecyclerAdapter(options);
        partnerRecyclerView.setAdapter(currentPartnerItemRecyclerAdapter);
    }
}
