package com.example.activities.ptyxiakilauncher.classes;

import static com.example.activities.ptyxiakilauncher.classes.Helper.AppHelper.openCamera;
import static com.example.activities.ptyxiakilauncher.classes.Helper.AppHelper.openFileManager;
import static com.example.activities.ptyxiakilauncher.classes.Helper.AppHelper.openGallery;
import static com.example.activities.ptyxiakilauncher.classes.Helper.AppHelper.openMaps;
import static com.example.activities.ptyxiakilauncher.classes.Helper.AppHelper.openPlayStore;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activities.ptyxiakilauncher.R;

import java.util.ArrayList;
import java.util.List;

public class AllAppsAdapter extends RecyclerView.Adapter<AllAppsAdapter.AllAppsViewHolder>{
    private final Context context;
    private final List<String> apps;
    private String currentApp;

    public AllAppsAdapter(Context context, ArrayList<String> apps) {
        this.context = context;
        this.apps = apps;
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
        Log.d("asdf",String.valueOf(apps.get(position)));
        holder.app_title_txt.setText(apps.get(position));
        holder.mainLayout2.setOnClickListener(v -> {
            if (holder.getAdapterPosition() == RecyclerView.NO_POSITION)
                return;
            // Get the current contact
            currentApp = apps.get(holder.getAdapterPosition());
            Toast.makeText(context, currentApp, Toast.LENGTH_SHORT).show();
            switch (currentApp) {
                case "Camera":
                    openCamera(context);
                    break;
                case "Album":
                    openGallery(context);
                    break;
                case "Maps":
                    openMaps(context);
                    break;
                case "Files":
                    openFileManager(context);
                    break;
                case "Play Store":
                    openPlayStore(context);
                    break;
                default:
                    Toast.makeText(context, "Error to find the App", Toast.LENGTH_SHORT).show();
                    break;
            }

            //Run the app
        });

    }

    @Override
    public int getItemCount() {
        return apps.size();
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
}
