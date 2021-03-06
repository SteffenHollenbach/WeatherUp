package com.example.steffen.weatherup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Steffen on 08.09.2015.
 */
public class Download_Class_Stage2 extends AppCompatActivity {

    static Context c;
    SharedPreferences prefs;
    private ListView lv_source;
    private String[] sourcesFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_source_layout);

        c = this;

        setTitle("Select data to download:");

        Bundle b = this.getIntent().getExtras();
        final String[] sources = b.getStringArray("sources");
        sourcesFull = b.getStringArray("sourcesFull");


        lv_source = (ListView) findViewById(R.id.listview);

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

                Intent intent = new Intent(c, Download_CityEntries_Class.class);
                intent.putExtra("position", position);
                intent.putExtra("cityNameClear", sources[position]);
                Bundle b = new Bundle();
                b.putStringArray("sourcesFull", sourcesFull);
                intent.putExtras(b);
                c.startActivity(intent);

            }

        });

    }


}

