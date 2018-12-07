package com.frontierapp.frontierapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.frontierapp.frontierapp.Firestore;
import com.frontierapp.frontierapp.R;
import com.frontierapp.frontierapp.databinding.ActivityChatBinding;
import com.frontierapp.frontierapp.databinding.MessageItemLayoutBinding;
import com.frontierapp.frontierapp.model.Messages;
import com.frontierapp.frontierapp.viewmodel.MessageViewModel;

public class ChatActivity extends AppCompatActivity {
    private MessageViewModel messageViewModel;
    private Firestore firestore = new Firestore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChatBinding chatBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        messageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        final MessageRecyclerAdapter messageRecyclerAdapter = new MessageRecyclerAdapter();
        chatBinding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatBinding.chatRecyclerView.setAdapter(messageRecyclerAdapter);

        messageViewModel.retrieveMessages();
        messageViewModel.getMessages().observe(this, new Observer<Messages>() {
            @Override
            public void onChanged(@Nullable Messages messages) {

                messageRecyclerAdapter.setMessages(messages);
            }
        });

    }

    public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageViewHolder>{
        private MessageItemLayoutBinding messageItemLayoutBinding;
        private Messages messages;

        @NonNull
        @Override
        public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            messageItemLayoutBinding =
                    MessageItemLayoutBinding.inflate(getLayoutInflater(), parent, false);
            return new MessageViewHolder(messageItemLayoutBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
            Boolean b = messages.get(position).getSender_id() == firestore.getCurrentUserId();
            messageItemLayoutBinding.setB(b);
            messageItemLayoutBinding.setMessage(messages.get(position));
        }

        public void setMessages(Messages messages){
            this.messages = messages;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if(messages == null)
                return 0;

            return messages.size();
        }
    }

    private class MessageViewHolder extends RecyclerView.ViewHolder {
        public MessageViewHolder(View itemView) {
            super(itemView);
        }
    }
}
