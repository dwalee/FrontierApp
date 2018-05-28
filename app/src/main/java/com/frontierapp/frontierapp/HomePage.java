package com.frontierapp.frontierapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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
    private final List<PostItemData> postItemDataList = new ArrayList<>();
    private PostItemRecyclerViewAdapter postItemRecyclerViewAdapter;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String username;
    String firestoreUid;
    Integer userPic;


    public HomePage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page, container, false);

        //Create the recyclerview
        feedRecyclerView = (RecyclerView) view.findViewById(R.id.feedRecyclerView);
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        feedRecyclerView.setHasFixedSize(true);
        postItemRecyclerViewAdapter = new
                PostItemRecyclerViewAdapter(getContext(), postItemDataList);
        feedRecyclerView.setAdapter(postItemRecyclerViewAdapter);

        mstorage = FirebaseStorage.getInstance().getReference();

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

    @Override
    public void onStart() {
        super.onStart();
        getFeed();
    }

    //Get current user feed
    public void getFeed(){
        final DocumentReference userDocRef = firebaseFirestore.collection(
                "UserInformation/Users/User"
        ).document("ibTb31OODgEbTa8M8Bha");

        userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                postItemDataList.clear();
                //if the task is successful, get their first name, last name, and profile pic url
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();

                    String firstName = documentSnapshot.getString("User.first_name");
                    String lastName = documentSnapshot.getString("User.last_name");
                    final String profilePicUrl = documentSnapshot.getString("userAvatarUrl");
                    final String full_name = firstName + " " + lastName;

                    //Point to the Post collection for this user
                    CollectionReference postCollectionRef = userDocRef.collection("Posts");
                    Task<QuerySnapshot> postTask = postCollectionRef.get();

                    postTask.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                //Get all the data needed for each post
                                for (QueryDocumentSnapshot postSnapShot : task.getResult()) {
                                    PostItemData postItemData = new PostItemData();
                                    postItemData.setUserName(full_name);
                                    postItemData.setUserAvatarUrl(profilePicUrl);

                                    postItemData.setPostString(postSnapShot.getString("Post.post_text"));
                                    postItemData.setPostTimeStamp(postSnapShot.getDate("Post.post_timestamp"));
                                    postItemData.setPostPhotoUrl(postSnapShot.getString("Post.post_image_url"));
                                    postItemDataList.add(postItemData);
                                }
                                postItemRecyclerViewAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                }else{
                    Toast.makeText(getContext(), "This doesn't exist: User", Toast.LENGTH_LONG).show();
                }
            }
        });
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
