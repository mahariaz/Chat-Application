package com.mahariaz.i181652_180681;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class new_grp_cnt_fragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<contact_model> contactlist;
    Adapter_Contacts contact_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_grp_cnt_fragment, container, false);
        // Inflate the layout for this fragment
        contactlist = new ArrayList<>();




        recyclerView = view.findViewById(R.id.rec_view);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        contact_adapter = new Adapter_Contacts(contactlist);
        recyclerView.setAdapter(contact_adapter);
        contact_adapter.notifyDataSetChanged();


        RelativeLayout rl = view.findViewById(R.id.newContactBtn);
        rl.setOnClickListener(v -> {
            //startActivity(new Intent(view.getContext(), new_contact.class));
        });
        return view;
    }
}