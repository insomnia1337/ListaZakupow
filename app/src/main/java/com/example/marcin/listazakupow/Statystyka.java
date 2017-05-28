package com.example.marcin.listazakupow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.String.valueOf;

public class Statystyka extends AppCompatActivity {

    SharedPreferences sharedPref2;
    SharedPreferences.Editor editor2;
    java.text.DecimalFormat df;
    float sumaZakupow;
    int iloscProduktow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statystyka);

        df=new java.text.DecimalFormat();
        df.setMaximumFractionDigits(2); //dla df ustawiamy największą ilość miejsc po przecinku
        df.setMinimumFractionDigits(2);

        sharedPref2 = getSharedPreferences("STATYSTYKA", Context.MODE_PRIVATE);
        editor2 = sharedPref2.edit();

        sumaZakupow = sharedPref2.getFloat("suma", 0);
        iloscProduktow = sharedPref2.getInt("ilosc", 0);

        TextView textView = (TextView) findViewById(R.id.textView19);
        textView.setText(valueOf(df.format(sumaZakupow)));

        textView = (TextView) findViewById(R.id.textView22);
        textView.setText(valueOf(iloscProduktow));

        textView = (TextView) findViewById(R.id.textView25);
        float zakupy = sumaZakupow/30;
        textView.setText(valueOf(df.format(zakupy)));
    }

    public void zerujStatystyki(View view) {
        editor2.clear();
        editor2.commit();

        {
            Context context = getApplicationContext();
            CharSequence text = "Statystyki zostały zresetowane.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        finish();
        startActivity(getIntent());
    }

    public void wrocDoMenu(View view) {
        Intent intent = new Intent(this, MenuGlowne.class);
        startActivity(intent);
    }
}
