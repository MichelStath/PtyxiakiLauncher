package com.example.activities.ptyxiakilauncher.classes;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.activities.ptyxiakilauncher.R;

public class BatHelper {

    private TextView tv;
    private ImageView iv;

    public BatHelper(TextView tv, ImageView iv) {
        this.tv = tv;
        this.iv = iv;
    }

    public  void setBatLevel(int level){

        tv.setText(level + " %");
        if (level < 10) batLevel1();
        else if (level < 25) batLevel2(level);
        else if (level < 45) batLevel3(level);
        else if (level < 60) batLevel4();
        else if (level < 85) batLevel5();
        else if (level < 100) batLevel6();
        else if (level == 100) batLevelFull();
    }
    private  void batLevel1(){
        tv.setTextColor(ContextCompat.getColor(tv.getContext(), R.color.red));
        iv.setForeground(ContextCompat.getDrawable(iv.getContext(),R.drawable.baseline_battery_1_bar_24));
    }

    private  void batLevel2(int lvl){
        tv.setTextColor(ContextCompat.getColor(tv.getContext(),R.color.orangered));
        iv.setForeground(ContextCompat.getDrawable(iv.getContext(),R.drawable.baseline_battery_2_bar_24));
        if (lvl == 20 ) playMusic(/*Song name*/);
    }
    private  void batLevel3(int lvl){
        tv.setTextColor(ContextCompat.getColor(tv.getContext(),R.color.orange));
        iv.setForeground(ContextCompat.getDrawable(iv.getContext(),R.drawable.baseline_battery_3_bar_24));
        if (lvl == 40)  playMusic(/*Song name*/);

    }
    private void batLevel4(){
        tv.setTextColor(ContextCompat.getColor(tv.getContext(),R.color.olive));
        iv.setForeground(ContextCompat.getDrawable(iv.getContext(),R.drawable.baseline_battery_4_bar_24));
    }
    private void batLevel5(){
        tv.setTextColor(ContextCompat.getColor(tv.getContext(),R.color.green));
        iv.setForeground(ContextCompat.getDrawable(iv.getContext(), R.drawable.baseline_battery_5_bar_24));
    }
    private void batLevel6(){
        tv.setTextColor(ContextCompat.getColor(tv.getContext(),R.color.lightgreen));
        iv.setForeground(ContextCompat.getDrawable(iv.getContext(),R.drawable.baseline_battery_6_bar_24));
    }
    private void batLevelFull(){
        tv.setTextColor(ContextCompat.getColor(tv.getContext(),R.color.lightgreen));
        iv.setForeground(ContextCompat.getDrawable(iv.getContext(),R.drawable.baseline_battery_full_24));
    }

    //region START/STOP AUDIO
    private void playMusic() {
    }
    private void stopMusic() {
    }
    //endregion
}
