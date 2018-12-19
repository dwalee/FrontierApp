package com.frontierapp.frontierapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.frontierapp.frontierapp.R;
import com.frontierapp.frontierapp.databinding.ActivityProfileBinding;
import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.model.Connection;
import com.frontierapp.frontierapp.model.Profile;
import com.frontierapp.frontierapp.viewmodel.ProfileViewModel;
import com.google.firebase.firestore.DocumentReference;

public class ConnectionsProfileActivity extends AppCompatActivity {
    private static final String TAG = "CurrentConnectionsProfi";
    private ActivityProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private DocumentReference profileDocumentReference;
    private DocumentReference connectionDocumentReference;
    private Toolbar toolbar;
    private boolean isFavorite = false;
    private boolean isPartner = false;
    private Menu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        Intent intent = getIntent();
        profileDocumentReference = Firestore.myFirestore.document(intent.getStringExtra("PATH"));
        connectionDocumentReference = Firestore.userCollection.document(Firestore.currentUserId)
                .collection(Firestore.CONNECTIONS).document(profileDocumentReference.getId());

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.retrieveProfile(profileDocumentReference);
        profileViewModel.retrieveConnection(connectionDocumentReference);

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
    public boolean onCreateOptionsMenu(final Menu menu) {

        getMenuInflater().inflate(R.menu.partner_menu, menu);

        profileViewModel.getConnection().observe(this, new Observer<Connection>() {
            @Override
            public void onChanged(@Nullable Connection connection) {
                isFavorite = connection.isFavorite();
                isPartner = connection.isPartner();

                MenuItem favoriteMenuItem  = menu.findItem(R.id.favoriteMenu);
                if(isFavorite){

                    favoriteMenuItem.setIcon(R.drawable.heart);
                }else{
                    favoriteMenuItem = menu.findItem(R.id.favoriteMenu);
                    favoriteMenuItem.setIcon(R.drawable.heart_outline);
                }

                MenuItem partnerMenuItem = menu.findItem(R.id.addOrRemoveMenu);
                if(isPartner){
                    partnerMenuItem.setIcon(R.drawable.account_check);
                }else{
                    partnerMenuItem.setIcon(R.drawable.account_plus_white);
                }

            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //go back
                finish();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }
}

