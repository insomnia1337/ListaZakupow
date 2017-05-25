package com.example.marcin.listazakupow;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.solver.SolverVariable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class TwojaLista extends AppCompatActivity {

    Context context;
    SharedPreferences sharedPref= PreferenceManager.getDefaultSharedPreferences(context);
    Gson gson;
    String arrayListString;
    ArrayList<HashMap<String, String>> map;
    String mapListString;
    ListAdapter adapter;

    private final String KEY_DESCRIPTION = "Description";
    private final String KEY_OPIS = "opis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twoja_lista);
//        context = getApplicationContext();
//        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
//        gson = new Gson();
//        arrayListString = sharedPref.getString("key", null);
//        mapListString = sharedPref.getString("key");
//        map = gson.fromJson(mapListString, MainActivity.class);
//        adapter = new SimpleAdapter(MainActivity.this, map, R.layout.list_item,
//                new String[] { KEY_DESCRIPTION, KEY_OPIS }, new int[] {R.id.przedmiot, R.id.opis});
    }




    //setListAdapter(adapter);
}
