package com.example.activities.ptyxiakilauncher.classes;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activities.ptyxiakilauncher.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter1 extends RecyclerView.Adapter<CustomAdapter1.MyViewHolder1> {
    private final MessageClickListener messageClickListener;
    private final Context context;
    private final List<Models.FastMessage> messages;
    //int position;
    private final Helper.MessageDbHelper dbHelper;

   public CustomAdapter1(Context context, ArrayList<Models.FastMessage> messages, MessageClickListener listener){
        this.context = context;
        this.messages=messages;
        this.messageClickListener = listener;
        this.dbHelper = new Helper.MessageDbHelper(context);

    }


    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.message_row,parent,false);
        return new MyViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {
        Log.d("asdf",String.valueOf(messages.get(position).getFastMessageID()));
        Log.d("asdf",String.valueOf(messages.get(position).getFastMessageTitle()));
        Log.d("asdf",String.valueOf(messages.get(position).getFastMessageContent()));
        holder.message_id_txt.setText(String.valueOf(messages.get(position).getFastMessageID()));
        holder.message_title_txt.setText(String.valueOf(messages.get(position).getFastMessageTitle()));
        holder.message_content_txt.setText(String.valueOf(messages.get(position).getFastMessageContent()));
        holder.mainLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getAdapterPosition() == RecyclerView.NO_POSITION)
                    return;
                // Get the current contact
                Models.FastMessage currentMessage = messages.get(holder.getAdapterPosition());
                // Build an alert dialog with options
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Options for " + currentMessage.getFastMessageTitle());
                // Add options to the dialog
                builder.setItems(new CharSequence[]{"Update Message", "Send Message", "Delete Message"}, (dialog, which) -> {
                    // Handle the selected option
                    switch (which) {
                        case 0:

                            // Notify the activity that a contact is deleted
                            if (messageClickListener != null) {
                                messageClickListener.onDeleteMessage(currentMessage);
                            }
                            break;
                        case 1:
                            // Option 2 selected
                            Helper.SMSHelper.composeSMS(context, currentMessage.getFastMessageContent());
                            break;
                        case 2:
                            // Option 3 selected
                            dbHelper.deleteContact(currentMessage);
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
        return messages.size();
    }

    public static class MyViewHolder1 extends RecyclerView.ViewHolder {
        TextView message_id_txt, message_title_txt, message_content_txt;
        LinearLayout mainLayout1;
        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);
            message_id_txt = itemView.findViewById(R.id.message_id_txt);
            message_title_txt = itemView.findViewById(R.id.message_title_txt);
            message_content_txt = itemView.findViewById(R.id.message_content_txt);
            mainLayout1 = itemView.findViewById(R.id.mainLayout1);
        }
    }

    public interface MessageClickListener {
        void onDeleteMessage(Models.FastMessage message);
    }

}
