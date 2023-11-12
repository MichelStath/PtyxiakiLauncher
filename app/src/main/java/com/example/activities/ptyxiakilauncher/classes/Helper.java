package com.example.activities.ptyxiakilauncher.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Helper {

    public static class DbHelper extends SQLiteOpenHelper {

        private final Context context;
        private static final String DATABASE_NAME = "MyContacts.db";
        private static final int DATABASE_VERSION = 1;

        private static final String TABLE_NAME = "my_contacts";
        private static final String COLUMN_ID = "_id";
        private static final String COLUMN_CONTACT_NAME = "contact_name";
        private static final String COLUMN_CONTACT_PHONE = "contact_phone";

        public DbHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTableQuery = "CREATE TABLE " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CONTACT_NAME + " TEXT, " +
                    COLUMN_CONTACT_PHONE + " TEXT);";
            db.execSQL(createTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public void addContact(Models.Contact contact) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_CONTACT_NAME, contact.getContactName());
            cv.put(COLUMN_CONTACT_PHONE, contact.getContactNumber());

            long result = db.insert(TABLE_NAME, null, cv);

            if (result == -1)
                Toast.makeText(context, "Insert to DATABASE Failed", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }

        public List<Models.Contact> getAllContacts() {

            List<Models.Contact> contactList = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM " + TABLE_NAME;

            try (Cursor cursor = db.rawQuery(selectQuery, null)) {
                if (cursor.moveToFirst()) {
                    do {
                        int contactIDIndex = cursor.getColumnIndex(COLUMN_ID);
                        int contactNameIndex = cursor.getColumnIndex(COLUMN_CONTACT_NAME);
                        int contactPhoneIndex = cursor.getColumnIndex(COLUMN_CONTACT_PHONE);

                        // Log column indices for debugging
                        Log.d("DbHelper", "Contact ID Index: " + contactIDIndex);
                        Log.d("DbHelper", "Contact Name Index: " + contactNameIndex);
                        Log.d("DbHelper", "Contact Phone Index: " + contactPhoneIndex);

                        // Check if column indices are valid before extracting data
                        if (contactIDIndex >= 0 && contactNameIndex >= 0 && contactPhoneIndex >= 0) {
                            int contactID = cursor.getInt(contactIDIndex);
                            String contactName = cursor.getString(contactNameIndex);
                            String contactPhone = cursor.getString(contactPhoneIndex);

                            Models.Contact contact = new Models.Contact(contactID, contactName, contactPhone);
                            contactList.add(contact);
                        } else {
                            Log.e("DbHelper", "Invalid column index found.");
                        }
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                Log.e("DbHelper", "Error retrieving contacts: " + e.getMessage());
            }

            return contactList;
        }


        public void deleteContact(Models.Contact contact) {
            SQLiteDatabase db = this.getWritableDatabase();
            long result = db.delete(TABLE_NAME, "_id=?", new String[]{Integer.toString(contact.getContactID())});
            if (result == -1) {
                Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
            }
        }
    }




}