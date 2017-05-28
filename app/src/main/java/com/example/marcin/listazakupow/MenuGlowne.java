package com.example.marcin.listazakupow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MenuGlowne extends AppCompatActivity {

    Context context;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    SharedPreferences sharedPref2;
    SharedPreferences.Editor editor2;

    MediaPlayer mediaPlayer;

    float sumaZakupow;
    int iloscProduktow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_glowne);

        context = getApplicationContext();
        sharedPref = this.getSharedPreferences("DANE", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        sharedPref2 = getSharedPreferences("STATYSTYKA", Context.MODE_PRIVATE);
        editor2 = sharedPref2.edit();

//        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
//
//        mediaPlayer.start();
//        mediaPlayer.setLooping(true);

    }




//    @Override
//    protected void onPause(){
//        mediaPlayer.stop();
//        mediaPlayer.release();
//
//    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void przejdzDoProdukty(View view) {
        if(!isOnline())
        {
            AlertDialog alertDialog = new AlertDialog.Builder(MenuGlowne.this).create();
            alertDialog.setTitle("Brak połączenia z Internetem.");
            alertDialog.setMessage("Aby pobrać listę produktów, wymagany jest dostęp do Internetu!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }


    public void idzDoAutorzy(View view) {
        Intent intent = new Intent(this, Autorzy.class);
        startActivity(intent);
    }

    public void idzDoTwojeListy(View view) {
        Intent intent = new Intent(this, TwojaLista.class);
        startActivity(intent);
    }

    public void wyczyscListy(View view) {
        sumaZakupow = sharedPref.getFloat("suma", 0);
        iloscProduktow = sharedPref.getInt("ilosc", 0);

        float sumaZakupow2 = sharedPref2.getFloat("suma", 0) + sumaZakupow;
        int iloscProduktow2 = sharedPref2.getInt("ilosc", 0) + iloscProduktow;
        editor2.putFloat("suma", sumaZakupow2);
        editor2.putInt("ilosc", iloscProduktow2);


        editor.clear();
        editor2.commit();
        editor.commit();

        {
            Context context = getApplicationContext();
            CharSequence text = "Lista zakupów została wyczyszczona.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void idzDoStatystyki(View view) {
        Intent intent = new Intent(this, Statystyka.class);
        startActivity(intent);
    }
}
