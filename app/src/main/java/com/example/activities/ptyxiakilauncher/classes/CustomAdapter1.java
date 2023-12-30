package com.example.activities.ptyxiakilauncher.classes;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activities.ptyxiakilauncher.R;
import com.example.activities.ptyxiakilauncher.UpdateFastMessageActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter1 extends RecyclerView.Adapter<CustomAdapter1.MyViewHolder1> {
    private final MessageClickListener messageClickListener;
    private final Context context;
    private final List<Models.FastMessage> messages;
    private final Helper.MessageDbHelper dbHelper;
    private Models.FastMessage currentMessage;

   public CustomAdapter1(Context context, ArrayList<Models.FastMessage> messages, MessageClickListener listener){
       LocalBroadcastManager.getInstance(context).registerReceiver(updateFinishedReceiver,
               new IntentFilter("com.example.activities.ptyxiakilauncher.UPDATE_ACTIVITY_FINISHED"));
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
                currentMessage = messages.get(holder.getAdapterPosition());
                // Build an alert dialog with options
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Options for " + currentMessage.getFastMessageTitle());
                // Add options to the dialog
                builder.setItems(new CharSequence[]{"Update Message", "Send Message", "Delete Message"}, (dialog, which) -> {
                    // Handle the selected option
                    switch (which) {
                        case 0:
                            // Notify the activity that a contact is deleted
                            Intent intent = new Intent(context, UpdateFastMessageActivity.class);
                            intent.putExtra("fastMessage", currentMessage);
                            context.startActivity(intent);
                            break;
                        case 1:
                            // Option 2 selected
                            Helper.SMSHelper.composeSMS(context, currentMessage.getFastMessageContent());
                            break;
                        case 2:
                            // Option 3 selected
                            dbHelper.deleteMessage(currentMessage);
                            if (messageClickListener != null) {
                                messageClickListener.onDeleteMessage(currentMessage);
                            }
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

    private final BroadcastReceiver updateFinishedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.example.activities.ptyxiakilauncher.UPDATE_ACTIVITY_FINISHED".equals(intent.getAction())) {
                if (messageClickListener != null) {
                    Toast.makeText(context, currentMessage.getFastMessageTitle(), Toast.LENGTH_SHORT).show();
                    messageClickListener.onUpdateMessage(currentMessage);

                }else
                    Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();

            }
        }
    };

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        // Register the broadcast receiver when the adapter is attached to the RecyclerView
        IntentFilter filter = new IntentFilter("com.example.activities.ptyxiakilauncher.UPDATE_ACTIVITY_FINISHED");
        context.registerReceiver(updateFinishedReceiver, filter);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        // Unregister the broadcast receiver when the adapter is detached from the RecyclerView
        LocalBroadcastManager.getInstance(context).unregisterReceiver(updateFinishedReceiver);
        //context.unregisterReceiver(updateFinishedReceiver);
    }
    public interface MessageClickListener {
        void onDeleteMessage(Models.FastMessage message);
        void onUpdateMessage(Models.FastMessage message);
    }


}
