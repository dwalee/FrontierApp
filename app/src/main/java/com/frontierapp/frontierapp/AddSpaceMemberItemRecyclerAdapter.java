package com.frontierapp.frontierapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class AddSpaceMemberItemRecyclerAdapter extends RecyclerView.Adapter<AddSpaceMemberViewHolder>{
    private static final String TAG = "AddSpaceMemberRecycler";
    View view;
    Context context;
    List<AddSpaceMemberViewData> addSpaceMemberViewDataList;

    private AddMemberCheckedListener listener;

    private List<String> member_ids_selected = new ArrayList<>();

    public AddSpaceMemberItemRecyclerAdapter(Context context, List<AddSpaceMemberViewData> addSpaceMemberViewDataList) {
        this.context = context;
        this.addSpaceMemberViewDataList = addSpaceMemberViewDataList;
    }

    @Override
    public AddSpaceMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_space_member_item,
                parent, false);

        return new AddSpaceMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddSpaceMemberViewHolder holder, int position) {
        final AddSpaceMemberViewData addSpaceMemberViewData = addSpaceMemberViewDataList.get(position);

        Glide.with(view).load(addSpaceMemberViewData.getMember_url())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.addSpaceMemberImageView);

        holder.addSpaceMemberNameTextView.setText(addSpaceMemberViewData.getMember_name());

        holder.addSpaceMemberCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    member_ids_selected.add(addSpaceMemberViewData.getMember_id());
                    int memberSelectedSize = member_ids_selected.size();
                    listener.getMemberCount(memberSelectedSize);
                }else if(!(isChecked)){
                    if(member_ids_selected.contains(addSpaceMemberViewData.getMember_id()))
                        member_ids_selected.remove(addSpaceMemberViewData.getMember_id());

                    int memberSelectedSize = member_ids_selected.size();
                    listener.getMemberCount(memberSelectedSize);
                }
                //Toast.makeText(context, "Check box is " + isChecked, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return addSpaceMemberViewDataList.size();
    }

    public List<String> getMember_ids_selected() {
        return member_ids_selected;
    }

    public interface AddMemberCheckedListener{
        void getMemberCount(int size);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(recyclerView.getContext() instanceof AddMemberCheckedListener){
            listener = (AddMemberCheckedListener) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement AddMemberCheckedListener");
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        listener = null;
    }


}
