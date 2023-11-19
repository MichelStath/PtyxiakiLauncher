package com.example.activities.ptyxiakilauncher;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.activities.ptyxiakilauncher.classes.CustomAdapter;
import com.example.activities.ptyxiakilauncher.classes.Helper;
import com.example.activities.ptyxiakilauncher.classes.Models;

public class FastContactsActivity extends AppCompatActivity implements CustomAdapter.ContactClickListener {
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    Helper.ContactDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_contacts);
        db = new Helper.ContactDbHelper(this);
        recyclerView = findViewById(R.id.recyclerView);

        UpdateRecyclerView();
    }

    @Override
    public void onDeleteContact(Models.Contact contact) {
        UpdateRecyclerView();
    }

    public void addNewContact(View view) {
        pickContactLauncher.launch(new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI));
    }

    public void UpdateRecyclerView(){
        customAdapter = new CustomAdapter(FastContactsActivity.this,db.getAllContacts(),this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FastContactsActivity.this));

    }
    private final ActivityResultLauncher<Intent> pickContactLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Handle the result here, for example, get the selected contact
                    Intent data = result.getData();
                    if (data != null) {
                        Uri contactUri = data.getData();

                        // Specify which fields you want
                        // your query to return values for
                        String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};

                        // Perform your query
                        try (Cursor cursor = getContentResolver().query(contactUri, queryFields, null, null, null)) {
                            // Double-check that you actually got results
                            if (cursor != null && cursor.moveToFirst()) {
                                // Pull out the first column of the first row of data that is your contact's name
                                int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                                int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                                String phone = cursor.getString(phoneIndex);
                                String name = cursor.getString(nameIndex);

                                db.addContact(new Models.Contact(name,phone));
                                Toast.makeText(this, "Contact added: " + name, Toast.LENGTH_SHORT).show();
                                UpdateRecyclerView();
                            }
                        }
                    }
                }
            }
    );

}