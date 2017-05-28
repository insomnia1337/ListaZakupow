package com.example.marcin.listazakupow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class EkranStartowy extends AppCompatActivity {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    boolean alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ekran_startowy);

        sharedPref = getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        alert = sharedPref.getBoolean("alert", true);
    }

    public void przejdzDoMenu(View view) {
        Intent intent = new Intent(this, MenuGlowne.class);
        startActivity(intent);

    }
}
