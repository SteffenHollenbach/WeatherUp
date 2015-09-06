package com.example.steffen.weatherup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Steffen on 26.07.2015.
 */
public class History_Class extends ActionBarActivity {

    List<RetrofitToRealmAdapter> woList = new ArrayList<>();
    List<String> saved = new ArrayList<>();
    private ListView mListView;
    static SharedPreferences prefs;
    Context c;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);

        mListView = (ListView) findViewById(R.id.listview);
        c = this;

        prefs = c.getSharedPreferences(
                "Share", Context.MODE_PRIVATE);

        initialize();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                String s = mListView.getAdapter().getItem(position).toString();

                WeatherObject wo = new RealmToRetrofitAdapter(loadSingle(s)).createWeatherObject();

                Intent intent = new Intent(c, ShowResult_Class.class);
                intent.putExtra("WeaterObject", wo);
                startActivity(intent);
            }

        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String s = mListView.getAdapter().getItem(position).toString();
                RetrofitToRealmAdapter woToDelete = (loadSingle(s));

                Realm realm = Realm.getInstance(c);
                realm.beginTransaction();
                woToDelete.removeFromRealm();
                realm.commitTransaction();
                finish();

                Toast.makeText(MainActivity.c, "Data '"+s+"' removed", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(c, History_Class.class);
                c.startActivity(intent);

                return true;
            }
        });

    }

    public void loadData() {
        Realm realm = Realm.getInstance(this);
        RealmResults<RetrofitToRealmAdapter> query;

        String cityFilter = prefs.getString("CityFilter", "");
        String dateFilter = prefs.getString("DateFilter", "");
        if (cityFilter.equals("") && dateFilter.equals("")) {
            query = realm.where(RetrofitToRealmAdapter.class)
                    .findAll();
        }else if (!cityFilter.equals("") && dateFilter.equals("")){
            query = realm.where(RetrofitToRealmAdapter.class)
                    .equalTo("name", cityFilter)
                    .findAll();
        }else if (!dateFilter.equals("") && cityFilter.equals("")){
            query = realm.where(RetrofitToRealmAdapter.class)
                    .equalTo("date", dateFilter)
                    .findAll();
        }else{
            query = realm.where(RetrofitToRealmAdapter.class)
                    .equalTo("name", cityFilter)
                    .equalTo("date", dateFilter)
                    .findAll();
        }

        woList.clear();
        saved.clear();

        for (RetrofitToRealmAdapter woA : query) {
            woList.add(woA);
            saved.add(woA.getName() + "," + woA.getDate()+ "," + woA.getTime());
        }

        if (saved.size() == 0){
            Toast.makeText(MainActivity.c, "Kein Eintrag gefunden", Toast.LENGTH_LONG).show();
        }
    }

    public RetrofitToRealmAdapter loadSingle(String input) {
        String[] separated = input.split(",");
        Realm realm = Realm.getInstance(this);
        RealmResults<RetrofitToRealmAdapter> query = realm.where(RetrofitToRealmAdapter.class)
                .equalTo("name", separated[0])
                .equalTo("date", separated[1])
                .equalTo("time", separated[2])
                .findAll();

        //Toast.makeText(MainActivity.c, "Found " + loaded.size(), Toast.LENGTH_SHORT).show();
        return query.get(0);
    }

    public void add(WeatherObject wo, int status, String dateServer, String timeServer) {
        this.add(wo, MainActivity.c, status, dateServer, timeServer);
    }

    public void add(WeatherObject wo, Context c, int status, String dateServer, String timeServer) { //0 = aus Internet live, 1 = von Server
        Realm realm = Realm.getInstance(c);
        realm.beginTransaction();
        RetrofitToRealmAdapter woA = realm.createObject(RetrofitToRealmAdapter.class);

        String dateS = null, timeS = null;

        if (status == 0) {
            SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

            dateS = date.format(new Date());
            timeS = time.format(new Date());
        } else if (status == 1){
            dateS = dateServer;
            timeS = timeServer;
        }

        String primaryKey = wo.getId() + ";" + dateS + ";" + timeS;

        woA.setLon(wo.getCoord().getLon());
        woA.setLat(wo.getCoord().getLat());
        woA.setWeather_id(wo.getWeather().get(0).getId());
        woA.setMain(wo.getWeather().get(0).getMain());
        woA.setDescription(wo.getWeather().get(0).getDescription());
        woA.setIcon(wo.getWeather().get(0).getIcon());
        woA.setTemp(wo.getMain().getTemp());
        woA.setPressure(wo.getMain().getPressure());
        woA.setHumidity(wo.getMain().getHumidity());
        woA.setTemp_min(wo.getMain().getTemp_min());
        woA.setTemp_max(wo.getMain().getTemp_max());
        woA.setSpeed(wo.getWind().getSpeed());
        woA.setDeg(wo.getWind().getDeg());
        woA.setAll(wo.getClouds().getAll());
        woA.setType(wo.getSys().getType());
        woA.setSys_id(wo.getSys().getId());
        woA.setMessage(wo.getSys().getMessage());
        woA.setCountry(wo.getSys().getCountry());
        woA.setSunrise(wo.getSys().getSunset());
        woA.setBase(wo.getBase());
        woA.setVisibility(wo.getVisibility());
        woA.setDt(wo.getDt());
        woA.setId(wo.getId());
        woA.setName(wo.getName().replace(" ","").replace("-","").replace("ü","ue").replace("ä", "ae").replace("ö","oe").replace("Ü","Ue").replace("Ä","Ae").replace("Ö","Oe").replace("ß","ss"));
        woA.setCod(wo.getCod());

        woA.setDate(dateS);
        woA.setTime(timeS);
        woA.setPrimarykey(primaryKey);


        realm.commitTransaction();
        woList.add(woA);
        //mAdapter.notifyDataSetChanged();
        Toast.makeText(c, "weather-data saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter) {
            Intent intent = new Intent(c, Filter_Class.class);
            c.startActivity(intent);
        }else if (id == R.id.graph) {
            Intent intent = new Intent(c, GraphDrawer_Class.class);
            intent.putExtra("cityFilter", prefs.getString("CityFilter", ""));
            intent.putExtra("dateFilter", prefs.getString("DateFilter", ""));
            c.startActivity(intent);
        }else if (id == R.id.delete) {
            Realm realm = Realm.getInstance(c);
            realm.beginTransaction();
            realm.where(RetrofitToRealmAdapter.class).findAll().clear();
            realm.commitTransaction();
            Toast.makeText(c, "Database cleared", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(c, History_Class.class);
            c.startActivity(intent);
        }else if (id == R.id.upload) {
            Intent intent = new Intent(c, Upload_Class.class);
            intent.putExtra("cityFilter", prefs.getString("CityFilter", ""));
            intent.putExtra("dateFilter", prefs.getString("DateFilter", ""));
            c.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();

        initialize();

    }

    public void initialize(){
        loadData();

        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                saved);

        mListView.setAdapter(arrayAdapter);
    }





}