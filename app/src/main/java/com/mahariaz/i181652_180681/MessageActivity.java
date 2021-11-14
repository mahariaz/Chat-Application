package com.mahariaz.i181652_180681;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageActivity extends AppCompatActivity {
    messageScreenAdapter messageAdapterInstance;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ArrayList<messageScreenAdapter.MessageModel> messageList;

    FirebaseAuth auth;
    FirebaseUser firebaseuser;
    SinchClient sinchClient;
    Call call;
    DatabaseReference dbRefForSinch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        Required by sinch


//      This line switches the user
        Shared.username = "abubakar2000";

        auth = FirebaseAuth.getInstance();
        firebaseuser = auth.getCurrentUser();
        dbRefForSinch = FirebaseDatabase.getInstance().getReference().child("Users");
/*
        findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sinchClient = Sinch.getSinchClientBuilder()
                        .context(MessageActivity.this)
                        .userId(firebaseuser.getUid())
                        .applicationKey("")
                        .environmentHost("")
                        .build();
//                starts listening for calls
                sinchClient.startListeningOnActiveConnection();


                sinchClient.getCallClient().addCallClientListener(new CallClientListener() {
                    @Override
                    public void onIncomingCall(CallClient callClient, Call call) {
//                        executes when call is recieved


                    }
                });

                sinchClient.start();

                fetchUsers();
            }



        });
*/


        messageList = new ArrayList<>();
        setContentView(R.layout.activity_message);
        rootNode = FirebaseDatabase.getInstance();
        EditText myNewMessage = findViewById(R.id.messageField);
        RecyclerView messageRecycler = findViewById(R.id.messages_recycler_view);
        ImageButton sendButton = findViewById(R.id.sendButton);
        LinearLayoutManager linear = new LinearLayoutManager(MessageActivity.this);
        messageList = messageSorter(messageList);
        messageAdapterInstance = new messageScreenAdapter(messageList);
        linear.setOrientation(LinearLayoutManager.VERTICAL);
        messageRecycler.setLayoutManager(linear);

//        fetch data from fire store
        FirebaseDatabase dataRetreival = FirebaseDatabase.getInstance();
        DatabaseReference dataReader = dataRetreival.getReference("Chats");
        TextView UserNameTv = findViewById(R.id.username);
        TextView UserStatusTv = findViewById(R.id.status);
        UserNameTv.setText(getIntent().getStringExtra("recipientName"));
        UserStatusTv.setText(getIntent().getStringExtra("recipientStatus"));
//          checking messages that the other people has sent to me.
        dataReader.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user : snapshot.getChildren()) {
                    System.out.println(user.getKey());
//                    traverse each user message
                    DatabaseReference chatReader = dataRetreival.getReference("Chats/" + user.getKey());
                    chatReader.addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot messages : snapshot.getChildren()) {
                                        UsersMessageStorage messageObject2 = messages.getValue(UsersMessageStorage.class);
                                        if (messageObject2.to_phone.equals(Shared.username)) {
                                            messageList.add(new messageScreenAdapter.MessageModel(messageObject2.message, messageObject2.time, "", true,messageObject2.sortingParam));
                                        }

                                    }
                                    messageList = messageSorter(messageList);
                                    messageAdapterInstance = new messageScreenAdapter(messageList);
                                    messageRecycler.setAdapter(messageAdapterInstance);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            }
                    );
                }

                System.out.println("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        Segment reads the messages of mine only. these are the messages that are sent by me only.
        dataReader.child(Shared.username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Add the messages
                for (DataSnapshot child : snapshot.getChildren()) {
                    UsersMessageStorage messageObject = child.getValue(UsersMessageStorage.class);
                    if (messageObject.to_phone.equals(UserNameTv.getText().toString())) {
                        messageList.add(new messageScreenAdapter.MessageModel(messageObject.message, messageObject.time, "", false,messageObject.sortingParam));
                    }
                }
                messageList = messageSorter(messageList);
                messageAdapterInstance = new messageScreenAdapter(messageList);
                messageRecycler.setAdapter(messageAdapterInstance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error reading from database check for internet connection...");
            }
        });


//        messageList.add(new messageScreenAdapter.MessageModel("I am great", "", "", true));

        sendButton.setOnClickListener(v -> {

            // get time
            SimpleDateFormat input = new SimpleDateFormat("HH:mm");
            String time = input.format(new Date());
            // get payload
            String myMessageAsAString = myNewMessage.getText().toString();

            String sortingParam = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString();

            messageList.add(new messageScreenAdapter.MessageModel(myMessageAsAString, time, "", false, sortingParam));
            //class to store users message activity and connect it to firebase
            UsersMessageStorage usersMessageStorage = new UsersMessageStorage(UserNameTv.getText().toString(), Shared.email, myMessageAsAString, time, sortingParam);
            // firebase code

            reference = rootNode.getReference("Chats");
            reference.child(Shared.username).child(sortingParam).setValue(usersMessageStorage);
            //empty message filed and show message
            myNewMessage.setText("");
            messageRecycler.scrollTo(0, messageRecycler.getBottom());

            messageList = messageSorter(messageList);
            messageAdapterInstance = new messageScreenAdapter(messageList);
            messageRecycler.setAdapter(messageAdapterInstance);




        });


        // finishes the activity
        findViewById(R.id.backButtonMessageScreen).setOnClickListener(view -> finish());

        findViewById(R.id.callButton).setOnClickListener(v -> {
//            TODO add the call screen
//            Intent i = new Intent(MessageActivity.this, CallerActivity.class);
//            startActivity(i);
        });

        messageRecycler.setAdapter(messageAdapterInstance);
    }

    private void fetchUsers() {
//        fetch users to see whom to call
        dbRefForSinch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private class SinchCallListener implements CallListener {

        @Override
        public void onCallProgressing(Call call) {
            Toast.makeText(MessageActivity.this, "Call is progressing...", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCallEstablished(Call call) {
            Toast.makeText(MessageActivity.this, "Call Connected", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCallEnded(Call call) {
            Toast.makeText(MessageActivity.this, "Call Ended", Toast.LENGTH_LONG).show();
//            hangs up the call
            call.hangup();
        }
    }

    public ArrayList<messageScreenAdapter.MessageModel> messageSorter(ArrayList<messageScreenAdapter.MessageModel> messageList) {
        for (int i = 0; i < messageList.size(); i++) {
            for (int j = i; j < messageList.size(); j++) {
                if (Long.parseLong(messageList.get(i).sortingParam) > Long.parseLong(messageList.get(j).sortingParam)) {
                    messageScreenAdapter.MessageModel temp = messageList.get(i);
                    messageList.set(i, messageList.get(j));
                    messageList.set(j, temp);
                }
            }
        }
        return messageList;
    }
}