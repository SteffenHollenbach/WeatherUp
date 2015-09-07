package com.example.steffen.weatherup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
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
public class Download_CityEntries_Class extends AppCompatActivity {

    static Context c;
    int position;
    ListView lv_source;
    String[] sources, sourcesFull;
    String cityNameClear, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_source_layout);

        c = this;

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        cityNameClear = intent.getStringExtra("cityNameClear");
        Bundle b = this.getIntent().getExtras();
        sourcesFull = b.getStringArray("sourcesFull");

        setTitle("Available data for " + cityNameClear + ":");


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        lv_source = (ListView) findViewById(R.id.listview);
        sources = getCityEntriesFromServer(position);
        tableName = sourcesFull[position];

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < sources.length; ++i) {
            list.add(sources[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.white_list_item_text, list);
        lv_source.setAdapter(adapter);

        lv_source.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                Intent intent = new Intent(c,Show_SingleServerEntry_Class.class);

                String[] temp = sources[position].split(" ");
                String date = temp[0].substring(0, temp[0].length() - 1);
                String time = temp[1];//.substring(0, temp[1].length() - 4);

                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("cityNameClear", cityNameClear);
                intent.putExtra("tableName", tableName);

                c.startActivity(intent);

            }

        });

    }

    public String[] getCityEntriesFromServer(int position){
        String [] result;
        StringBuilder total = new StringBuilder();

        try {
            URL url = new URL("http://steffen-dell.khicprtogzhehhpq.myfritz.net:18188/getCityEntries.php?CityName="+sourcesFull[position]); //Skript ausführen
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

        result = total.toString().split(";");

        return result;
    }
}
