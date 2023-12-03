package com.example.activities.ptyxiakilauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_messages);
        db = new Helper.MessageDbHelper(this);
        recyclerView = findViewById(R.id.recyclerView1);
        //db.deleteAllContacts();
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
        //go to add new message
        Intent i = new Intent(this, AddFastMessageActivity.class);
        startActivity(i);
        //UpdateRecyclerView1();
    }
}