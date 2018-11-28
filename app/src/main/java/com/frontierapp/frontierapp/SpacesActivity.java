package com.frontierapp.frontierapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SpacesActivity extends AppCompatActivity implements SpaceFirestoreConstants, NavigationView.OnNavigationItemSelectedListener  {
    Toolbar spacesToolbar;
    SwipeRefreshLayout spacesSwipeRefreshLayout;
    FloatingActionButton addSpaceFloatingActionButton;
    RecyclerView spacesRecyclerview;
    SpacesItemRecyclerAdapter spacesItemRecyclerAdapter;
    List<SpacesViewData> spacesViewDataList = new ArrayList<>();
    Context context = this;
    UserFirestore userFirestore = new UserFirestore();
    String currentUserId = userFirestore.getCurrentUserId();
    SpaceFirestore spaceFirestore = new SpaceFirestore(context);

    private static final String TAG = "SpacesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spaces);

        instantiateViews();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getSpaces();
    }

    public void instantiateViews(){
        //navigationView.setNavigationItemSelectedListener(this);

        spacesToolbar = (Toolbar) findViewById(R.id.spacesToolbar);
        spacesToolbar.setTitle("Spaces");

        setSupportActionBar(spacesToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        spacesSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.spacesSwipeRefreshLayout);

        spacesRecyclerview = (RecyclerView) findViewById(R.id.spacesRecyclerView);
        spacesRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        spacesRecyclerview.setHasFixedSize(true);

        spacesItemRecyclerAdapter = new SpacesItemRecyclerAdapter(spacesViewDataList,
                this);

        spacesRecyclerview.setAdapter(spacesItemRecyclerAdapter);

        addSpaceFloatingActionButton = (FloatingActionButton) findViewById(R.id.addSpaceFloatingActionButton);
        addSpaceFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createSpaceIntent = new Intent(v.getContext(), CreateSpaceActivity.class);
                startActivity(createSpaceIntent);
            }
        });
    }

    public void getSpaces(){
        spacesViewDataList.clear();
        Log.i(TAG, "getSpaces: currentUserId = " + currentUserId);
        userFirestore.getUserData(currentUserId).collection("Spaces").get()
        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                final List<Task<DocumentSnapshot>> allTasks = new ArrayList<>();
                final List<String> ids = new ArrayList<>();
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                    String id = documentSnapshot.getId();
                    Log.i(TAG, "onSuccess: id = " + id);

                    ids.add(id);
                }

                allTasks.addAll(spaceFirestore.getSpaces(ids));

                Task<List<DocumentSnapshot>> task = Tasks.whenAllSuccess(allTasks);
                task.addOnSuccessListener(new OnSuccessListener<List<DocumentSnapshot>>() {
                    @Override
                    public void onSuccess(List<DocumentSnapshot> documentSnapshots) {
                        for(DocumentSnapshot documentSnapshot : documentSnapshots){
                            SpacesViewData spacesViewData = new SpacesViewData();
                            String name = documentSnapshot.getString(NAME);
                            String purpose = documentSnapshot.getString(PURPOSE);
                            Log.i(TAG, "onSuccess: name = " + name);

                            spacesViewData.setSpace_name(name);
                            spacesViewData.setSpace_purpose(purpose);

                            spacesViewDataList.add(spacesViewData);
                            spacesItemRecyclerAdapter.notifyDataSetChanged();

                        }
                    }
                });
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
