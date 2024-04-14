package com.example.activities.ptyxiakilauncher;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activities.ptyxiakilauncher.classes.BatHelper;
import com.example.activities.ptyxiakilauncher.classes.DateTimeThread;
import com.example.activities.ptyxiakilauncher.classes.Helper;
import com.example.activities.ptyxiakilauncher.classes.Helper.ContactDbHelper;
import com.example.activities.ptyxiakilauncher.classes.LocationHelper;
import com.example.activities.ptyxiakilauncher.classes.Models;
import com.example.activities.ptyxiakilauncher.classes.Models.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ALL_PERMISSIONS = 1;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.SEND_SMS,
            // Add other permissions here
    };
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String FAST_CALL_KEY = "fast_call_key";

    SharedPreferences sharedpreferences;
    public TextView batteryLevelTV, dateTV, timeTV;
    ImageView batteryLevelIV;
    String fastCallNUM;
    DateTimeThread thread;
    Handler handler;
    ContactDbHelper db;
    private boolean shouldContinueLocationUpdates;

    private BatHelper bh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeEnvironment();
        CheckFastContacts();

        // Request location permission and initiate location retrieval
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        requestAllPermissions();
        updateButton(hasAllPermissions());
        startTimeThread();

    }

    private void CheckFastContacts() {
        if(db.getAllContacts().isEmpty()){
            //FAST CONTACTS ARE MANDATORY FOR THE APP.
            Toast.makeText(this,"FAST CONTACTS ARE MANDATORY FOR THE APP", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,FastContactsActivity.class);
            startActivity(i);
        }
    }

    private void InitializeEnvironment(){
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        fastCallNUM = sharedpreferences.getString(FAST_CALL_KEY,null);
        batteryLevelTV = findViewById(R.id.batteryLevelTV);
        dateTV = findViewById(R.id.dateTV);
        timeTV = findViewById(R.id.timeTV);
        handler = new Handler(getMainLooper());
        thread = new DateTimeThread(handler,dateTV,timeTV);
        batteryLevelIV = findViewById(R.id.batteryLevelIV);
        db = new ContactDbHelper(this);
        bh = new BatHelper(batteryLevelTV, batteryLevelIV);

        LocationHelper.requestLocationPermission(this);

    }

    public void testBTN(View view) {
        Contact a = new Contact("Test","123");
        ContactDbHelper db = new ContactDbHelper(MainActivity.this);
        db.addContact(a);
        List<Contact> all = db.getAllContacts();
        Toast.makeText(this, "List Length: " + all.size(), Toast.LENGTH_SHORT).show();
        //List<Models.Contact> ab = Helper.DbHelper.getAllContacts();
    }

    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctx, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryPct = (int) (level * 100 / (float)scale);
            bh.setBatLevel(batteryPct);
        }
    };

    @Override
    protected void onPause() {
        stopTimeThread();
        super.onPause();
    }

    @Override
    protected void onResume() {
        startTimeThread();
        super.onResume();
    }

    public void startTimeThread(){
        if (thread == null || !thread.isAlive()) {
            thread = new DateTimeThread(handler, dateTV, timeTV);
            thread.start();
        }
    }

    public void stopTimeThread(){
        thread.stopThread();
    }


    //region HOME BUTTONS
    public void dialBTN_Clicked(View view) {
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void contactsBTN_Clicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
        startActivity(intent);
    }

    public void messageBTN_Clicked(View view) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"));
        startActivity(sendIntent);
    }

    public void infoBTN_Clicked(View view) {
        Intent i = new Intent(this,InfoActivity.class);
        startActivity(i);
    }

    public void sosBTN_Clicked(View view) {
        if (!shouldContinueLocationUpdates)
            startContinuousLocationUpdates();
    }



    public void allAppsBTN_Clicked(View view) {
        Intent i = new Intent(this,AllAppsActivity.class);
        startActivity(i);
    }

    public void settingsBTN_Clicked(View view) {
        Intent i = new Intent(android.provider.Settings.ACTION_SETTINGS);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void browserBTN_Clicked(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com"));
        startActivity(browserIntent);
    }

    // Inside your fastCallBTN_Clicked method
    public void fastCallBTN_Clicked(View view) {
        fastCallNUM = sharedpreferences.getString(FAST_CALL_KEY, null);
        if (fastCallNUM != null) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + fastCallNUM));
            startActivity(intent);
        } else {
            Toast.makeText(this, "Add a number", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No Saved Phone \nAdd now ?")
                    .setPositiveButton("Yes", (dialog, id) -> {
                        // Use the ActivityResultLauncher to start the contact picker
                        pickContactLauncher.launch(new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI));
                    })
                    .setNegativeButton("No", (dialog, id) -> {
                        // User cancelled the dialog
                    });
            // Create the AlertDialog object and show it
            builder.create().show();
        }
    }

    public void chatAppBTN_Clicked(View view) {
        Intent i = new Intent(this,FastMessagesActivity.class);
        startActivity(i);
    }
    //endregion

    //region MAIN MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "FastCall Set", Toast.LENGTH_SHORT).show();
                // Use the ActivityResultLauncher to start the contact picker
                pickContactLauncher.launch(new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI));
                return true;
            case R.id.item2:
                Toast.makeText(this, "FastCall Set", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,FastContactsActivity.class);
                startActivity(i);
                // Use the ActivityResultLauncher to start the contact picker
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion


    // Declare a variable for the ActivityResultLauncher
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

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(FAST_CALL_KEY, phone);
                                editor.apply();
                                Toast.makeText(this, "Phone saved: " + sharedpreferences.getString(FAST_CALL_KEY, null), Toast.LENGTH_SHORT).show();
                                Toast.makeText(this, "Phone saved: " + name, Toast.LENGTH_SHORT).show();
                                Log.i("Contact Phone", phone);
                            }
                        }
                    }
                }
            }
    );

    //region CONTACT PERMISSIONS - SET FASTCALL-NUMBER FROM CONTACTS
    private boolean hasAllPermissions() {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // Request contact permission if it has not been granted already
    private void requestAllPermissions() {
        if (!hasAllPermissions())
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_ALL_PERMISSIONS);
    }

    public void updateButton(boolean enable) {
        Log.i("updateBTN", String.valueOf(enable));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LocationHelper.onRequestPermissionsResult(requestCode,  grantResults, this);

        if (requestCode == REQUEST_ALL_PERMISSIONS) {
            boolean allPermissionsGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
            if (allPermissionsGranted) {
                // All permissions granted, proceed with your logic
                updateButton(true);
            } else {
                // Some permissions denied, request again or inform the user
                Toast.makeText(this, "The permissions are MANDATORY to use the app", Toast.LENGTH_SHORT).show();
                boolean shouldRequestAgain = true;
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED && !ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        // Permission denied and "Don't ask again" not checked
                        shouldRequestAgain = false;
                        break;
                    }
                }
                if (shouldRequestAgain) {
                    // Request permissions again
                    requestAllPermissions();
                } else {
                    // Inform the user about the importance of the permission or provide an alternative
                    Toast.makeText(this, "Permissions are required for the app to function properly.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }


    }
//endregion

    private void startContinuousLocationUpdates() {
        shouldContinueLocationUpdates = true;
        long st = System.currentTimeMillis();
        Runnable locationRunnable = new Runnable() {
            @Override
            public void run() {
                if (!shouldContinueLocationUpdates) {
                    // Stop the updates when the flag is set to false
                    return;
                }

                LocationHelper.requestLocationPermission(MainActivity.this);
                Location a = LocationHelper.getLastKnownLocation();

                if (a != null) {
                    String mapsUrl = "http://maps.google.com/maps?q=" + a.getLatitude() + "," + a.getLongitude();

                    ArrayList<Contact> ab = db.getAllContacts();
                    Toast.makeText(MainActivity.this, "Your location is " + a.getLatitude(), Toast.LENGTH_SHORT).show();
                    for (Contact ct : ab) {
                        Helper.sendAlertToContact(ct, mapsUrl, getApplicationContext());
                    }
                    LocationHelper.stopLocationUpdates();
                    stopContinuousLocationUpdates();
                    Log.d("SOS TIME", String.valueOf(System.currentTimeMillis() - st));
                } else {
                    Log.d("SOS", "NULL LOCATION");
                    // Retry location request after a delay
                    handler.postDelayed(this, 1000); // 1000 milliseconds (1 second)
                }
            }
        };
        // Start the initial location request
        // Run the location update loop
        handler.post(locationRunnable);
    }

    private void stopContinuousLocationUpdates() {
        shouldContinueLocationUpdates = false;
    }
}