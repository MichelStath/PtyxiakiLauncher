package com.example.activities.ptyxiakilauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.activities.ptyxiakilauncher.classes.Filters;
import com.example.activities.ptyxiakilauncher.classes.Helper;
import com.example.activities.ptyxiakilauncher.classes.Models;

public class UpdateFastMessageActivity extends AppCompatActivity {
    Helper.MessageDbHelper db;
    Models.FastMessage currentFastMessage;
    EditText messTitle,messContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fast_message);
        currentFastMessage = (Models.FastMessage) getIntent().getSerializableExtra("fastMessage");
        db = new Helper.MessageDbHelper(this);
        messTitle = findViewById(R.id.update_message_title_txt);
        messContent = findViewById(R.id.update_message_content_txt);
        messContent.setFilters(new InputFilter[] {new Filters.LineCountInputFilter(5,50,this)});

        messTitle.setText(currentFastMessage.getFastMessageTitle());
        messContent.setText(currentFastMessage.getFastMessageContent());
    }

    public void updateMessageToDB(View view) {
        if (messTitle.getText() != null && messContent.getText() != null){
            db.updateMessage(new Models.FastMessage(currentFastMessage.getFastMessageID(),messTitle.getText().toString(),messContent.getText().toString()));
            finishAndReturn();
        }else {
            Toast.makeText(this, "Fill all the Fields!", Toast.LENGTH_SHORT).show();
        }
    }

    private void finishAndReturn() {
        Intent resultIntent = new Intent("com.example.activities.ptyxiakilauncher.UPDATE_ACTIVITY_FINISHED");
        sendBroadcast(resultIntent);  // Send broadcast to notify listeners
        finish();
    }
}