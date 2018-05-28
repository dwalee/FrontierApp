package com.frontierapp.frontierapp;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ProfileActivity extends AppCompatActivity {
    CollapsingToolbarLayout profileCollapsingToolbar;
    Toolbar profileToolbar;
    ImageView profileBackgroundImageView, profilePicCircleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.profileCollapsingToolbar);


        profileBackgroundImageView = (ImageView) findViewById(R.id.profileBackgroundImageView);
        Glide.with(this)
                .load("https://vignette.wikia.nocookie.net/dragonball/images/c/c2/" +
                        "Gizard_Wasteland_DBZ_Ep_33_003.png/revision/latest?cb=20170827060816")
                .into(profileBackgroundImageView);

        profilePicCircleImageView = (ImageView) findViewById(R.id.profilePicCircleImageView);
        Glide.with(this)
                .load("https://pbs.twimg.com/media/DXVX493U8AAvqLf.jpg")
                .apply(RequestOptions.circleCropTransform())
                .into(profilePicCircleImageView);

        profileToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("Yoshua Isreal");
        setSupportActionBar(profileToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(profilePicCircleImageView.getDrawable());

    }
}
