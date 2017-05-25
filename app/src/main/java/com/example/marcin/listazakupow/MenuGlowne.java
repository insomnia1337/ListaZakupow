package com.example.marcin.listazakupow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuGlowne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_glowne);
    }

    public void przejdzDoProdukty(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void idzDoAutorzy(View view) {
        Intent intent = new Intent(this, Autorzy.class);
        startActivity(intent);
    }
}
