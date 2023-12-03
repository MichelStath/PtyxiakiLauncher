package com.example.activities.ptyxiakilauncher;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.activities.ptyxiakilauncher.classes.CustomAdapter1;
import com.example.activities.ptyxiakilauncher.classes.Helper;
import com.example.activities.ptyxiakilauncher.classes.Models;

public class FastMessagesActivity extends AppCompatActivity implements CustomAdapter1.MessageClickListener{
    RecyclerView recyclerView;
    CustomAdapter1 customAdapter;
    Helper.MessageDbHelper db;
    private static final int ADD_MESSAGE_REQUEST_CODE = 1;
    private ActivityResultLauncher<Intent> startActivityForResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_messages);
        db = new Helper.MessageDbHelper(this);
        recyclerView = findViewById(R.id.recyclerView1);
        //db.deleteAllContacts();

        // Initialize the ActivityResultLauncher
        startActivityForResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Handle the result here
                        UpdateRecyclerView1();
                    }
                }
        );

        UpdateRecyclerView1();
    }

    public void UpdateRecyclerView1(){
        customAdapter = new CustomAdapter1(FastMessagesActivity.this,db.getAllMessages(),this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FastMessagesActivity.this));

    }

    @Override
    public void onDeleteMessage(Models.FastMessage message) {
        UpdateRecyclerView1();
    }

    public void addNewMessage(View view) {
        Intent i = new Intent(this, AddFastMessageActivity.class);
        startActivityForResultLauncher.launch(i);
    }
}