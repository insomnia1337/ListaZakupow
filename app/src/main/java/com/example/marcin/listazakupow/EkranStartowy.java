package com.example.marcin.listazakupow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class EkranStartowy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ekran_startowy);
    }

    public void przejdzDoMenu(View view) {
        Intent intent = new Intent(this, MenuGlowne.class);
        startActivity(intent);
    }
}
