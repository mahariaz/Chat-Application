package com.mahariaz.i181652_180681;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class MessageActivity extends AppCompatActivity {
    messageScreenAdapter messageAdapterInstance;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        rootNode= FirebaseDatabase.getInstance();
        EditText myNewMessage = findViewById(R.id.messageField);
        RecyclerView messageRecycler = findViewById(R.id.messages_recycler_view);
        ImageButton sendButton = findViewById(R.id.sendButton);


        ArrayList<messageScreenAdapter.MessageModel> messageList = new ArrayList<>();
//        messageList.add(new messageScreenAdapter.MessageModel("I am great", "", "", true));
//        messageList.add(new messageScreenAdapter.MessageModel("I am great", "", "", true));
        TextView UserNameTv = findViewById(R.id.username);
        TextView UserStatusTv = findViewById(R.id.status);
        UserNameTv.setText(getIntent().getStringExtra("recipientName"));
        UserStatusTv.setText(getIntent().getStringExtra("recipientStatus"));
        sendButton.setOnClickListener(v -> {
            // get time
            SimpleDateFormat input = new SimpleDateFormat("HH:mm");
            String time = input.format(new Date());
            // get payload
            String myMessageAsAString = myNewMessage.getText().toString();




            messageList.add(new messageScreenAdapter.MessageModel(myMessageAsAString, time, "", true));
            //class to store users message activity and connect it to firebase
            UsersMessageStorage usersMessageStorage=new UsersMessageStorage(UserNameTv.getText().toString(),Shared.email,myMessageAsAString,time);
            // firebase code

            reference=rootNode.getReference("Chats");
            reference.child("abubakar2000").child("timestamp").setValue(usersMessageStorage);
            //empty mesage filed and show message
            myNewMessage.setText("");
            messageRecycler.scrollTo(0, messageRecycler.getBottom());

            messageAdapterInstance = new messageScreenAdapter(messageList);
            messageRecycler.setAdapter(messageAdapterInstance);




        });


        // finishes the activity
        findViewById(R.id.backButtonMessageScreen).setOnClickListener(view -> finish());

        findViewById(R.id.callButton).setOnClickListener(v -> {
            //Intent i = new Intent(MessageActivity.this, CallerActivity.class);
            //startActivity(i);
        });





        //setting up the recycler view

        LinearLayoutManager linear = new LinearLayoutManager(MessageActivity.this);
        messageAdapterInstance = new messageScreenAdapter(messageList);
        linear.setOrientation(LinearLayoutManager.VERTICAL);
//
        messageRecycler.setAdapter(messageAdapterInstance);
        messageRecycler.setLayoutManager(linear);
    }
}