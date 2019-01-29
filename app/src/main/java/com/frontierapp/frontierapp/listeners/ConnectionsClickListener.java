package com.frontierapp.frontierapp.listeners;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.frontierapp.frontierapp.databinding.ConnectionsItemLayoutBinding;
import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.view.ConnectionsProfileActivity;
import com.frontierapp.frontierapp.viewmodel.ConnectionViewModel;

public class ConnectionsClickListener implements View.OnClickListener {
    private static final String TAG = "ConnectionsClickListene";
    private ConnectionsItemLayoutBinding binding;
    private ConnectionViewModel connectionViewModel;
    private FragmentActivity context;

    public ConnectionsClickListener(ConnectionsItemLayoutBinding binding, FragmentActivity context) {
        this.context = context;
        this.binding = binding;
    }

    @Override
    public void onClick(View v) {
        String class_name = context.getLocalClassName().substring(5);

        if(v.getId() == binding.followButton.getId()) {
            Toast.makeText(binding.getRoot().getContext(), "Heart pressed!", Toast.LENGTH_SHORT).show();
            connectionViewModel = ViewModelProviders.of(context).get(ConnectionViewModel.class);
        }else {
            Intent intent = new Intent(binding.getRoot().getContext(), ConnectionsProfileActivity.class);
            if(class_name.equals("ConnectionsActivity"))
                intent.putExtra("PATH", binding.getConnection().getUser_ref().getPath());
            else if(class_name.equals("MainAppActivity"))
                intent.putExtra("PATH", binding.getProfile().getUser_ref().getPath());

            binding.getRoot().getContext().startActivity(intent);
        }
    }
}
