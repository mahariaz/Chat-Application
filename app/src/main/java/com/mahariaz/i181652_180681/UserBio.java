package com.mahariaz.i181652_180681;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.UUID;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserBio extends AppCompatActivity {
    EditText fname_field, lname_field, age_field,tag_field;
    public String fname, lname, age, dp,tag_line;
    Button create_button;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ImageView iv;
    Uri selectedImage = null;

    public static final int CAMERA_ACTION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bio);
        fname_field = findViewById(R.id.fname);
        lname_field = findViewById(R.id.lname);
        age_field = findViewById(R.id.age);
        tag_field = findViewById(R.id.tag_field);


        //register user
        create_button = findViewById(R.id.create_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname = fname_field.getText().toString();
                lname = lname_field.getText().toString();
                age = age_field.getText().toString();
                tag_line = tag_field.getText().toString();
                register_bio();
                Intent intent = new Intent(UserBio.this, Login.class);
                startActivity(intent);

            }
        });
        findViewById(R.id.dp_view).setOnClickListener(view -> {
            System.out.println("Selecting");
            Intent selecting = new Intent();
            selecting.setAction(Intent.ACTION_GET_CONTENT);
            selecting.setType("image/*");
            startActivityForResult(selecting, 200);
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            selectedImage = data.getData();
            iv = findViewById(R.id.dp_view);
            iv.setImageURI(selectedImage);


        }
    }
    public void register_bio(){

        StorageReference storage = FirebaseStorage.getInstance().getReference();
        String uniqueID = UUID.randomUUID().toString();
        storage = storage.child("UserDP/"+uniqueID+".jpg");
        storage.putFile(selectedImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                        task
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        dp = uri.toString();

                                        FirebaseDatabase fstorage = FirebaseDatabase.getInstance();
                                        DatabaseReference DBREF = fstorage.getReference("UserBio");
                                        /*DBREF.push().setValue(
                                                dp
                                        );*/
                                        rootNode = FirebaseDatabase.getInstance();
                                        reference = rootNode.getInstance().getReference("UserBio");
                                        UserBioStorage userBioStorage = new UserBioStorage(dp,Shared.email,Shared.username, fname, lname, age,tag_line);
                                        reference.child(Shared.username).setValue(userBioStorage);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}