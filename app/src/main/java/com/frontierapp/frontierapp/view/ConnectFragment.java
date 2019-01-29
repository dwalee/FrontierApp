package com.frontierapp.frontierapp.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frontierapp.frontierapp.R;
import com.frontierapp.frontierapp.adapter.ConnectionsRecyclerViewAdapter;
import com.frontierapp.frontierapp.databinding.ConnectionsItemLayoutBinding;
import com.frontierapp.frontierapp.databinding.FragmentConnectBinding;
import com.frontierapp.frontierapp.listeners.ConnectionsClickListener;
import com.frontierapp.frontierapp.model.Profile;
import com.frontierapp.frontierapp.model.Profiles;
import com.frontierapp.frontierapp.util.SpacesItemDecoration;
import com.frontierapp.frontierapp.viewmodel.ProfilesViewModel;
import com.google.protobuf.Internal;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectFragment extends Fragment {
    private FragmentConnectBinding connectBinding;
    private ProfilesViewModel viewModel;
    private RecyclerView recyclerView;
    private ConnectRecyclerViewAdpater adpater;

    public ConnectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        connectBinding = FragmentConnectBinding.inflate(inflater, container, false);
        init();
        // Inflate the layout for this fragment
        return connectBinding.getRoot();
    }

    private void init() {
        recyclerView = connectBinding.connectReyclerView;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        adpater = new ConnectRecyclerViewAdpater(getLayoutInflater());
        int spacing = 5;
        boolean includeEdge = false;

        recyclerView.addItemDecoration(new SpacesItemDecoration(2, spacing, includeEdge));
        recyclerView.setAdapter(adpater);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(ProfilesViewModel.class);
        viewModel.retrieveProfiles();
        viewModel.getProfiles().observe(getViewLifecycleOwner(), new Observer<Profiles>() {
            @Override
            public void onChanged(@Nullable Profiles profiles) {
                adpater.submitList(profiles);
            }
        });
    }

    private static final DiffUtil.ItemCallback<Profile> diffCallback = new DiffUtil.ItemCallback<Profile>() {
        @Override
        public boolean areItemsTheSame(@NonNull Profile oldProfile, @NonNull Profile newProfile) {
            return oldProfile.equals(newProfile);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Profile oldProfile, @NonNull Profile newProfile) {
            return oldProfile.sameContent(newProfile);
        }
    };

    public class ConnectRecyclerViewAdpater extends ListAdapter<Profile, ConnectViewHolder>{
        private static final String TAG = "ConnectRecyclerViewAdpa";
        private ConnectionsItemLayoutBinding connectionsItemLayoutBinding;
        private LayoutInflater layoutInflater;

        protected ConnectRecyclerViewAdpater(LayoutInflater layoutInflater) {
            super(diffCallback);
            this.layoutInflater = layoutInflater;
        }

        @NonNull
        @Override
        public ConnectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            connectionsItemLayoutBinding = ConnectionsItemLayoutBinding.inflate(layoutInflater,
                    viewGroup, false);
            return new ConnectViewHolder(connectionsItemLayoutBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull ConnectViewHolder connectViewHolder, int i) {
            ConnectionsItemLayoutBinding binding = DataBindingUtil.getBinding(connectViewHolder.itemView);
            binding.setProfile(getItem(i));
            binding.setListener(new ConnectionsClickListener(binding, getActivity()));
        }
    }

    public class ConnectViewHolder extends RecyclerView.ViewHolder{

        public ConnectViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
