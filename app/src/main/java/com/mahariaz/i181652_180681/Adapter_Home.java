package com.mahariaz.i181652_180681;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_Home extends RecyclerView.Adapter<Adapter_Home.ViewHolder> {
    private List<ModelClass> userList;
    public Adapter_Home(List<ModelClass>userlist){
        this.userList=userlist;
    }

    @NonNull
    @Override
    public Adapter_Home.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Home.ViewHolder holder, int position) {
        int resource=userList.get(position).getDp();
        String name=userList.get(position).getPerson_name();
        String msg=userList.get(position).getLast_chat();
        String time=userList.get(position).getTime();
        holder.setData(resource,name,msg,time);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), MessageActivity.class);
            i.putExtra("recipientName",holder.person_name.getText().toString());
            i.putExtra("recipientStatus","online");
            holder.itemView.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView dp;
        private TextView person_name;
        private TextView time;
        private TextView last_chat;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dp=itemView.findViewById(R.id.dp);
            person_name=itemView.findViewById(R.id.person_name);
            time=itemView.findViewById(R.id.time);
            last_chat=itemView.findViewById(R.id.last_chat);
        }

        public void setData(int resource, String name, String msg, String times) {
            dp.setImageResource(resource);
            person_name.setText(name);
            last_chat.setText(msg);
            time.setText(times);
        }
    }
}
