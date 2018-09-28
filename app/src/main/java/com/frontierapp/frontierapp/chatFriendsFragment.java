package com.frontierapp.frontierapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class chatFriendsFragment extends Fragment {
    private RecyclerView myPartnerList;
    private DatabaseReference partnerReference;
    private FirebaseAuth mAuth;

    String online_userid;

    private View myMainView;


    public chatFriendsFragment() {
        // Required empty public constructor
    }


}

