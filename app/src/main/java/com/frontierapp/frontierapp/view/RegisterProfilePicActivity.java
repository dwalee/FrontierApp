package com.frontierapp.frontierapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.frontierapp.frontierapp.databinding.ActivityMainappBinding;

public class RegisterProfilePicActivity extends AppCompatActivity {
    private static final String TAG = "RegisterProfilePicActiv";
    private ActivityMainappBinding mainappBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}


