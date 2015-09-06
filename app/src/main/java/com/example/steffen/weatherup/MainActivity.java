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


public class MainActivity extends ActionBarActivity {


    ListView lv_source;
    static Context c;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_source_layout);

        c = this;

        prefs = c.getSharedPreferences(
                "Share", Context.MODE_PRIVATE);

        setTitle("Select Source");

        lv_source = (ListView) findViewById(R.id.listview);
        String[] sources = new String[] {"GPS", "Zip-Code", "ID", "Name", "History", "Service-Settings", "Graph", "Server"};

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
                    Intent intent = new Intent(c, SearchByGPS_Class.class);
                    c.startActivity(intent);
                }else if (position == 1){
                    Intent intent = new Intent(c, SearchByZip_Class.class);
                    c.startActivity(intent);
                }else if (position == 2){
                    Intent intent = new Intent(c, SearchByID_Class.class);
                    c.startActivity(intent);
                }else if (position == 3){
                    Intent intent = new Intent(c, SearchByName_Class.class);
                    c.startActivity(intent);
                }else if (position == 4){
                    Intent intent = new Intent(c, History_Class.class);
                    c.startActivity(intent);
                }else if (position == 5){
                    Intent intent = new Intent(c, AutoSave_Class.class);
                    c.startActivity(intent);
                }else if (position == 6){
                    Intent intent = new Intent(c,GraphDrawer_Class.class);
                    intent.putExtra("cityFilter", "");
                    intent.putExtra("dateFilter", "");
                    c.startActivity(intent);
                }else if (position == 7){
                    Intent intent = new Intent(c,Download_Class.class);
                    c.startActivity(intent);
                }else {
                    Toast.makeText(c, "If you see this something went wrong", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
