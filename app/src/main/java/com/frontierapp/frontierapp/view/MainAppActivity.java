package com.frontierapp.frontierapp.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.frontierapp.frontierapp.R;
import com.frontierapp.frontierapp.databinding.ActivityMainappBinding;
import com.frontierapp.frontierapp.databinding.NavigationDrawerBinding;
import com.frontierapp.frontierapp.databinding.NavigationHeaderBinding;
import com.frontierapp.frontierapp.datasource.Firestore;

import com.frontierapp.frontierapp.datasource.FirestoreDBReference;
import com.frontierapp.frontierapp.model.Profile;
import com.frontierapp.frontierapp.viewmodel.ProfileViewModel;
import com.google.firebase.firestore.DocumentReference;


public class MainAppActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainAppActivity";
    private ProfileViewModel profileViewModel;
    private NavigationDrawerBinding binding;
    private ActivityMainappBinding mainappBinding;
    private DocumentReference profileDocumentReference;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    public void init(){
        profileDocumentReference =  FirestoreDBReference.userCollection.document(Firestore.currentUserId);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.retrieveProfile(profileDocumentReference);
        binding       = DataBindingUtil.setContentView(this, R.layout.navigation_drawer);
        mainappBinding = binding.mainActivity;

        toolbar = (Toolbar) mainappBinding.toolbar;
        toolbar.setTitle("Home");
        setSupportActionBar((Toolbar) mainappBinding.toolbar);

        drawerLayout = binding.drawerLayout;

        navigationView = binding.navigationView;
        initNavheader(navigationView.getHeaderView(0));

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        initFragment();
    }

    public void initNavheader(View view){

        final NavigationHeaderBinding navigationHeaderBinding = NavigationHeaderBinding.bind(view);
        profileViewModel.getProfile().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(@Nullable Profile profile) {
                navigationHeaderBinding.setProfile(profile);
            }
        });


    }

    public void initFragment(){
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(mainappBinding.mainActivityFrameLayout.getId(), homeFragment, "homeFragment");
        transaction.commit();
    }

    public void replaceFragment(Fragment fragment, String fragmentTag){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(mainappBinding.mainActivityFrameLayout.getId(), fragment, fragmentTag);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.welcome_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.settings) {
            Toast.makeText(getApplicationContext(), "Selected settings menu", Toast.LENGTH_LONG).show();
            Log.i("Information", "Options menu clicked");
        }
        else if (id == R.id.logout){
            //Sign user out
            Firestore.currentFirebaseAuth.signOut();

            Intent signout = new Intent(MainAppActivity.this, LoginActivity.class);
            startActivity(signout);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();

        switch(id){
            case R.id.home:
                replaceFragment(new HomeFragment(), "homeFragment");
                toolbar.setTitle("Home");
                break;
            case R.id.profile:
                Intent profileScreen = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profileScreen);
                break;
            case R.id.navMessages:
                replaceFragment(new ChatsFragment(), "chatsFragment");
                toolbar.setTitle("Chat");
                break;
            case R.id.connections:
                Intent connectsIntent = new Intent(this, ConnectionsActivity.class);
                startActivity(connectsIntent);
                break;
            case R.id.notifications:
                replaceFragment(new NotificationFragment(), "notificationFragment");
                toolbar.setTitle("Notifications");
                break;
            case R.id.spaces:
                replaceFragment(new SpacesFragment(), "spaceFragment");
                toolbar.setTitle("Spaces");
                break;

            case R.id.vbc:

                break;


        }

        drawerLayout.closeDrawer(Gravity.START);
        return true;
    }

}

