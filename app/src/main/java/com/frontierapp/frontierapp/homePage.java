package com.frontierapp.frontierapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class homePage extends Fragment {
    ListView feedListView;
    ViewPager viewPager;
    View view;


    public homePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page2, container, false);

        feedListView = (ListView) view.findViewById(R.id.feedListView);

        return view;
    }


}
