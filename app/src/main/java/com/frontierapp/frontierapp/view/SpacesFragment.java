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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frontierapp.frontierapp.databinding.FragmentSpacesBinding;
import com.frontierapp.frontierapp.databinding.SpacesItemLayoutBinding;
import com.frontierapp.frontierapp.model.Space;
import com.frontierapp.frontierapp.model.Spaces;
import com.frontierapp.frontierapp.viewmodel.SpacesViewModel;


public class SpacesFragment extends Fragment {
    private static final String TAG = "SpacesFragment";
    private FragmentSpacesBinding spacesBinding;
    private SpaceRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private SpacesViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        spacesBinding = FragmentSpacesBinding.inflate(inflater, container, false);
        init();
        viewModel = ViewModelProviders.of(getActivity()).get(SpacesViewModel.class);
        viewModel.retrieveSpaces();
        viewModel.getSpaces().observe(getViewLifecycleOwner(), new Observer<Spaces>() {
            @Override
            public void onChanged(@Nullable Spaces spaces) {
                adapter.submitList(spaces);
            }
        });
        return spacesBinding.getRoot();
    }

    private void init() {
        adapter = new SpaceRecyclerViewAdapter();
        recyclerView = spacesBinding.spacesRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }

    private static final DiffUtil.ItemCallback<Space> DIFF_CALLBACK = new DiffUtil.ItemCallback<Space>() {
        @Override
        public boolean areItemsTheSame(@NonNull Space oldItem, @NonNull Space newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Space oldItem, @NonNull Space newItem) {
            String oldName = oldItem.getName();
            String newName = newItem.getName();

            String oldPurpose = oldItem.getPurpose();
            String newPurpose = newItem.getPurpose();

            int old_number_of_members = oldItem.getNumber_of_members();
            int new_number_of_members = newItem.getNumber_of_members();
            Log.i(TAG, "areContentsTheSame: oldName = "+ oldName + " newName = " + newName + " " + oldName.equals(newName) );
            return oldName.equals(newName) && oldPurpose.equals(newPurpose) &&
                    old_number_of_members == new_number_of_members;
        }
    };

    private class SpaceRecyclerViewAdapter extends ListAdapter<Space, SpaceViewHolder>{
        private static final String TAG = "SpaceRecyclerViewAdapte";
        private SpacesItemLayoutBinding spacesItemLayoutBinding;

        protected SpaceRecyclerViewAdapter() {
            super(DIFF_CALLBACK);
        }

        @NonNull
        @Override
        public SpaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            spacesItemLayoutBinding = SpacesItemLayoutBinding.inflate(getLayoutInflater(), viewGroup, false);
            return new SpaceViewHolder(spacesItemLayoutBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull SpaceViewHolder spaceViewHolder, int i) {
            SpacesItemLayoutBinding binding = DataBindingUtil.getBinding(spaceViewHolder.itemView);
            binding.setListener(new SpaceOnClickListener(binding));
            binding.setSpace(getItem(i));
        }
    }

    private class SpaceViewHolder extends RecyclerView.ViewHolder{

        public SpaceViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class SpaceOnClickListener implements View.OnClickListener{
        SpacesItemLayoutBinding binding;

        public SpaceOnClickListener(SpacesItemLayoutBinding binding) {
            this.binding = binding;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), SpaceActivity.class);
            intent.putExtra("PATH", binding.getSpace().getSpace_ref().getPath());
            startActivity(intent);
        }
    }
}

