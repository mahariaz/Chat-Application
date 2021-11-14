package com.mahariaz.i181652_180681;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    messageScreenAdapter messageAdapterInstance;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ArrayList<messageScreenAdapter.MessageModel> messageList;
    private View main;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      This line switches the user
        Shared.username = "abubakar2000";

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
            //Intent i = new Intent(MessageActivity.this, CallerActivity.class);
            //startActivity(i);
        });

        messageRecycler.setAdapter(messageAdapterInstance);
        ///// calling screenshot function
        ImageButton ss=findViewById(R.id.ss);
        main=findViewById(R.id.main);
        imageView=findViewById(R.id.imageView);
        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap b = Screenshot.takescreenshotOfRootView(imageView);
                imageView.setImageBitmap(b);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(null);
                    }
                }, 2000);

                //send notification
                String title="ScreenShot";
                String body="Your screenshot of chat is taken";
                String subject="ScreenShot Taken!";

                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification.Builder
                        (getApplicationContext()).
                        setContentTitle(title).
                        setContentText(body).
                        setContentTitle(subject).
                        setSmallIcon(R.drawable.notif_icon).build();

                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(0, notify);


            }
        });



    }


    public ArrayList<messageScreenAdapter.MessageModel> messageSorter(ArrayList<messageScreenAdapter.MessageModel> messageList){
        for (int i = 0; i < messageList.size(); i++) {
            for (int j = i; j < messageList.size(); j++) {
                if (Long.parseLong(messageList.get(i).sortingParam) > Long.parseLong(messageList.get(j).sortingParam)) {
                    messageScreenAdapter.MessageModel temp = messageList.get(i);
                    messageList.set(i,messageList.get(j));
                    messageList.set(j,temp);
                }
            }
        }
        return messageList;
    }


}