package com.example.activities.ptyxiakilauncher.classes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activities.ptyxiakilauncher.FastContactsActivity;
import com.example.activities.ptyxiakilauncher.R;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private ContactClickListener contactClickListener;
    private Context context;
    private final List<Models.Contact> contacts;
    int position;
    private Helper.ContactDbHelper dbHelper;

   public CustomAdapter(Context context, ArrayList<Models.Contact> contacts, ContactClickListener listener){
        this.context = context;
        this.contacts=contacts;
        this.contactClickListener = listener;
        this.dbHelper = new Helper.ContactDbHelper(context);
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
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getAdapterPosition() == RecyclerView.NO_POSITION)
                    return;
                // Get the current contact
                Models.Contact currentContact = contacts.get(holder.getAdapterPosition());
                // Build an alert dialog with options
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Options for " + currentContact.getContactName());
                // Add options to the dialog
                builder.setItems(new CharSequence[]{"Delete Contact", "Return"}, (dialog, which) -> {
                    // Handle the selected option
                    switch (which) {
                        case 0:
                            dbHelper.deleteContact(currentContact);
                            // Notify the activity that a contact is deleted
                            if (contactClickListener != null) {
                                contactClickListener.onDeleteContact(currentContact);
                                Toast.makeText(context, "Contact Deleted", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1:
                            // Option 2 selected
                            break;
                    }
                });
                // Show the alert dialog
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView contact_id_txt, contact_name_txt, contact_phone_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contact_id_txt = itemView.findViewById(R.id.contact_id_txt);
            contact_name_txt = itemView.findViewById(R.id.contact_name_txt);
            contact_phone_txt = itemView.findViewById(R.id.contact_phone_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    public interface ContactClickListener {
        void onDeleteContact(Models.Contact contact);
    }

}
