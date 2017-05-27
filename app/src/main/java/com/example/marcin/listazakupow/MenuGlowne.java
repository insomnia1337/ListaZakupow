package com.example.marcin.listazakupow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_glowne);

        context = getApplicationContext();
        sharedPref = this.getSharedPreferences("DANE", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

    }

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
        editor.clear();
        editor.commit();
    }
}
