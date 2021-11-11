package com.mahariaz.i181652_180681;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button login;
    EditText email_field,password_field;
    String email_login,password_login;
    TextView reg_now;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email_field=findViewById(R.id.email_field);
        password_field=findViewById(R.id.password_field);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            Intent intent=new Intent(Login.this,Home.class);
            startActivity(intent);
        }
        login=findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_login = email_field.getText().toString();
                password_login = password_field.getText().toString();
                System.out.println(email_login);
                if(email_login.isEmpty()){
                    email_field.setError("Email is required");
                    email_field.requestFocus();
                    return;
                }
                if(password_login.isEmpty()){
                    password_field.setError("Password is required");
                    password_field.requestFocus();
                    return;
                }
                Shared.email = email_login;
               mAuth.signInWithEmailAndPassword(email_login,password_login)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    // Login to home

                                    Intent intent=new Intent(Login.this,Home.class);
                                    startActivity(intent);
                                    sp.edit().putBoolean("logged",true).apply();
                                }else{
                                    Toast.makeText(Login.this,"Register First",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


        reg_now=findViewById(R.id.reg_now);
        reg_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,signUp.class);
                startActivity(intent);
            }
        });

    }
}