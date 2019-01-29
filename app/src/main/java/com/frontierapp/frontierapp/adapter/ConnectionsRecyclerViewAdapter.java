package com.frontierapp.frontierapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frontierapp.frontierapp.databinding.ConnectionsItemLayoutBinding;
import com.frontierapp.frontierapp.listeners.ConnectionsClickListener;
import com.frontierapp.frontierapp.model.Connection;
import com.frontierapp.frontierapp.model.Connections;


public class ConnectionsRecyclerViewAdapter extends ListAdapter<Connection,ConnectionsViewHolder> {
    private static final String TAG = "ConnectionsRecyclerView";
    private ConnectionsItemLayoutBinding connectionsItemLayoutBinding;
    private LayoutInflater layoutInflater;
    private FragmentActivity fragmentActivity;

    public ConnectionsRecyclerViewAdapter(LayoutInflater layoutInflater, FragmentActivity fragmentActivity) {
        super(DIFF_CALLBACK);
        this.layoutInflater = layoutInflater;
        this.fragmentActivity = fragmentActivity;
    }


    private static final DiffUtil.ItemCallback<Connection> DIFF_CALLBACK = new DiffUtil.ItemCallback<Connection>() {
        @Override
        public boolean areItemsTheSame(Connection oldItem, Connection newItem) {
            String oldPath = oldItem.getUser_ref().getPath();
            String newPath = newItem.getUser_ref().getPath();

            return oldPath.equals(newPath);
        }

        @Override
        public boolean areContentsTheSame(Connection oldItem, Connection newItem) {

            return oldItem.isFollower() == newItem.isFollower() &&
                    oldItem.isFavorite() == newItem.isFavorite() &&
                    oldItem.isPartner() == newItem.isPartner() &&
                    oldItem.getProfile().equals(newItem.getProfile());
        }
    };

    @NonNull
    @Override
    public ConnectionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        connectionsItemLayoutBinding =
                ConnectionsItemLayoutBinding.inflate(layoutInflater, parent, false);

        return new ConnectionsViewHolder(connectionsItemLayoutBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectionsViewHolder holder, int position) {
        ConnectionsItemLayoutBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setListener(new ConnectionsClickListener(connectionsItemLayoutBinding, fragmentActivity));
        binding.connectionsRequestButton.setVisibility(View.GONE);
        binding.setProfile(getItem(position).getProfile());
        binding.setConnection(getItem(position));
    }






}
