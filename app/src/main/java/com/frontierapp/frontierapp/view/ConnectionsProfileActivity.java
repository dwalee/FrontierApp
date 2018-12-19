package com.frontierapp.frontierapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.frontierapp.frontierapp.R;
import com.frontierapp.frontierapp.databinding.ActivityProfileBinding;
import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.datasource.FirestoreDBReference;
import com.frontierapp.frontierapp.model.Profile;
import com.frontierapp.frontierapp.viewmodel.ProfileViewModel;
import com.google.firebase.firestore.DocumentReference;

public class ConnectionsProfileActivity extends AppCompatActivity {
    private static final String TAG = "CurrentConnectionsProfi";
    private ActivityProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private DocumentReference profileDocumentReference;
    private Toolbar toolbar;
    private boolean isFavorite = false;
    private boolean isPartner = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        Intent intent = getIntent();
        profileDocumentReference = Firestore.myFirestore.document(intent.getStringExtra("PATH"));
        isFavorite = intent.getBooleanExtra("isFavorite", false);
        isPartner = intent.getBooleanExtra("isPartner", false);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.retrieveProfile(profileDocumentReference);
        profileViewModel.getProfile().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(@Nullable Profile profile) {
                binding.setProfile(profile);
            }
        });

        toolbar = binding.profileToolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.partner_menu,menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //go back
                finish();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }
}

