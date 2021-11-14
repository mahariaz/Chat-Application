package com.mahariaz.i181652_180681;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_Contacts extends RecyclerView.Adapter<Adapter_Contacts.ViewHolder> {
    private List<contact_model> contactlist;
    public Adapter_Contacts(List<contact_model>contactlist){
        this.contactlist=contactlist;
    }

    @NonNull
    @Override
    public Adapter_Contacts.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Contacts.ViewHolder holder, int position) {
        int resource=contactlist.get(position).getDp();
        String name=contactlist.get(position).getPerson_name();
        String phone=contactlist.get(position).getPhone();
        holder.setData(resource,name,phone);
    }

    @Override
    public int getItemCount() {
        return contactlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView dp;
        private TextView person_name;
        private TextView ph_no;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dp=itemView.findViewById(R.id.dp);
            person_name=itemView.findViewById(R.id.person_name);
            ph_no=itemView.findViewById(R.id.ph_no);

        }

        public void setData(int resource, String name,String phone) {
            dp.setImageResource(resource);
            person_name.setText(name);
            ph_no.setText(phone);

        }
    }
}
