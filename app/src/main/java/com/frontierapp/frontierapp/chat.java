package com.frontierapp.frontierapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class chat extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private chatInfo chatInformation;


    public void sendChat(View view) {
        EditText message = (EditText) findViewById(R.id.chatEditText);
        String sender = firebaseAuth.getCurrentUser().toString();
        String recipient = firebaseAuth.().toString();


        chatInformation = new chatInfo(recipient, sender,message);
        Map<String, Object> chatInfo = new HashMap<>();
        chatInfo.put("sender", sender);
        chatInfo.put("recipient", chatInformation.getRecipient());
        chatInfo.put("message", message.getText().toString());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


    }
}
