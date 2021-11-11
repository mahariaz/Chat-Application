package com.mahariaz.i181652_180681;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.squareup.picasso.Picasso;

public class ImageUpload extends AppCompatActivity {

    Uri selectedImage = null;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        findViewById(R.id.upload).setOnClickListener(view -> {
            System.out.println("Uploading");

            StorageReference storage = FirebaseStorage.getInstance().getReference();
            storage = storage.child("images/name.jpg");
            storage.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                            task
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String dp = uri.toString();

                                            FirebaseDatabase fstorage = FirebaseDatabase.getInstance();
                                            DatabaseReference DBREF = fstorage.getReference("images");
                                            DBREF.push().setValue(
                                                    dp
                                            );
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
        });
        findViewById(R.id.dp).setOnClickListener(view -> {
            System.out.println("Selecting");
            Intent selecting = new Intent();
            selecting.setAction(Intent.ACTION_GET_CONTENT);
            selecting.setType("image/*");
            startActivityForResult(selecting, 200);
        });
        findViewById(R.id.show_dp).setOnClickListener(view -> {
            System.out.println("setting");

            FirebaseDatabase dowbDB = FirebaseDatabase.getInstance();
            DatabaseReference dbRef = dowbDB.getReference("images");
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds:
                         snapshot.getChildren()) {
                        System.out.println(ds.getValue().toString());
//                        ds.getValue().toString() is url for the image
                        String url_to_str=ds.getValue().toString();
                        ImageView show_dp=findViewById(R.id.show_dp);
                        Glide.with(getApplicationContext()).load(url_to_str).into(show_dp);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("Mujh se baat nai karo abhi");
                }
            });
        });





    } //oncreate close




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            selectedImage = data.getData();
            iv = findViewById(R.id.dp);
            iv.setImageURI(selectedImage);
        }
    }
}