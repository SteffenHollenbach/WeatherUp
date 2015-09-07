package com.example.steffen.weatherup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Steffen on 05.09.2015.
 */
public class Download_Class extends ActionBarActivity {

    static Context c;
    SharedPreferences prefs;
    ListView lv_source;
    String[] sourcesFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_source_layout);

        c = this;

        setTitle("Select data to download:");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        lv_source = (ListView) findViewById(R.id.listview);
        final String[] sources = getCitiesFromServer();

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < sources.length; ++i) {
            list.add(sources[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        lv_source.setAdapter(adapter);

        lv_source.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                Intent intent = new Intent(c,Download_CityEntries_Class.class);
                intent.putExtra("position", (int)position);
                intent.putExtra("cityNameClear", sources[position]);
                Bundle b = new Bundle();
                b.putStringArray("sourcesFull", sourcesFull);
                intent.putExtras(b);
                c.startActivity(intent);

            }

        });

    }

    public String[] getCitiesFromServer(){
        String [] result = null;
        StringBuilder total = new StringBuilder();

        try {
            URL url = new URL("http://steffen-dell.khicprtogzhehhpq.myfritz.net:18188/getAllCities.php"); //Skript ausführen
            HttpURLConnection mUrlConnection;
            mUrlConnection = (HttpURLConnection) url.openConnection();
            mUrlConnection.setDoInput(true);
            InputStream is = new BufferedInputStream(mUrlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = r.readLine()) != null) { //Ergebnis einlesen
                total.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            result = total.toString().split(";");
            sourcesFull = total.toString().split(";");

            for (int i = 0; i < result.length; i++) {
                result[i] = result[i].substring(0, result[i].indexOf('_'));
            }
        } catch (Exception e){

            createDialog("Error","Error contacting server or no data on server",0);
            //finish();
        }

        return result;
    }

    void createDialog(String title, String text, final int afterClick){ // 0 = finish(), 1 = close dialog

        new AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(text)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (afterClick == 0) {
                            finish();
                        } else if (afterClick == 1) {

                        }

                    }
                }).create().show();
    }





}
