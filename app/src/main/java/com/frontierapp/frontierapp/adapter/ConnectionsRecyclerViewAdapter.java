package com.frontierapp.frontierapp.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frontierapp.frontierapp.databinding.ConnectionsItemLayoutBinding;
import com.frontierapp.frontierapp.listeners.ConnectionsClickListener;
import com.frontierapp.frontierapp.model.Connections;


public class ConnectionsRecyclerViewAdapter extends RecyclerView.Adapter<ConnectionsViewHolder> {
    private static final String TAG = "ConnectionsRecyclerView";
    private Connections connections;
    private ConnectionsItemLayoutBinding connectionsItemLayoutBinding;
    private LayoutInflater layoutInflater;

    public ConnectionsRecyclerViewAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

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
        binding.setListener(new ConnectionsClickListener(connectionsItemLayoutBinding));
        binding.connectionsRequestButton.setVisibility(View.GONE);
        binding.setProfile(connections.get(position).getProfile());
        binding.setConnection(connections.get(position));
    }

    public void setConnections(Connections connections){
        this.connections = connections;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        if(connections == null)
            return 0;
        return connections.size();
    }
}
