package com.frontierapp.frontierapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddSpaceMembersActivity extends AppCompatActivity implements AddSpaceMemberItemRecyclerAdapter.AddMemberCheckedListener {
    private static final String TAG = "AddSpaceMembersActivity";
    Toolbar addSpaceMemberToolbar;
    RecyclerView addSpaceMemberRecyclerView;
    AddSpaceMemberItemRecyclerAdapter addSpaceMemberItemRecyclerAdapter;
    List<AddSpaceMemberViewData> addSpaceMemberViewDataList = new ArrayList<>();
    //Firestore
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String currentUserId = currentFirebaseUser.getUid();
    UserFirestore userFirestore = new UserFirestore();
    SpaceFirestore spaceFirestore = new SpaceFirestore(this);

    private Menu menu;
    private MenuItem inviteMenu;
    private MenuItem skipMenu;

    private final String GENERATED_SPACE_ID = "generatedSpaceId";
    private final String SPACE_NAME = "spaceName";

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_space_members);

        instantiateViews();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getMembers();
    }

    public void instantiateViews() {
        addSpaceMemberToolbar = (Toolbar) findViewById(R.id.addSpaceMemberToolbar);
        addSpaceMemberToolbar.setTitle("Add Member(s)");

        setSupportActionBar(addSpaceMemberToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        addSpaceMemberRecyclerView = (RecyclerView) findViewById(R.id.addSpaceMemberRecyclerView);
        addSpaceMemberRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addSpaceMemberRecyclerView.setHasFixedSize(true);

        addSpaceMemberItemRecyclerAdapter = new AddSpaceMemberItemRecyclerAdapter(this,
                addSpaceMemberViewDataList);

        addSpaceMemberRecyclerView.setAdapter(addSpaceMemberItemRecyclerAdapter);


    }

    public void getMembers() {
        addSpaceMemberViewDataList.clear();
        userFirestore.getUserConnections(currentUserId)
                .whereEqualTo("partner", "True")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(final QuerySnapshot queryDocumentSnapshots) {
                final List<Task<DocumentSnapshot>> allTasks = new ArrayList<>();
                    for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {

                        String id = queryDocumentSnapshot.getId();

                       allTasks.add(userFirestore.getUserData(id)
                                .get());
                    }

                Task<List<DocumentSnapshot>> task = Tasks.whenAllSuccess(allTasks);
                    task.addOnSuccessListener(new OnSuccessListener<List<DocumentSnapshot>>() {
                        @Override
                        public void onSuccess(List<DocumentSnapshot> documentSnapshots) {
                            int i = 0;
                            for(DocumentSnapshot documentSnapshot : documentSnapshots) {
                                AddSpaceMemberViewData addSpaceMemberViewData = new AddSpaceMemberViewData();
                                String first_name = documentSnapshot.getString("User.first_name");
                                String last_name = documentSnapshot.getString("User.last_name");

                                Log.i(TAG, "onSuccess: first_name = " + first_name);
                                addSpaceMemberViewData.setMember_name(first_name + " " + last_name);
                                addSpaceMemberViewData.setMember_id(documentSnapshot.getId());
                                addSpaceMemberViewData.setMember_url(documentSnapshot.getString(
                                        "Profile.profile_avatar"
                                ));
                                addSpaceMemberViewData.setChecked(false);

                                addSpaceMemberViewDataList.add(addSpaceMemberViewData);

                                Log.i(TAG, "onSuccess: getMemberName = " + addSpaceMemberViewDataList.get(i).getMember_name());
                                i++;
                                addSpaceMemberItemRecyclerAdapter.notifyDataSetChanged();

                            }

                        }
                    });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.add_space_members_menu, this.menu);

        skipMenu = this.menu.getItem(1);
        inviteMenu = this.menu.getItem(0);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.skipAddSpaceMemberMenuItem){
            Intent intent = getIntent();

            Intent spaceIntent = new Intent(context, SpaceActivity.class);
            finish();
            startActivity(spaceIntent);
        }else if(id == R.id.inviteAppSpaceMemberMenuItem){
            Intent intent = getIntent();
            String currentSpaceId = intent.getStringExtra(GENERATED_SPACE_ID);
            String currentSpaceName = intent.getStringExtra(SPACE_NAME);

            spaceFirestore.inviteSpaceMembers(currentSpaceId, addSpaceMemberItemRecyclerAdapter.getMember_ids_selected(), currentSpaceName);

            Intent spaceIntent = new Intent(context, SpaceActivity.class);
            finish();
            startActivity(spaceIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getMemberCount(int size) {
        if(size == 0){
            inviteMenu.setVisible(true);
            skipMenu.setVisible(false);
        }else if(size > 0){
            inviteMenu.setVisible(false);
            skipMenu.setVisible(true);
        }
    }
}
