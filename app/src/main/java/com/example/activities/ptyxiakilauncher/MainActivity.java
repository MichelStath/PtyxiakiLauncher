package com.example.activities.ptyxiakilauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    TextView batteryLevelTV, dateTV, timeTV;
    ImageView batteryLevelIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        batteryLevelTV = findViewById(R.id.batteryLevelTV);
        dateTV = findViewById(R.id.dateTV);
        timeTV = findViewById(R.id.timeTV);
        batteryLevelIV = findViewById(R.id.batteryLevelIV);
        setDateTime();
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

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
        batteryLevelTV.setText(String.valueOf(level) + " %");
        if (level == 100) battLevelFull();
        if(level > 95) battLevel6();
        else if (level > 85) battLevel5();
        else if (level > 60) battLevel4();
        else if (level > 45) battLevel3(level);


        else if (level > 30 ) battLevel2(level);
        else battLevel1();
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
        if (lvl == 25 ) playMusic(/*Song name*/);

    }
    public void battLevel3(int lvl){
        batteryLevelTV.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.orange));
        batteryLevelIV.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_battery_3_bar_24));
        if (lvl == 50)  playMusic(/*Song name*/);

    }


    public void battLevel4(){
        batteryLevelTV.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.olive));
        batteryLevelIV.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_battery_4_bar_24));
    }
    public void battLevel5(){
        batteryLevelTV.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
        batteryLevelIV.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_battery_5_bar_24));
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

    public void testBTN(View view) {
        Intent i = new Intent(this,InfoActivity.class);
        startActivity(i);
    }

    private void playMusic() {
    }

    //region HOME BUTTONS
    public void dialBTN_Clicked(View view) {
        //OK
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void contactsBTN_Clicked(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
        startActivity(intent);
    }

    public void messageBTN_Clicked(View view) {
        //OK
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"));
        startActivity(sendIntent);
    }

    public void infoBTN_Clicked(View view) {
        //OK
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
    }

    public void chatApp_Clicked(View view) {
    }
    //endregion






}