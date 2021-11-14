package com.mahariaz.i181652_180681;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.net.URI;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cam_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cam_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView bv;
    ImageView iv;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public cam_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cam_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static cam_fragment newInstance(String param1, String param2) {
        cam_fragment fragment = new cam_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv = getView().findViewById(R.id.cam_fragment_imageView);
        bv = getView().findViewById(R.id.cam_fragment_button);

        bv.setOnClickListener(v -> {
            takePicture();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.cam_fragment, container, false);
    }

    public void takePicture(){
        Intent imageCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(imageCapture,0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            Bitmap b =  (Bitmap)data.getExtras().get("data");
//            Uri u = (Uri) data.getExtras().getParcelable("uri");
//            Uri uri = data.getData();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] byteArray = stream.toByteArray();
            Intent sendImage = new Intent(requireContext(),SendImage.class);

            sendImage.putExtra("image",byteArray);
            getContext().startActivity(sendImage);
//            iv.setImageBitmap(b);
        }
    }
}