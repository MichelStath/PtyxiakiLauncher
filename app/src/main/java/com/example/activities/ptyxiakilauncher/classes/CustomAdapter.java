package com.example.activities.ptyxiakilauncher.classes;

import android.content.Context;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activities.ptyxiakilauncher.R;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private final List<Models.Contact> contacts;

   public CustomAdapter(Context context, ArrayList<Models.Contact> contacts){
        this.context = context;
        this.contacts=contacts;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contact_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.contact_id_txt.setText(String.valueOf(contacts.get(position).getContactID()));
        holder.contact_name_txt.setText(String.valueOf(contacts.get(position).getContactName()));
        holder.contact_phone_txt.setText(String.valueOf(contacts.get(position).getContactNumber()));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView contact_id_txt, contact_name_txt, contact_phone_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contact_id_txt = itemView.findViewById(R.id.contact_id_txt);
            contact_name_txt = itemView.findViewById(R.id.contact_name_txt);
            contact_phone_txt = itemView.findViewById(R.id.contact_phone_txt);
        }
    }
}
