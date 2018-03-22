package com.frontierapp.frontierapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;


public class homePage extends Fragment {
    ListView feedListView;
    ViewPager viewPager;
    View view;
    FloatingActionButton post;
    AlertDialog.Builder builder;


    public homePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page2, container, false);

        feedListView = (ListView) view.findViewById(R.id.feedListView);

        post = (FloatingActionButton) view.findViewById(R.id.post);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder =  new AlertDialog.Builder(getActivity());
                builder.setTitle("Create Post");


                final EditText postContent= new EditText(getActivity());

                builder.setView(postContent);

                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }

                });
                builder.show();

            }
        });
        return view;
    }


}
