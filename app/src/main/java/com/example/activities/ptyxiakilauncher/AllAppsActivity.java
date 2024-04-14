package com.example.activities.ptyxiakilauncher;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activities.ptyxiakilauncher.classes.AllAppsAdapter;
import com.example.activities.ptyxiakilauncher.classes.CustomAdapter1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllAppsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AllAppsAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apps);
        recyclerView = findViewById(R.id.AllAppsRecyclerView);

        UpdateAllAppsRecyclerView();
    }

    public void UpdateAllAppsRecyclerView(){
        ArrayList<String> myList = new ArrayList<>();
        myList.add("Camera");
        myList.add("Album");
        myList.add("Maps");
        myList.add("Files");
        myList.add("Play Store");
        customAdapter = new AllAppsAdapter(AllAppsActivity.this, myList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllAppsActivity.this));
    }


}