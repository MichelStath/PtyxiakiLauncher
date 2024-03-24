package com.example.activities.ptyxiakilauncher.classes;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activities.ptyxiakilauncher.R;
import com.example.activities.ptyxiakilauncher.UpdateFastMessageActivity;

import java.util.ArrayList;
import java.util.List;

public class AllAppsAdapter extends RecyclerView.Adapter<AllAppsAdapter.AllAppsViewHolder>{
    private final MessageClickListener messageClickListener;
    private final Context context;
    private final List<String> messages;
    private String currentMessage;

    public AllAppsAdapter(Context context, ArrayList<String> messages, MessageClickListener listener) {
        LocalBroadcastManager.getInstance(context).registerReceiver(updateFinishedReceiver,
                new IntentFilter("com.example.activities.ptyxiakilauncher.UPDATE_ACTIVITY_FINISHED"));
        this.context = context;
        this.messages = messages;
        this.messageClickListener = listener;

    }

    @NonNull
    @Override
    public AllAppsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.app_row,parent,false);
        return new AllAppsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAppsViewHolder holder, int position) {
        Log.d("asdf",String.valueOf(messages.get(position)));
        holder.app_title_txt.setText(messages.get(position));
        holder.mainLayout2.setOnClickListener(v -> {
            if (holder.getAdapterPosition() == RecyclerView.NO_POSITION)
                return;
            // Get the current contact
            currentMessage = messages.get(holder.getAdapterPosition());
            //Run the app
        });

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class AllAppsViewHolder extends RecyclerView.ViewHolder {
        TextView app_title_txt;
        LinearLayout mainLayout2;
        public AllAppsViewHolder(@NonNull View itemView) {
            super(itemView);
            app_title_txt = itemView.findViewById(R.id.app_title_txt);
            mainLayout2 = itemView.findViewById(R.id.mainLayout2);
        }
    }

    private final BroadcastReceiver updateFinishedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.example.activities.ptyxiakilauncher.UPDATE_ACTIVITY_FINISHED".equals(intent.getAction())) {
                if (messageClickListener != null) {
                    Toast.makeText(context, currentMessage, Toast.LENGTH_SHORT).show();
                    messageClickListener.onUpdateMessage(currentMessage);

                }else
                    Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();

            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        // Register the broadcast receiver when the adapter is attached to the RecyclerView
        IntentFilter filter = new IntentFilter("com.example.activities.ptyxiakilauncher.UPDATE_ACTIVITY_FINISHED");
        context.registerReceiver(updateFinishedReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        // Unregister the broadcast receiver when the adapter is detached from the RecyclerView
        LocalBroadcastManager.getInstance(context).unregisterReceiver(updateFinishedReceiver);
        //context.unregisterReceiver(updateFinishedReceiver);
    }
    public interface MessageClickListener {
        void onUpdateMessage(String message);
    }
}
