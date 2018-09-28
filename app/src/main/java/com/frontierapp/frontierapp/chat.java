package com.frontierapp.frontierapp;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Handler;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ServerValue;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.firestore.CollectionReference;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.Query;
        import com.google.firebase.firestore.QueryDocumentSnapshot;
        import com.google.firebase.firestore.QuerySnapshot;
        import com.google.firebase.iid.FirebaseInstanceId;


        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class chat extends AppCompatActivity {
    private String mChatUser;

    private DatabaseReference mRootRef;
    String currentUserID;
    private static final String TAG = "CurrentPartnerActivity";
    RecyclerView currentPartnerRecyclerView;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    final Context context = this;
    private Toolbar mChatToolbar;
    private RecyclerView mPartnersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mChatToolbar = (Toolbar) findViewById(R.id.chatToolBar);
        setSupportActionBar(mChatToolbar);
        getSupportActionBar().setTitle("My Messages");
        //SET METHOD TO SHOW back arrow in Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentUserID = currentFirebaseUser.getUid();

        mRootRef = FirebaseDatabase.getInstance().getReference().child("UserInformation").child("Users").child("User")
                .child(currentUserID).child("Connections");

        mPartnersList = (RecyclerView) findViewById(R.id.partnerList);
        mPartnersList.setHasFixedSize(true);
        mPartnersList.setLayoutManager(new LinearLayoutManager(this));



    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<chatInfo, PartnerViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<chatInfo, PartnerViewHolder>(

                chatInfo.class,
                R.layout.chat_single_layout,
                PartnerViewHolder.class,
                mRootRef

        ) {
            @Override
            protected void populateViewHolder(PartnerViewHolder viewHolder, chatInfo model, int i) {

                viewHolder.setUserName(model.getUserName());
            }
        };

        mPartnersList.setAdapter(firebaseRecyclerAdapter);


    }
        public static class PartnerViewHolder extends RecyclerView.ViewHolder{

        View mView;

            public PartnerViewHolder(View itemView) {
                super(itemView);

                mView = itemView;


            }

            public void setUserName(String UserName){

                TextView userNameText = (TextView) mView.findViewById(R.id.userName);
                userNameText.setText(UserName);
            }

        }

}