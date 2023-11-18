package com.example.activities.ptyxiakilauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    //region SharedPreferences Keys
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String NAME_KEY = "name_key";
    public static final String SURNAME_KEY = "surname_key";
    public static final String PHONE_KEY = "phone_key";
    public static final String AFM_KEY = "afm_key";
    public static final String AMKA_KEY = "amka_key";
    public static final String IDNUM_KEY = "idNum_key";
    //endregion
    String userName, userSurname, userPhone, userAmka, userAfm, userIdNum;
    TextView nameTV, surnameTV, phoneTV, afmTV, amkaTV,idNumTV;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        userName = sharedpreferences.getString(NAME_KEY,null);
        userSurname = sharedpreferences.getString(SURNAME_KEY,null);
        userPhone = sharedpreferences.getString(PHONE_KEY,null);
        userAfm = sharedpreferences.getString(AFM_KEY,null);
        userAmka = sharedpreferences.getString(AMKA_KEY,null);
        userIdNum =sharedpreferences.getString(IDNUM_KEY,null);

        nameTV = findViewById(R.id.userNameTV);
        surnameTV = findViewById(R.id.userSurnameTV);
        phoneTV = findViewById(R.id.userPhoneTV);
        afmTV = findViewById(R.id.userAfmTV);
        amkaTV = findViewById(R.id.userAmkaTV);
        idNumTV = findViewById(R.id.userIdNumTV);

        nameTV.setText(userName);
        surnameTV.setText(userSurname);
        phoneTV.setText(userPhone);
        afmTV.setText(userAfm);
        amkaTV.setText(userAmka);
        idNumTV.setText(userIdNum);


    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(userName == null){
            Intent i = new Intent(this,InfoEditActivity.class);
            startActivity(i);
        }

    }

    public void editBTN(View view) {
        Intent i = new Intent(this,InfoEditActivity.class);
        startActivity(i);
    }
}