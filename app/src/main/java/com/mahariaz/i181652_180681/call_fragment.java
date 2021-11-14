package com.mahariaz.i181652_180681;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class call_fragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    CallLogAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<CallLogAdapter.CallLogModel> list = new ArrayList<>();
        list.add(new CallLogAdapter.CallLogModel("","Ahmed","online",false));
        list.add(new CallLogAdapter.CallLogModel("","Bashir","online",true));
        list.add(new CallLogAdapter.CallLogModel("","Catelina","online",false));
        list.add(new CallLogAdapter.CallLogModel("","Delila","offline",false));
        list.add(new CallLogAdapter.CallLogModel("","Elephant","online",false));
        list.add(new CallLogAdapter.CallLogModel("","Fujitsu","online",false));
        list.add(new CallLogAdapter.CallLogModel("","Guatamala","online",false));
        list.add(new CallLogAdapter.CallLogModel("","Benazir","offline",true));
        list.add(new CallLogAdapter.CallLogModel("","Benjamin","offline",true));
        list.add(new CallLogAdapter.CallLogModel("","Brandom","offline",true));
        list.add(new CallLogAdapter.CallLogModel("","Maverick","offline",true));
        list.add(new CallLogAdapter.CallLogModel("","Zamaan","online",true));

        View view =inflater.inflate(R.layout.call_fragment, container, false);
        recyclerView=view.findViewById(R.id.CallLogRV);
        layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new CallLogAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}