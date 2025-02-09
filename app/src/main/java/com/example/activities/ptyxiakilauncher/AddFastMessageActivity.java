package com.example.activities.ptyxiakilauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextPaint;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.activities.ptyxiakilauncher.classes.Filters;
import com.example.activities.ptyxiakilauncher.classes.Helper;
import com.example.activities.ptyxiakilauncher.classes.Models;

public class AddFastMessageActivity extends AppCompatActivity {
    Helper.MessageDbHelper db;
    EditText messTitle,messContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fast_message);
        db = new Helper.MessageDbHelper(this);
        messTitle = findViewById(R.id.add_message_title_txt);
        messContent = findViewById(R.id.add_message_content_txt);
        messContent.setFilters(new InputFilter[] {new Filters.LineCountInputFilter(5,50,this)});
    }

    public void addNewMessageToDB(View view) {
        if (messTitle.getText() != null && messContent.getText() != null){
            db.addMessage(new Models.FastMessage(messTitle.getText().toString(),messContent.getText().toString()));
            finishAndReturn();
        }else {
            Toast.makeText(this, "Fill all the Fields!", Toast.LENGTH_SHORT).show();
        }
    }

    private void finishAndReturn() {
        setResult(Activity.RESULT_OK);
        finish();
    }

}
