package com.example.marcin.listazakupow;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.valueOf;

public class MainActivity extends ListActivity {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Context context;
    ListAdapter adapter;

    private String[] nazwaProduktu = new String[50];
    private double[] cenaProduktu = new double[50];
    private String[] opisProduktu = new String[50];
    private int licznikProduktow = 0;

    // wezel rodzica
    private final String KEY_RECORD = "record";

    private final String KEY_ID = "ProductID";
    private final String KEY_DESCRIPTION = "Description";
    private final String KEY_PRICE = "Price";
    private final String KEY_CATEGORY = "Category";


    private final String KEY_OPIS = "opis";

    private String url = "http://v-ie.uek.krakow.pl/~s185868/produktyy.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPref.edit();


        new ReadXMLTask().execute(url);
    }

    private class ReadXMLTask extends AsyncTask<String, Void, String> {


        // co sie dzieje przed pobieraniem danych
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        // wątek pobierania danych
        @Override
        protected String doInBackground(String... urls) {
            String response = "";

            try{
                URL url = new URL(urls[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.connect();

                InputStream is = conn.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String s = "";

                while ((s = br.readLine()) != null){
                    response += s;
                }
            } catch(Exception e){
                e.printStackTrace();
            }

            return response;
        }


        // co się dzieje po pobraniu danych
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            XMLParser parser = new XMLParser();

            Document doc = parser.getDomElement(result);

            ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

            NodeList nl = doc.getElementsByTagName(KEY_RECORD);

            for(int i = 0; i< nl.getLength(); i++){
                HashMap<String, String> map = new HashMap<String, String>();

                Element e = (Element) nl.item(i);

                map.put(KEY_DESCRIPTION, parser.getValue(e, KEY_DESCRIPTION));
                map.put(KEY_OPIS, "Numer id: " + parser.getValue(e, KEY_ID) +
                "\nCena: " + parser.getValue(e, KEY_PRICE) + "\nKategoria: " + parser.getValue(e, KEY_CATEGORY));
                map.put(KEY_PRICE, parser.getValue(e, KEY_PRICE));


                // ZAPIS DO ZMIENNYCH
//                nazwaProduktu[i] = parser.getValue(e, KEY_DESCRIPTION);
//                String zmiennaPomocnicza = parser.getValue(e, KEY_PRICE);
//                cenaProduktu[i] = Double.parseDouble(zmiennaPomocnicza);
//                idProduktu[i] = parser.getValue(e, KEY_ID);

                menuItems.add(map);
            }

//            Log.i("Nazwa", nazwaProduktu[0]);
//            Log.i("Cena", valueOf(cenaProduktu[0]));
//            Log.i("id", idProduktu[0]);
;

            adapter = new SimpleAdapter(MainActivity.this, menuItems, R.layout.list_item,
                    new String[] { KEY_DESCRIPTION, KEY_OPIS, KEY_PRICE }, new int[] {R.id.przedmiot, R.id.opis, R.id.cena});

            MainActivity.this.setListAdapter(adapter);
        }
    }

    public void myClickHandler(View v){
        ListView lvItems = getListView();
        // to do poszczególnych elementów
//        for (int i=0; i < lvItems.getChildCount(); i++)
//        {
//            lvItems.getChildAt(i).setBackgroundColor(Color.BLUE);
//        }

        ConstraintLayout vwParentRow = (ConstraintLayout)v.getParent();

        TextView nazwaProduktuTV = (TextView)vwParentRow.getChildAt(0);
        TextView opisProduktuTV = (TextView)vwParentRow.getChildAt(1);
        TextView iloscProduktu = (TextView)vwParentRow.getChildAt(4);
        nazwaProduktu[licznikProduktow] = nazwaProduktuTV.getText().toString();
        opisProduktu[licznikProduktow] = opisProduktuTV.getText().toString();
        String iloscET = iloscProduktu.getText().toString();
        int value= Integer.parseInt(iloscET);
        value ++;
        iloscProduktu.setText(valueOf(value));
//        child2.setText(nazwa);
//        child.setText(dane);

        int c = Color.CYAN;

        //vwParentRow.setBackgroundColor(c);
        vwParentRow.refreshDrawableState();
    }
}
