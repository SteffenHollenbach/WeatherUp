package com.example.steffen.weatherup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steffen on 06.09.2015.
 */
public class Show_SingleServerEntry_Class extends ActionBarActivity {

    static Context c;
    String date, time, cityNameClear, tableName;
    TextView tv_city_result, tv_temp_result, tv_weather_desc_result, tv_wind_speed_result, tv_humidity_result, tv_time, tv_date;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_result_layout);

        c = this;

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        cityNameClear = intent.getStringExtra("cityNameClear");
        tableName = intent.getStringExtra("tableName");

        setTitle(cityNameClear);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        tv_city_result = (TextView) findViewById(R.id.tv_city_result);
        tv_temp_result = (TextView) findViewById(R.id.tv_temp_result);
        tv_weather_desc_result = (TextView) findViewById(R.id.tv_weather_desc_result);
        tv_wind_speed_result = (TextView) findViewById(R.id.tv_wind_speed_result);
        tv_humidity_result = (TextView) findViewById(R.id.tv_humidity_result);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_date = (TextView) findViewById(R.id.tv_date);

        btn_save = (Button) findViewById(R.id.btn_save);


        getSingleEntryFromServer(date, time, tableName);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WeatherObject.Coord coordTemp = new WeatherObject.Coord(null, null);

                List<WeatherObject.Weather> weatherListTemp = new ArrayList<WeatherObject.Weather>();
                WeatherObject.Weather weatherTemp = new WeatherObject.Weather(null, null, tv_weather_desc_result.getText().toString(), null);
                weatherListTemp.add(weatherTemp);

                WeatherObject.Main mainTemp = new WeatherObject.Main(tv_temp_result.getText().toString(), null,  null, null, null);

                WeatherObject.Wind windTemp = new WeatherObject.Wind(null, null);

                WeatherObject.Clouds cloudsTemp = new WeatherObject.Clouds(null);

                WeatherObject.Sys sysTemp = new WeatherObject.Sys(null, null, null, null, null, null);

                WeatherObject wo = WeatherObject.checkNull(new WeatherObject(coordTemp, weatherListTemp, mainTemp, windTemp, cloudsTemp, sysTemp, null, null, null, null, tv_city_result.getText().toString(), null));

                new History_Class().add(wo, 1, date, time);
            }
        });


    }

    public void getSingleEntryFromServer(String date, String time, String tableName){
        String [] result;
        StringBuilder total = new StringBuilder();

        Log.e("*********", "http://steffen-dell.khicprtogzhehhpq.myfritz.net:18188/getSingleEntry.php?TableName="+tableName+"&Date="+date+"&Time="+time);

        try {
            URL url = new URL("http://steffen-dell.khicprtogzhehhpq.myfritz.net:18188/getSingleEntry.php?TableName="+tableName+"&Date="+date+"&Time="+time); //Skript ausführen
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

        tv_city_result.setText(result[0]);
        tv_date.setText(result[2]);
        tv_time.setText(result[3]);
        tv_temp_result.setText(result[4]);
        tv_weather_desc_result.setText(result[5]);



    }
}


