package com.mahariaz.i181652_180681;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

// this is signup
public class signUp extends AppCompatActivity {
    EditText email_field_signup,password_field_signup,confirm_field;
    String email_signup,password_signup,conf_pass;
    Button signup_button;
    boolean empty_field=true,pass_matches=false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        signup_button=findViewById(R.id.signup_button);



        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email_field_signup = findViewById(R.id.email_s);
                password_field_signup = findViewById(R.id.pass);
                confirm_field = findViewById(R.id.con_pass);
                email_signup = email_field_signup.getText().toString();
                password_signup = password_field_signup.getText().toString();
                conf_pass = confirm_field.getText().toString();
                System.out.println(email_signup);
                System.out.println(password_signup);

                mAuth.createUserWithEmailAndPassword(email_signup,password_signup)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    System.out.println("Uptil here");
                                    User user=new User(email_signup,password_signup);
                                    FirebaseDatabase.getInstance().getReference("User")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(signUp.this,"S",Toast.LENGTH_LONG).show();

                                                //

                                            }
                                        }
                                    });


                                }
                            }
                        });




            }

        });
        TextView login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }
}