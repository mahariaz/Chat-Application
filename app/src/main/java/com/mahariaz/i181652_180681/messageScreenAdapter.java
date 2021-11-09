package com.mahariaz.i181652_180681;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class messageScreenAdapter extends RecyclerView.Adapter {
    ArrayList<MessageModel> list;

    public messageScreenAdapter(ArrayList<MessageModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message, parent, false);
            return new ViewHolder1(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.their_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (list.get(position).their) {
//            My message's data setting so no image here
            ViewHolder me = (ViewHolder) holder;
            me.SetData(list.get(position).message,list.get(position).date);
        } else {
            ViewHolder1 me = (ViewHolder1) holder;
            me.SetData(list.get(position).message,list.get(position).date,list.get(position).image);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).their ? 0 : 1;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView date;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.myMessageOnMessageView);
            date = itemView.findViewById(R.id.myDateOnMessageView);
            image = itemView.findViewById(R.id.myImageOnMessasgeView);
        }

        public void SetData(String message, String date) {
            this.message.setText(message);
            this.date.setText(date);
            // No Image here in my view
        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        TextView message;
        TextView date;
//        ImageView image;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.theirMessageOnMessageView);
            date = itemView.findViewById(R.id.theirDateOnMessageView);
//            image = itemView.findViewById(R.id.theirImageOnMessasgeView);
        }

        public void SetData(String message, String date, String image) {
            this.message.setText(message);
            this.date.setText(date);
            // image will be set later
        }
    }

    public static class MessageModel {
        String message;
        String date;
        String image;
        boolean their;
        String sortingParam;

        public MessageModel(String message, String date, String image, boolean their,String sortingParam) {
            this.message = message;
            this.date = date;
            this.image = image;
            this.their = their;
            this.sortingParam = sortingParam;
        }
    }
}
