package com.example.activities.ptyxiakilauncher;

import static android.provider.ContactsContract.Directory.DISPLAY_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_READ_CONTACTS_PERMISSION = 0;
    private static final int REQUEST_CONTACT = 1;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String FAST_CALL_KEY = "fast_call_key";

    SharedPreferences sharedpreferences;
    TextView batteryLevelTV, dateTV, timeTV;
    ImageView batteryLevelIV;
    String fastCallNUM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        fastCallNUM = sharedpreferences.getString(FAST_CALL_KEY,null);
        batteryLevelTV = findViewById(R.id.batteryLevelTV);
        dateTV = findViewById(R.id.dateTV);
        timeTV = findViewById(R.id.timeTV);
        batteryLevelIV = findViewById(R.id.batteryLevelIV);
        setDateTime();
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        requestContactsPermission();
        updateButton(hasContactsPermission());

    }

    public void testBTN(View view) {
        Log.i(this.getClass().getSimpleName(),"this is a test");
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(FAST_CALL_KEY,null);
        editor.apply();
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctx, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryPct = (int) (level * 100 / (float)scale);
            setBatteryLevel(batteryPct);
            setDateTime();

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void setBatteryLevel(int level){
        batteryLevelTV.setText(level + " %");
        if (level < 10) battLevel1();
        else if (level < 25) battLevel2(level);
        else if (level < 45) battLevel3(level);
        else if (level < 60) battLevel4();
        else if (level < 85) battLevel5();
        else if (level < 95) battLevel6();
        else if (level == 100) battLevelFull();
    }
    public void setDateTime(){
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        dateTV.setText(currentDate);
        timeTV.setText(currentTime);
    }

    //region visualize battery level
    public void battLevel1(){
        batteryLevelTV.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
        batteryLevelIV.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_battery_1_bar_24));
    }
    public void battLevel2(int lvl){
        batteryLevelTV.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.orangered));
        batteryLevelIV.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_battery_2_bar_24));
        if (lvl == 20 ) playMusic(/*Song name*/);
    }
    public void battLevel3(int lvl){
        batteryLevelTV.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.orange));
        batteryLevelIV.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_battery_3_bar_24));
        if (lvl == 40)  playMusic(/*Song name*/);

    }
    public void battLevel4(){
        batteryLevelTV.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.olive));
        batteryLevelIV.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_battery_4_bar_24));
    }
    public void battLevel5(){
        batteryLevelTV.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
        batteryLevelIV.setForeground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.baseline_battery_5_bar_24));
    }
    public void battLevel6(){
        batteryLevelTV.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.lightgreen));
        batteryLevelIV.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_battery_6_bar_24));
    }
    public void battLevelFull(){
        batteryLevelTV.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.lightgreen));
        batteryLevelIV.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_battery_full_24));
    }
    //endregion

    //region START/STOP AUDIO
    private void playMusic() {
    }
    private void stopMusic() {
    }
    //endregion

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
    }

    public void allAppsBTN_Clicked(View view) {
    }

    public void settingsBTN_Clicked(View view) {
        //OK
        Intent i = new Intent(android.provider.Settings.ACTION_SETTINGS);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void browserBTN_Clicked(View view) {
        //OK
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com"));
        startActivity(browserIntent);
    }

    public void fastCallBTN_Clicked(View view) {
        fastCallNUM = sharedpreferences.getString(FAST_CALL_KEY,null);
        if (fastCallNUM != null){
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+ fastCallNUM));
            startActivity(intent);
        }else {
            Toast.makeText(this, "Add a number", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Den yparxei apothikeymenos arithmos \nThelete na prosthesete twra ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            final Intent i = new Intent(Intent.ACTION_PICK,ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                            startActivityForResult(i, REQUEST_CONTACT);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create();
            builder.show();

        }

    }

    public void chatAppBTN_Clicked(View view) {
    }
    //endregion

    //region MAIN MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "FastCall Set", Toast.LENGTH_SHORT).show();
                final Intent i = new Intent(Intent.ACTION_PICK,ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region CONTACT PERMISSIONS - SET FASTCALL-NUMBER FROM CONTACTS
    private boolean hasContactsPermission()
    {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED;
    }
    // Request contact permission if it has not been granted already
    private void requestContactsPermission()
    {
        if (!hasContactsPermission())
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS_PERMISSION);
        }
    }
    public void updateButton(boolean enable)
    {
        Log.i("updateBTN", String.valueOf(enable));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_READ_CONTACTS_PERMISSION && grantResults.length > 0)
        {
            updateButton(grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_CONTACT && data != null)
        {
            Uri contactUri = data.getData();

            // Specify which fields you want
            // your query to return values for
            String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME};

            // Perform your query - the contactUri
            // Perform your query - the contactUri
            // is like a "where" clause here
            Cursor cursor = this.getContentResolver()
                    .query(contactUri, null, null, null, null);

            try
            {
                // Double-check that you actually got results
                if (cursor.getCount() == 0) return;

                // Pull out the first column of the first row of data that is your contact's name
                cursor.moveToFirst();
                String phone = null;
                int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                phone = cursor.getString(phoneIndex);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(FAST_CALL_KEY,phone);
                editor.apply();
                Toast.makeText(this, "Phone saved" + sharedpreferences.getString(FAST_CALL_KEY,null), Toast.LENGTH_SHORT).show();
                Log.i("Contact Phone",phone);
            }
            finally
            {
                cursor.close();
            }
        }
    }
    //endregion
}