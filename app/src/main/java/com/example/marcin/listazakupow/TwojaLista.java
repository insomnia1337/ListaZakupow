package com.example.marcin.listazakupow;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.SolverVariable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.valueOf;

public class TwojaLista  extends AppCompatActivity  {

    float sumaZakupow;
    int iloscProduktow;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    SharedPreferences sharedPref2;
    SharedPreferences.Editor editor2;

    TextView textView;

    private String[] nazwaProduktu = new String[50];
    private String[] cenaTowarow = new String[50];
    private String[] opisProduktu = new String[50];
    private String[] cenaProduktuString = new String[50];

    ArrayList<HashMap<String,String>> arrayList;
    private ListView list;
    private BaseAdapter adapter ;

    java.text.DecimalFormat df;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twoja_lista);

        sharedPref = getSharedPreferences("DANE", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        sharedPref2 = getSharedPreferences("STATYSTYKA", Context.MODE_PRIVATE);
        editor2 = sharedPref2.edit();

        sumaZakupow = sharedPref.getFloat("suma", 0);
        iloscProduktow = sharedPref.getInt("ilosc", 0);

        df=new java.text.DecimalFormat();
        df.setMaximumFractionDigits(2); //dla df ustawiamy największą ilość miejsc po przecinku
        df.setMinimumFractionDigits(2);


        for(int i=0; i<=iloscProduktow; i++){
            nazwaProduktu[i] = sharedPref.getString("nazwaProduktu"+(i+1), null);
            cenaTowarow[i] = sharedPref.getString("cenaTowarow"+(i+1), null);
            //cenaProduktuString[i] = valueOf(cenaProduktu[i]);
            opisProduktu[i] = sharedPref.getString("opisProduktu"+(i+1), null);
        }

        arrayList=new ArrayList<>();
        for(int i=0; i<iloscProduktow; i++){
            HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put("nazwa",nazwaProduktu[i]);
            hashMap.put("cena",cenaTowarow[i]);
            hashMap.put("opis",opisProduktu[i]);
            arrayList.add(hashMap);//add the hashmap into arrayList
        }

        adapter = new SimpleAdapter(TwojaLista.this, arrayList, R.layout.list_item2,
                new String[] { "nazwa", "opis", "cena" }, new int[] {R.id.przedmiot, R.id.opis, R.id.cena});

        //list.setListAdapter(adapter);

        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);


        textView = (TextView) findViewById(R.id.textView15);
        textView.setText(valueOf(iloscProduktow));
        textView = (TextView) findViewById(R.id.textView17);
        textView.setText(valueOf(df.format(sumaZakupow)) + " zł");


    }


    public void checkBox(View v){
        //ListView lvItems = getListView();
        // to do poszczególnych elementów
//        for (int i=0; i < lvItems.getChildCount(); i++)
//        {
//            lvItems.getChildAt(i).setBackgroundColor(Color.BLUE);
//        }

        ConstraintLayout vwParentRow = (ConstraintLayout)v.getParent();

        // zmiana poszczególnych wartości TextView
        CheckBox checkBox = (CheckBox) vwParentRow.getChildAt(3);
        checkBox.setChecked(true);

        //vwParentRow.setBackgroundColor(c);
        vwParentRow.refreshDrawableState();

        {
            Context context = getApplicationContext();
            CharSequence text = "Produkt w koszyku!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    public void usun(View v){


        final int position = list.getPositionForView((View) v.getParent());
        arrayList.remove(position);
        adapter.notifyDataSetChanged();

        {
            Context context = getApplicationContext();
            CharSequence text = "Produkt usunięty";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        ConstraintLayout vwParentRow = (ConstraintLayout)v.getParent();
        TextView textView = (TextView) vwParentRow.getChildAt(2);
        String sumaString = textView.getText().toString();
        float cenaProduktu = Float.parseFloat(sumaString);

        sumaZakupow = sumaZakupow - cenaProduktu;
        iloscProduktow--;

        textView = (TextView) findViewById(R.id.textView15);
        textView.setText(valueOf(iloscProduktow));
        textView = (TextView) findViewById(R.id.textView17);
        textView.setText(valueOf(df.format(sumaZakupow)) + " zł");

        editor.remove("nazwaProduktu"+(position+1));
        editor.remove("cenaTowarow"+(position+1));
        editor.remove("opisProduktu"+(position+1));
        editor.putString("nazwaProduktu"+(position+1), "Produkt usunięty.");
        editor.putFloat("suma", sumaZakupow);
        editor.putInt("ilosc", iloscProduktow);
        editor.commit();
    }

    public void idzDoMenu(View view) {
        Intent intent = new Intent(this, MenuGlowne.class);
        startActivity(intent);
    }

//    private class ReceiverThread extends Thread {
//        @Override
//        public void run() {
//            TwojaLista.this.runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    BaseAdapter.notifyDataSetChanged();
//                }
//            });
//        }


    public void wyczysc(View view) {
        float sumaZakupow2 = sharedPref2.getFloat("suma", 0) + sumaZakupow;
        int iloscProduktow2 = sharedPref2.getInt("ilosc", 0) + iloscProduktow;

        editor2.putFloat("suma", sumaZakupow2);
        editor2.putInt("ilosc", iloscProduktow2);


        editor.clear();
        editor2.commit();
        editor.commit();

        finish();
        startActivity(getIntent());

    }
}
