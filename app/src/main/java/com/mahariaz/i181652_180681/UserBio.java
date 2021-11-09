package com.mahariaz.i181652_180681;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserBio extends AppCompatActivity {
    EditText fname_field,lname_field,age_field;
    String fname,lname,age;
    Button create_button;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bio);
        fname_field=findViewById(R.id.fname);
        lname_field=findViewById(R.id.lname);
        age_field=findViewById(R.id.age);
        create_button=findViewById(R.id.create_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = fname_field.getText().toString();
                lname = lname_field.getText().toString();
                age = age_field.getText().toString();
                rootNode= FirebaseDatabase.getInstance();
                reference=rootNode.getReference("UserBio");
                UserBioStorage userBioStorage=new UserBioStorage(Shared.email,fname,lname,age);
                reference.child(Shared.email).setValue(userBioStorage);
                Intent intent=new Intent(UserBio.this,Login.class);
                startActivity(intent);

            }
        });


    }
}