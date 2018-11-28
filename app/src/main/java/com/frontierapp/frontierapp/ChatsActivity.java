/**
 * <h1>Current Partner Activity</h1>
 * The CurrentPartnerActivity class loads a list of the user's current partners
 *
 * @author Yoshua Isreal
 * @version 1.0
 * @since 2018-07-01
 */

package com.frontierapp.frontierapp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.frontierapp.frontierapp.view.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity {
    private static final String TAG = "CurrentPartnerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_partners);
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }


}
