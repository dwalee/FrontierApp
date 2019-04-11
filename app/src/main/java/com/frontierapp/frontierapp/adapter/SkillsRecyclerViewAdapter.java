package com.frontierapp.frontierapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.frontierapp.frontierapp.R;

import java.util.ArrayList;

public class SkillsRecyclerViewAdapter extends RecyclerView.Adapter<SkillsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "SkillsRecyclerViewAdapt";

    ArrayList<String> mSkillsEditText = new ArrayList<>();
    ArrayList<String> mAddSkillsButton = new ArrayList<>();

    Context mContext;

    public SkillsRecyclerViewAdapter(ArrayList<String> mSkillsEditText, ArrayList<String> mAddSkillsButton, Context mContext) {
        this.mSkillsEditText = mSkillsEditText;
        this.mAddSkillsButton = mAddSkillsButton;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.skills_layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "Creating ViewHolder");


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText skillInput;
        ImageView addSkillButton;
        RelativeLayout skillRelativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            skillInput = itemView.findViewById(R.id.skillEditText);
            addSkillButton = itemView.findViewById(R.id.addSkill);
            skillRelativeLayout = itemView.findViewById(R.id.skillRelativeLayout);
        }
    }
}
