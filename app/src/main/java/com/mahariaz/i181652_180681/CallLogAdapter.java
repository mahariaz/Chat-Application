package com.mahariaz.i181652_180681;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.ViewHolder> {
    ArrayList<CallLogModel> list;

    public CallLogAdapter(ArrayList<CallLogModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CallLogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_log_entry,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallLogAdapter.ViewHolder holder, int position) {
        holder.SetData(list.get(position).image,list.get(position).name,list.get(position).status,list.get(position).missed);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView usernameImage;
        TextView username;
        TextView status;
        ImageView statusIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.TextViewForUsernameInCallLog);
            usernameImage = itemView.findViewById(R.id.imageViewUserInCallLog);
            status = itemView.findViewById(R.id.statusOfUserInCallLog);
            statusIcon = itemView.findViewById(R.id.statusIcon);
        }
        public void SetData(String userimage,String username,String status,boolean statusIcon){
            this.username.setText(username);
            this.status.setText(status);
            this.statusIcon.setImageResource( statusIcon ? R.drawable.ic_baseline_call_received_24 : R.drawable.ic_baseline_call_made_24 );
        }
    }
    public static class CallLogModel{
        public String image;
        public String name;
        public String status;
        public boolean missed;

        public CallLogModel(String image, String name, String status, boolean missed) {
            this.image = image;
            this.name = name;
            this.status = status;
            this.missed = missed;
        }
    }
}