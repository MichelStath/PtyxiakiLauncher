package com.example.activities.ptyxiakilauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.activities.ptyxiakilauncher.classes.CustomAdapter;
import com.example.activities.ptyxiakilauncher.classes.Helper;

public class FastContactsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    Helper.ContactDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_contacts);
        db = new Helper.ContactDbHelper(this);
        recyclerView = findViewById(R.id.recyclerView);

        customAdapter = new CustomAdapter(FastContactsActivity.this,db.getAllContacts());
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FastContactsActivity.this));

    }

    public void addNewContact(View view) {
    }

    public void DeleteCurrentContact(View view) {
    }
}