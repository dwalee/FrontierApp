package com.frontierapp.frontierapp.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.Image;
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

import com.frontierapp.frontierapp.adapter.SlideShowAdapter;
import com.frontierapp.frontierapp.databinding.FragmentFeedBinding;
import com.frontierapp.frontierapp.databinding.SpaceInfoLayoutBinding;
import com.frontierapp.frontierapp.databinding.SpacePostItemLayoutBinding;
import com.frontierapp.frontierapp.model.Feed;
import com.frontierapp.frontierapp.model.Post;
import com.frontierapp.frontierapp.model.Space;
import com.frontierapp.frontierapp.viewmodel.FeedViewModel;
import com.frontierapp.frontierapp.viewmodel.SpaceViewModel;

import java.util.ArrayList;


public class FeedFragment extends Fragment {
    private static final String TAG = "FeedFragment";
    private FragmentFeedBinding feedBinding;
    private SpaceInfoLayoutBinding spaceInfoBinding;
    private RecyclerView recyclerView;
    private FeedRecyclerViewAdapter adapter;
    private FeedViewModel viewModel;
    private SpaceViewModel spaceViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        feedBinding = FragmentFeedBinding.inflate(inflater, container, false);
        spaceInfoBinding = feedBinding.spaceInfoLayoutId;
        init();

        return feedBinding.getRoot();
    }

    private void init() {
        adapter = new FeedRecyclerViewAdapter();
        recyclerView = feedBinding.feedRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(FeedViewModel.class);
        spaceViewModel = ViewModelProviders.of(getActivity()).get(SpaceViewModel.class);

        viewModel.getFeed().observe(getViewLifecycleOwner(), new Observer<Feed>() {
            @Override
            public void onChanged(@Nullable Feed posts) {
                adapter.submitList(posts);
            }
        });

        spaceViewModel.getSpace().observe(getViewLifecycleOwner(), new Observer<Space>() {
            @Override
            public void onChanged(@Nullable Space space) {
                spaceInfoBinding.setSpace(space);
            }
        });
    }

    DiffUtil.ItemCallback<Post> DIFF_CALLBACK = new DiffUtil.ItemCallback<Post>() {
        @Override
        public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
            return oldItem.sameContent(newItem);
        }
    };

    private class FeedRecyclerViewAdapter extends ListAdapter<Post, PostViewHolder> {

        protected FeedRecyclerViewAdapter() {
            super(DIFF_CALLBACK);
        }

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            SpacePostItemLayoutBinding postItemLayoutBinding =
                    SpacePostItemLayoutBinding.inflate(getLayoutInflater(), viewGroup, false);

            return new PostViewHolder(postItemLayoutBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
            SpacePostItemLayoutBinding binding = DataBindingUtil.getBinding(postViewHolder.itemView);
            binding.setPost(getItem(i));
            binding.setProfile(getItem(i).getProfile());
            binding.mediaViewPager.setAdapter(new SlideShowAdapter(getContext(), getItem(i).getImage_urls()));
            binding.mediaCircleIndicator.setViewPager(binding.mediaViewPager);
            binding.setListener(new ClickListener(binding));
        }
    }

    private class PostViewHolder extends RecyclerView.ViewHolder {

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class ClickListener implements View.OnClickListener {
        private static final String TAG = "ClickListener";
        SpacePostItemLayoutBinding binding;

        public ClickListener(SpacePostItemLayoutBinding binding) {
            this.binding = binding;
        }

        @Override
        public void onClick(View view) {
            Intent imageViewIntent = new Intent(getActivity(), ImageViewerActivity.class);
            SlideShowAdapter slideShowAdapter = (SlideShowAdapter) binding.mediaViewPager.getAdapter();
            Log.i(TAG, "onClick: " + view + " " + view.toString() + " " + view.getId());

            if(view.getId() == binding.profileImageView.getId()) {
                ArrayList<String> stringArrayList = new ArrayList<>();
                stringArrayList.add(binding.getProfile().getProfile_url());
                imageViewIntent.putStringArrayListExtra("URL",  stringArrayList);
                getActivity().startActivity(imageViewIntent);

            }else if(view.getId() == slideShowAdapter.slideshowBinding.postImageView.getId()){
                imageViewIntent.putStringArrayListExtra("URL", (ArrayList<String>) binding.getPost().getImage_urls());
                getActivity().startActivity(imageViewIntent);
            }
        }
    }
}


