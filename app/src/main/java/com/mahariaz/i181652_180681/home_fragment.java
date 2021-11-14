package com.mahariaz.i181652_180681;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class home_fragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> userList;
    Adapter_Home adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view= inflater.inflate(R.layout.home_fragment, container, false);
        userList=new ArrayList<>();
        userList.add(new ModelClass(R.drawable.nu,"maha","2:45pm","how are you and I"));
        userList.add(new ModelClass(R.drawable.nu,"abubakar2000","2:45pm","how are you and I"));
        userList.add(new ModelClass(R.drawable.nu,"ana","2:45pm","how are you and I"));
        userList.add(new ModelClass(R.drawable.nu,"ayesha","2:45pm","how are you and I"));
        userList.add(new ModelClass(R.drawable.nu,"aima","2:45pm","how are you and I"));
        userList.add(new ModelClass(R.drawable.nu,"shiza","2:45pm","how are you and I"));

        EditText field = view.findViewById(R.id.REACTIVESEARCHFIELD);

        field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println(field.getText().toString());

                List<ModelClass> filtered = new LinkedList<ModelClass>();

                for (ModelClass it:
                     userList) {
                    if(it.getPerson_name().toLowerCase().contains(field.getText().toString())){
                        filtered.add(it);
                    }
                }

                adapter=new Adapter_Home(filtered);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        recyclerView=view.findViewById(R.id.rec_view);
        layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter_Home(userList);
        recyclerView.setAdapter(adapter);

        List backupList = userList;
        adapter.notifyDataSetChanged();
        return view;
    }
}