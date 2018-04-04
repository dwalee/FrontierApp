package com.frontierapp.frontierapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class HomePage extends Fragment {
    RecyclerView feedRecyclerView;
    ViewPager viewPager;
    View view;
    FloatingActionButton post;
    AlertDialog.Builder builder;
    private StorageReference mstorage;
    List<PostItemData> postItemDataList;
    private DocumentReference userDocRef = FirebaseFirestore.getInstance().collection(
            "UserInformation/Users/User"
    ).document("ibTb31OODgEbTa8M8Bha");
    private DocumentReference postDocRef;
    String username;
    Integer userPic;


    public HomePage() {
        // Required empty public constructor
    }
    private PostItemData postItemData;
    public void getFeed(){
        postItemDataList = new ArrayList<>();
        userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                postItemDataList.clear();
                postItemData = new PostItemData();
                if(documentSnapshot.exists()){
                    String firstName = documentSnapshot.getString("User.first_name");
                    String lastName = documentSnapshot.getString("User.last_name");
                    String full_name = firstName + " " + lastName;
                    postItemData.setUserName(full_name);
                    postDocRef = userDocRef.collection("Posts").document("OJ0EnoZ4g9d5Q2BQ98yO");

                    postDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshotPost) {
                            if(documentSnapshotPost.exists()){
                                postItemData.setPostString(documentSnapshotPost.getString("Post.post_text"));
                                postItemDataList.add(postItemData);

                                feedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                feedRecyclerView.setHasFixedSize(true);
                                PostItemRecyclerViewAdapter postItemRecyclerViewAdapter = new
                                        PostItemRecyclerViewAdapter(postItemDataList);
                                feedRecyclerView.setAdapter(postItemRecyclerViewAdapter);

                                postItemData.setPostTimeStamp(documentSnapshotPost.getDate("Post.post_timestamp"));
                            }else{
                                Toast.makeText(getContext(), "This doesn't exist: Post", Toast.LENGTH_LONG).show();
                            }

                        }

                    });



                }else{
                    Toast.makeText(getContext(), "This doesn't exist: User", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        feedRecyclerView = (RecyclerView) view.findViewById(R.id.feedRecyclerView);
        getFeed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page, container, false);

        //mstorage = FirebaseStorage.getInstance().getReference();

        feedRecyclerView = (RecyclerView) view.findViewById(R.id.feedListView);

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
