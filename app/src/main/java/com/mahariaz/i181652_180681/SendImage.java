package com.mahariaz.i181652_180681;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class SendImage extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> userList;
    Adapter_Home adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);

        ImageView image = findViewById(R.id.imageCaptured);
//        Uri uri = Uri.parse();
//        System.out.println(getIntent().getStringExtra("bitmap")+"\n\n\n<======");
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        image.setImageBitmap(bmp);

        userList=new ArrayList<>();
        userList.add(new ModelClass(R.drawable.nu,"maha","2:45pm","how are you and I"));
        userList.add(new ModelClass(R.drawable.nu,"ana","2:45pm","how are you and I"));
        userList.add(new ModelClass(R.drawable.nu,"ayesha","2:45pm","how are you and I"));
        userList.add(new ModelClass(R.drawable.nu,"aima","2:45pm","how are you and I"));
        userList.add(new ModelClass(R.drawable.nu,"shiza","2:45pm","how are you and I"));
        recyclerView= findViewById(R.id.sendImgContactsRV);
        layoutManager=new LinearLayoutManager(SendImage.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter_Home(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}