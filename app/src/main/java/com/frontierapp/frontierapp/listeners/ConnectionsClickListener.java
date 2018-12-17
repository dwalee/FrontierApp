package com.frontierapp.frontierapp.listeners;

import android.content.Intent;
import android.view.View;

import com.frontierapp.frontierapp.databinding.ConnectionsItemLayoutBinding;
import com.frontierapp.frontierapp.view.ConnectionsProfileActivity;

public class ConnectionsClickListener implements View.OnClickListener {
    ConnectionsItemLayoutBinding binding;
    public ConnectionsClickListener(ConnectionsItemLayoutBinding binding) {
        this.binding = binding;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(binding.getRoot().getContext(), ConnectionsProfileActivity.class);
        intent.putExtra("PATH", binding.getConnection().getUser_ref().getPath());
        intent.putExtra("isFavorite", binding.getConnection().isFavorite());
        intent.putExtra("isPartner", binding.getConnection().isPartner());
        binding.getRoot().getContext().startActivity(intent);
    }
}
