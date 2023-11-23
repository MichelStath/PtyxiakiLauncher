package com.example.activities.ptyxiakilauncher.classes;

import static com.example.activities.ptyxiakilauncher.classes.LocationHelper.isGpsEnabled;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeThread extends Thread {
    private final Handler handler;
    private final TextView tv1, tv2;
    private final Activity activity;
    private boolean threadIsRunning = true;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    public DateTimeThread(Handler handler, TextView tv1, TextView tv2) {
        this.handler = handler;
        this.tv1 = tv1;
        this.tv2 = tv2;
        this.activity =(Activity) tv1.getContext();
    }

    @Override
    public synchronized void start() {
        Log.i("THREAD","Thread Start");
        super.start();
    }

    private final Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            if (threadIsRunning) {
                String currentDate = dateFormat.format(new Date());
                String currentTime = timeFormat.format(new Date());
                tv1.setText(currentDate);
                tv2.setText(currentTime);
                if (!isGpsEnabled(tv1.getContext()))
                    LocationHelper.showGpsSettings(activity);
                handler.postDelayed(this, 1000); // Schedule the next update after 1 second
            }
        }
    };

    @Override
    public void run() {
        PowerManager pm = (PowerManager) tv1.getContext().getSystemService(Context.POWER_SERVICE);

        handler.post(updateTimeRunnable); // Start the initial update

        while (threadIsRunning && !Thread.interrupted()) {
            if (!pm.isInteractive()) {
                handler.removeCallbacks(updateTimeRunnable); // Stop updates if the screen is not interactive
                return;
            }
        }
    }

    // Getter for threadIsRunning
    public boolean isThreadRunning() {
        return threadIsRunning;
    }

    // Setter for threadIsRunning
    public void setThreadRunning(boolean threadIsRunning) {
        this.threadIsRunning = threadIsRunning;
    }

    public void stopThread(){
        Log.i("THREAD","Thread Stopped");
        this.threadIsRunning = false;
    }
}
