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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

// this is signup
public class signUp extends AppCompatActivity {
    EditText email_field_signup,password_field_signup,confirm_field;
    String email_signup,password_signup,conf_pass;
    Button signup_button;
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
                if(email_signup.isEmpty()){
                    email_field_signup.setError("Email is required");
                    email_field_signup.requestFocus();
                    return;
                }
                if(password_signup.isEmpty()){
                    password_field_signup.setError("password is required");
                    password_field_signup.requestFocus();
                    return;
                }
                if(conf_pass.isEmpty()){
                    confirm_field.setError("password is required");
                    confirm_field.requestFocus();
                    return;
                }





                mAuth.createUserWithEmailAndPassword(email_signup,password_signup)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){
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








