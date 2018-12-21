/**
 * <h1>Current Partner Activity</h1>
 * The CurrentPartnerActivity class loads a list of the user's current partners
 *
 * @author Yoshua Isreal
 * @version 1.0
 * @since 2018-07-01
 */

package com.frontierapp.frontierapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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

import com.frontierapp.frontierapp.databinding.ChatItemLayoutBinding;
import com.frontierapp.frontierapp.databinding.FragmentChatsBinding;
import com.frontierapp.frontierapp.model.Chat;
import com.frontierapp.frontierapp.model.Chats;
import com.frontierapp.frontierapp.model.Message;
import com.frontierapp.frontierapp.model.Profiles;
import com.frontierapp.frontierapp.viewmodel.ChatsViewModel;


public class ChatsFragment extends Fragment {
    private static final String TAG = "ChatsFragment";
    private FragmentChatsBinding chatsBinding;
    private RecyclerView recyclerView;
    private ChatsRecyclerViewAdapter adapter;
    private ChatsViewModel chatsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        chatsBinding = FragmentChatsBinding.inflate(inflater, container, false);
        init();
        return chatsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chatsViewModel = ViewModelProviders.of(getActivity()).get(ChatsViewModel.class);
        chatsViewModel.retrieveChats();
        chatsViewModel.getChats().observe(getViewLifecycleOwner(), new Observer<Chats>() {
            @Override
            public void onChanged(@Nullable Chats chats) {
                for (Chat chat:chats){
                    for (int i = 0; i < chats.size(); i++) {
                        Log.d(TAG, "onChanged() called wit: chat = [" + chat.getProfiles().get(i).getFirst_name() + "]");
                    }

                }
                adapter.submitList(chats);
            }
        });
    }

    public void init() {
        recyclerView = chatsBinding.chatListRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new ChatsRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

    }

    private static final DiffUtil.ItemCallback<Chat> DIFF_CALLBACK = new DiffUtil.ItemCallback<Chat>() {
        @Override
        public boolean areItemsTheSame(Chat oldItem, Chat newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(Chat oldItem, Chat newItem) {
            String old_first_name = oldItem.getProfiles().get(0).getFirst_name().toString();
            String new_first_name = newItem.getProfiles().get(0).getFirst_name().toString();

            Boolean b = new_first_name.equals(old_first_name);
            Log.i(TAG, "areContentsTheSame: " + b + " new_first_name = " + new_first_name +
                    " old_first_name = " + old_first_name);

            String oldMessage = oldItem.getMessage().getMessage();
            String newMessage = newItem.getMessage().getMessage();

            Log.d(TAG, "areContentsTheSame() called with: oldItem = ["
                    + oldItem.getMessage().getMessage() + "], newItem = ["
                    + newItem.getMessage().getMessage() + "]");

            return b && oldMessage.equals(newMessage);
        }
    };

    public class ChatsRecyclerViewAdapter extends ListAdapter<Chat, ChatsViewHolder> {
        private static final String TAG = "ChatsRecyclerViewAdapte";
        private ChatItemLayoutBinding chatItemLayoutBinding;

        public ChatsRecyclerViewAdapter() {
            super(DIFF_CALLBACK);
        }

        @NonNull
        @Override
        public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            chatItemLayoutBinding = ChatItemLayoutBinding.inflate(getLayoutInflater(), parent, false);
            return new ChatsViewHolder(chatItemLayoutBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {
            ChatItemLayoutBinding binding = DataBindingUtil.getBinding(holder.itemView);
            Profiles profiles = getItem(position).getProfiles();
            Message message = getItem(position).getMessage();

            binding.setChat(getItem(position));
            binding.setMessage(message);
            binding.setProfile(profiles.get(0));

            binding.setListener(new ChatOnClickListener(binding));
        }

    }

    public class ChatsViewHolder extends RecyclerView.ViewHolder {

        public ChatsViewHolder(View itemView) {
            super(itemView);
        }

    }

    public class ChatOnClickListener implements View.OnClickListener {
        ChatItemLayoutBinding binding;
        Context context;

        public ChatOnClickListener(ChatItemLayoutBinding binding) {
            this.binding = binding;
            context = binding.getRoot().getContext();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("PATH", binding.getChat().getChat_ref().getPath());
            startActivity(intent);

        }
    }
}
