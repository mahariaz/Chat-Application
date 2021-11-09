package com.mahariaz.i181652_180681;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageActivity extends AppCompatActivity {
    messageScreenAdapter messageAdapterInstance;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Current user is abubakar2000
        Shared.username = "abubakar2000";

        ArrayList<messageScreenAdapter.MessageModel> messageList = new ArrayList<>();
        setContentView(R.layout.activity_message);
        rootNode = FirebaseDatabase.getInstance();
        EditText myNewMessage = findViewById(R.id.messageField);
        RecyclerView messageRecycler = findViewById(R.id.messages_recycler_view);
        ImageButton sendButton = findViewById(R.id.sendButton);
        LinearLayoutManager linear = new LinearLayoutManager(MessageActivity.this);
        messageAdapterInstance = new messageScreenAdapter(messageList);
        linear.setOrientation(LinearLayoutManager.VERTICAL);
        messageRecycler.setLayoutManager(linear);

//        fetch data from fire store
        FirebaseDatabase dataRetreival = FirebaseDatabase.getInstance();
        DatabaseReference dataReader = dataRetreival.getReference("Chats");
        dataReader.child(Shared.username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("\n\nMessage");
//                Add the messages
                for (DataSnapshot child : snapshot.getChildren()){
                    UsersMessageStorage messageObject  = child.getValue(UsersMessageStorage.class);
                    messageList.add(new messageScreenAdapter.MessageModel(messageObject.message,messageObject.time,"",false));
                }
                messageAdapterInstance = new messageScreenAdapter(messageList);
                messageRecycler.setAdapter(messageAdapterInstance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error reading from database check for internet connection...");
            }
        });


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
            UsersMessageStorage usersMessageStorage = new UsersMessageStorage(UserNameTv.getText().toString(), Shared.email, myMessageAsAString, time);
            // firebase code

            reference = rootNode.getReference("Chats");
            reference.child(Shared.username).child(new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()).toString()).setValue(usersMessageStorage);
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


        messageRecycler.setAdapter(messageAdapterInstance);
    }
}