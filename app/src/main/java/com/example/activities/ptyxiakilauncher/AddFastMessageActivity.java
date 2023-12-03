package com.example.activities.ptyxiakilauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextPaint;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        messContent.setFilters(new InputFilter[] {new LineCountInputFilter(5,50,this)});

    }

    public void addNewMessageToDB(View view) {
        if (messTitle.getText() != null && messContent.getText() != null){
            db.addMessage(new Models.FastMessage(messTitle.getText().toString(),messContent.getText().toString()));
            finish();
        }else {
            Toast.makeText(this, "Fill all the Fields!", Toast.LENGTH_SHORT).show();
        }
    }

    private static class LineCountInputFilter implements InputFilter {
        private int maxLines;
        private int maxChars;
        private Context context;

        public LineCountInputFilter(int maxLines, int maxChars, Context context) {
            this.maxLines = maxLines;
            this.maxChars = maxChars;
            this.context = context;
        }


        private int countOccurrences(String text, char target) {
            int count = 0;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == target) {
                    count++;
                }
            }
            return count;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            int newLinesToBeAdded = countOccurrences(source.toString(), '\n');

            int lines = countOccurrences(dest.toString(), '\n') + newLinesToBeAdded;

            if (lines > maxLines) {
                int allowedLines = maxLines - countOccurrences(dest.toString(), '\n');
                int allowedLength = dest.length() + (end - start) - (dend - dstart);

                if (allowedLength == 0) {
                    return "";
                }

                int sourceEnd = start + Math.min(allowedLength, end - start);

                while (sourceEnd > start && source.charAt(sourceEnd - 1) != '\n') {
                    sourceEnd--;
                }

                // Check if the last character to be added is '\n'
                if (sourceEnd > start && source.charAt(sourceEnd - 1) == '\n') {
                    // If it is, don't add the '\n' and just return the preceding characters
                    sourceEnd--;
                }

                return source.subSequence(start, sourceEnd);
            }

            int remainingChars = maxChars - dest.length();
            int charsToKeep = Math.min(end - start, remainingChars);

            if (dest.length() + charsToKeep >= maxChars) {
                Toast.makeText(context, "Max Length 50 chars", Toast.LENGTH_SHORT).show();
            }
            return source.subSequence(start, start + charsToKeep);
        }

    }
}
