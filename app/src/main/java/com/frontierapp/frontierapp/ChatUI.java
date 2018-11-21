package com.frontierapp.frontierapp;

import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class ChatUI extends AppCompatActivity {

    private Toolbar ChatToolBar;
    User user;
    private ImageButton sendMessageButton;
    private ImageButton selectImageButton;
    private EditText inputMessageText;
    private String MessageSenderID;
    private String MessageReceiverID;
    private String MessageReceiverName;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CurrentPartnershipViewData profileUserID;
    private TextView usernameTitle;
    private TextView userLastSeen;
    private String message_UID;
    private RecyclerView message_List_user;
    private final List<Messages> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;

    private String message;
    private Long time;
    private Boolean seen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_ui);


        profileUserID = new CurrentPartnershipViewData();

        mAuth = FirebaseAuth.getInstance();

        sendMessageButton = (ImageButton) findViewById(R.id.SendMessageButton);
        selectImageButton = (ImageButton) findViewById(R.id.FindImageButton);
        inputMessageText = (EditText) findViewById(R.id.ChatEditText);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        MessageSenderID = mAuth.getCurrentUser().getUid();
        MessageReceiverID = profileUserID.getCurrentPartnerId();
        MessageReceiverName = profileUserID.getCurrentPartnerName();

        messageAdapter = new MessageAdapter(messagesList);

        linearLayoutManager = new LinearLayoutManager(this);

        message_List_user = (RecyclerView) findViewById(R.id.messages_List_users);

        message_List_user.setHasFixedSize(true);
        message_List_user.setLayoutManager(linearLayoutManager);


        ChatToolBar = (Toolbar) findViewById(R.id.chat_bar_layout);
        setSupportActionBar(ChatToolBar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View action_bar_view = layoutInflater.inflate(R.layout.chat_custom_bar, null);

        actionBar.setCustomView(action_bar_view);

        usernameTitle = (TextView) findViewById(R.id.chatUserName);
        userLastSeen = (TextView) findViewById(R.id.lastSeen);

        usernameTitle.setText(MessageReceiverName);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessage();
                FetchMessages();
            }
        });
        }

    private void FetchMessages() {
// this shitty comment

        firebaseFirestore.collection("UserInformation").document("Chat").collection(MessageSenderID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        Messages messages = (Messages) queryDocumentSnapshots.getDocumentChanges();

                        messagesList.add(messages);

                        messageAdapter.notifyDataSetChanged();


                    }
                });

    }

    ;

    public void sendMessage() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        String MessageText = inputMessageText.getText().toString();

        if(TextUtils.isEmpty(MessageText)) {

            Toast.makeText(ChatUI.this, "Please Enter Message", Toast.LENGTH_LONG).show();
        }
        else{

            String messageSender_ref = "UserInformation/" + "Chat/" + MessageSenderID + "/" + MessageReceiverID;

            String messageReceiver_ref = "UserInformation/" + "Chat/" + MessageReceiverID + "/" + MessageSenderID;


            //String message_Uid = user_message_key.getId();


            ArrayMap<String, Object> newUserChatInfo = new ArrayMap<>();
            newUserChatInfo.put("Message", MessageText);
            newUserChatInfo.put("Time", ServerValue.TIMESTAMP);
            newUserChatInfo.put("Seen", false);

            CollectionReference user_message_key = firebaseFirestore.collection("UserInformation").document("Chat").collection(MessageSenderID);

            //CollectionReference rec_user_message_key = firebaseFirestore.collection("UserInformation").document("Chat")
                    //.collection(MessageReceiverID);

            Map<String, Object> userChat = new HashMap<>();

            userChat.put("Chat Details", newUserChatInfo);
            user_message_key.add(userChat)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(ChatUI.this, "Message Sent"
                                    , Toast.LENGTH_LONG)
                                    .show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChatUI.this, "Message Failed to Send"
                                    , Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

           /* userChat.put("Chat Details", newUserChatInfo);
            rec_user_message_key.add(userChat)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(ChatUI.this, "Message Sent"
                                    , Toast.LENGTH_LONG)
                                    .show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChatUI.this, "Message Failed to Send"
                                    , Toast.LENGTH_LONG)
                                    .show();
                        }
                    });*/

            Map<String, Object> MessageBodyDetails = new HashMap<>();

            MessageBodyDetails.put(messageSender_ref + message_UID, MessageBodyDetails);

            MessageBodyDetails.put(messageReceiver_ref + message_UID, MessageBodyDetails);

            Map<String, Object> MessageBodyInfo = new HashMap<>();


           /* MessageBodyInfo.put("Message Info", MessageBodyDetails);
            firebaseFirestore.collection("UserInformation").document("Chat").update(MessageBodyDetails)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ChatUI.this, "Message Info collected."
                                    , Toast.LENGTH_LONG)
                                    .show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChatUI.this, "Message Info not collected!"
                                    , Toast.LENGTH_LONG)
                                    .show();
                        }
                    });*/
        }
    }

    }
