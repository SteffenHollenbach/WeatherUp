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

import com.special.ResideMenu.ResideMenu;

import java.util.ArrayList;

/**
 * Created by Steffen on 07.09.2015.
 */
public class StoredSource_Class extends ActionBarActivity {

    static Context c;
    ResideMenu resideMenu;
    ListView lv_source;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_source_layout);

        c = this;

        resideMenu = ResideMenus_Class.getMainMenu(c, this);

        prefs = c.getSharedPreferences(
                "Share", Context.MODE_PRIVATE);

        setTitle("Select Stored-Source");

        lv_source = (ListView) findViewById(R.id.listview);
        String[] sources = new String[] {"Local", "Server", "Graph"};

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

                if (position == 0){
                    Intent intent = new Intent(c, History_Class.class);
                    c.startActivity(intent);
                }else if (position == 1){
                    Intent intent = new Intent(c,Download_Class.class);
                    c.startActivity(intent);
                }else if (position == 2){
                    Intent intent = new Intent(c,GraphDrawer_Class.class);
                    intent.putExtra("cityFilter", "");
                    intent.putExtra("dateFilter", "");
                    c.startActivity(intent);
                }else {
                    Toast.makeText(c, "If you see this something went wrong", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }
}



