package com.example.steffen.weatherup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
    Context c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_source_layout);

        mListView = (ListView) findViewById(R.id.listview);
        c = this;

        loadData();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                saved);

        mListView.setAdapter(arrayAdapter);

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

                Toast.makeText(MainActivity.c, "Eintrag '"+s+"' entfernt", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(c, History_Class.class);
                c.startActivity(intent);

                return true;
            }
        });

    }

    public void loadData() {
        Realm realm = Realm.getInstance(this);
        RealmResults<RetrofitToRealmAdapter> query = realm.where(RetrofitToRealmAdapter.class)
                .findAll();
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

    public void add(WeatherObject wo) {
        this.add(wo, MainActivity.c);
    }

    public void add(WeatherObject wo, Context c) {
        Realm realm = Realm.getInstance(c);
        realm.beginTransaction();
        RetrofitToRealmAdapter woA = realm.createObject(RetrofitToRealmAdapter.class);

        SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

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
        woA.setName(wo.getName());
        woA.setCod(wo.getCod());

        woA.setDate(date.format(new Date()));
        woA.setTime(time.format(new Date()));


        realm.commitTransaction();
        woList.add(woA);
        //mAdapter.notifyDataSetChanged();
        Toast.makeText(c, "weater-data saved", Toast.LENGTH_SHORT).show();
    }




}