package com.frontierapp.frontierapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.frontierapp.frontierapp.R;
import com.frontierapp.frontierapp.databinding.ActivityChatBinding;
import com.frontierapp.frontierapp.databinding.MessageItemLayoutBinding;
import com.frontierapp.frontierapp.datasource.Firestore;
import com.frontierapp.frontierapp.model.Message;
import com.frontierapp.frontierapp.model.Messages;
import com.frontierapp.frontierapp.viewmodel.MessageViewModel;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    private MessageViewModel messageViewModel;
    private Query query;
    private MessageRecyclerAdapter adapter;
    private ActivityChatBinding chatBinding;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        initQuery();
        init();
        messageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        messageViewModel.retrieveMessages(query);
        messageViewModel.getMessages().observe(this, new Observer<Messages>() {
            @Override
            public void onChanged(@Nullable Messages messages) {
                int index = 0;

                for (Message message : messages) {
                    Log.d(TAG, "OnSuccess() called with: messages = [" + message.getMessage() + ' ' + index++ + " ]");
                }

                messages.sort();
                adapter.submitList(messages);
            }
        });

    }

    public void initQuery() {
        Intent intent = getIntent();
        String path = intent.getStringExtra("PATH");
        query = Firestore.myFirestore.document(path).collection("Messages")
                .orderBy("sent");
    }

    public void init() {
        adapter = new MessageRecyclerAdapter();

        recyclerView = chatBinding.chatRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private static final DiffUtil.ItemCallback<Message> DIFF_CALLBACK = new DiffUtil.ItemCallback<Message>() {

        @Override
        public boolean areItemsTheSame(Message oldItem, Message newItem) {

            return newItem.equals(oldItem);
        }

        @Override
        public boolean areContentsTheSame(Message oldItem, Message newItem) {
            String oldItemMessage = oldItem.getMessage();
            String newItemMessage = newItem.getMessage();

            return newItemMessage.equals(oldItemMessage);
        }
    };

    public class MessageRecyclerAdapter extends ListAdapter<Message, MessageViewHolder> {
        private static final String TAG = "MessageRecyclerAdapter";
        private MessageItemLayoutBinding messageItemLayoutBinding;

        public MessageRecyclerAdapter() {
            super(DIFF_CALLBACK);
        }

        @NonNull
        @Override
        public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            messageItemLayoutBinding =
                    MessageItemLayoutBinding.inflate(getLayoutInflater(), parent, false);
            return new MessageViewHolder(messageItemLayoutBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
            MessageItemLayoutBinding binding = DataBindingUtil.getBinding(holder.itemView);

            String myId = Firestore.currentUserId;
            String otherId = getItem(position).getSender().getId();
            Boolean b = myId.equals(otherId);
            binding.setB(b);
            binding.setMessage(getItem(position));
            binding.setProfile(getItem(position).getProfile());
        }

    }

    private class MessageViewHolder extends RecyclerView.ViewHolder {
        public MessageViewHolder(View itemView) {
            super(itemView);
        }
    }
}
