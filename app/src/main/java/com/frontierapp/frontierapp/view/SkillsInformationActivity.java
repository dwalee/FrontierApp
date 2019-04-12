package com.frontierapp.frontierapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.frontierapp.frontierapp.R;

import com.frontierapp.frontierapp.adapter.SkillsRecyclerViewAdapter;

import java.util.ArrayList;

public class SkillsInformationActivity extends AppCompatActivity {
    private static final String TAG = "SkillsInformationActivi";

    private ArrayList<String> skillNames = new ArrayList<>();
    private ArrayList<String> addSkillsButton = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initRecyclerView();
    }

    public void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.skillsRecyclerView);
        SkillsRecyclerViewAdapter skillsRecyclerViewAdapter = new SkillsRecyclerViewAdapter(this, skillNames,addSkillsButton);
        recyclerView.setAdapter(skillsRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}




