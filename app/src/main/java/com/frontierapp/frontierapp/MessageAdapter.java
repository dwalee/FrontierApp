package com.frontierapp.frontierapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Messages> userMessagesList;

    public MessageAdapter(List<Messages> userMessagesList){

        this.userMessagesList = userMessagesList;
   }

    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);

        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Messages messages = userMessagesList.get(position);

        holder.messageText.setText(messages.getMessage());
    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

       public TextView messageText;
       public ImageView userProfileImage;

       public MessageViewHolder(View view){
           super(view);

           messageText = (TextView) view.findViewById(R.id.ChatEditText);
           userProfileImage = (ImageView) view.findViewById(R.id.profile_image);
       }
   }
}
