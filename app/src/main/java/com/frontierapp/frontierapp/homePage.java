package com.frontierapp.frontierapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;


public class homePage extends Fragment {
    ListView feedListView;
    ViewPager viewPager;
    View view;
    FloatingActionButton post;
    AlertDialog.Builder builder;
    private StorageReference mstorage;
    String username;
    Integer userPic;


    public homePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page2, container, false);

        mstorage = FirebaseStorage.getInstance().getReference();

        feedListView = (ListView) view.findViewById(R.id.feedListView);

        post = (FloatingActionButton) view.findViewById(R.id.post);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Create Post");

                EditText postContent = new EditText(getActivity());

                builder.setView(postContent);

                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //startPosting();
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

            /*private void startPosting(String username, Integer userPic) {

                String postInfo = builder.toString().trim();
                String name = username.toString().trim();

                if (!TextUtils.isEmpty((postInfo))){

                    String key = mstorage.child("posts").push().getKey();
                    Post post = new Post(Uid, username, body);
                    Map<String, Object> postValues = post.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/posts/" + key, postValues);
                    childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

                    mstorage.updateChildren(childUpdates);
                }


                }
            }
        });
        return view;
    }*/
