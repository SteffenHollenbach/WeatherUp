package com.example.steffen.weatherup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steffen on 30.07.2015.
 */
public class ShowServiceCities_Class extends ActionBarActivity {

    Context c;
    SharedPreferences prefs;
    private ListView mListView;
    ArrayAdapter<String> arrayAdapter;
    List<String> saved = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_source_layout);

        c = this;
        prefs = c.getSharedPreferences(
                "Share", Context.MODE_PRIVATE);

        mListView = (ListView) findViewById(R.id.listview);

        String[] seperated = prefs.getString("ServiceCities", "").split(",");
        //Log.e("*******", seperated.length+"");

        //saved = Arrays.asList(seperated);
        for(int i = 0; i < seperated.length; i++){
            if (!seperated[i].equals("")){
                saved.add(prefs.getString(seperated[i], "Error") + " (" + seperated[i] + ")");
            }

        }

        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                saved);

        mListView.setAdapter(arrayAdapter);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String s = mListView.getAdapter().getItem(position).toString();
                s = s.substring(s.indexOf("(")+1, s.length()-1);

                String t = prefs.getString("ServiceCities", "");
                t = t.replace(s + ",", "");

                prefs.edit().putString("ServiceCities", t).apply();

                Toast.makeText(MainActivity.c, "City '" + s + "' removed", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(c, ShowServiceCities_Class.class);
                c.startActivity(intent);

                finish();

                return true;
            }
        });


        //TODO Jede Stadt nur einmal
    }
}