package com.frontierapp.frontierapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.frontierapp.frontierapp.databinding.ActivityProfileBinding;
import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.datasource.FirestoreDBReference;
import com.frontierapp.frontierapp.model.Profile;
import com.frontierapp.frontierapp.R;
import com.frontierapp.frontierapp.model.User;
import com.frontierapp.frontierapp.viewmodel.ProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

public class ProfileActivity extends AppCompatActivity{
    private static final String TAG = "ProfileActivity";
    private ActivityProfileBinding profileBinding;
    private ProfileViewModel profileViewModel;
    private DocumentReference profileDocumentReference = FirestoreDBReference.userCollection.document(Firestore.currentUserId);
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.retrieveProfile(profileDocumentReference);
        profileViewModel.getProfile().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(@Nullable Profile profile) {
                profileBinding.setProfile(profile);
            }
        });

        toolbar = profileBinding.profileToolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //go back
                finish();
                break;
            case R.id.editMenu:
                Intent profileEditIntent = new Intent(this, ProfileEditActivity.class);
                startActivity(profileEditIntent);
                break;
            case R.id.partnersMenu:
                Intent connectionsIntent = new Intent(this,
                        ConnectionsActivity.class);
                startActivity(connectionsIntent);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent signout = new Intent(this, LoginActivity.class);
                startActivity(signout);
                finish();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

}
