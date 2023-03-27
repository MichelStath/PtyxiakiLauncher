package com.example.activities.ptyxiakilauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class InfoEditActivity extends AppCompatActivity {

    //region SharedPreferences Keys
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String NAME_KEY = "name_key";
    public static final String SURNAME_KEY = "surname_key";
    public static final String PHONE_KEY = "phone_key";
    public static final String AFM_KEY = "afm_key";
    public static final String AMKA_KEY = "amka_key";
    public static final String IDNUM_KEY = "idNum_key";
    SharedPreferences sharedpreferences;
    //endregion

    String userName, userSurname, userPhone, userAmka, userAfm, userIdNum;

    EditText nameET, surnameET, phoneET, afmET, amkaET,idNumET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_edit);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        userName = sharedpreferences.getString(NAME_KEY,null);
        userSurname = sharedpreferences.getString(SURNAME_KEY,null);
        userPhone = sharedpreferences.getString(PHONE_KEY,null);
        userAfm = sharedpreferences.getString(AFM_KEY,null);
        userAmka = sharedpreferences.getString(AMKA_KEY,null);
        userIdNum =sharedpreferences.getString(IDNUM_KEY,null);

        nameET = findViewById(R.id.userNameET);
        surnameET = findViewById(R.id.userSurnameET);
        phoneET = findViewById(R.id.userPhoneET);
        afmET = findViewById(R.id.userAfmET);
        amkaET = findViewById(R.id.userAmkaET);
        idNumET = findViewById(R.id.userIdNumET);

        nameET.setText(userName);
        surnameET.setText(userSurname);
        phoneET.setText(userPhone);
        afmET.setText(userAfm);
        amkaET.setText(userAmka);
        idNumET.setText(userIdNum);

    }



    public void saveInfo(View view) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(NAME_KEY,nameET.getText().toString());
        editor.putString(SURNAME_KEY,surnameET.getText().toString());
        editor.putString(PHONE_KEY,phoneET.getText().toString());
        editor.putString(AFM_KEY,afmET.getText().toString());
        editor.putString(AMKA_KEY,amkaET.getText().toString());
        editor.putString(IDNUM_KEY,idNumET.getText().toString());
        editor.apply();
        finish();
    }
}