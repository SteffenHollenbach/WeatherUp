package com.example.steffen.weatherup;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by Steffen on 26.07.2015.
 */
public class ShowResult_Class extends ActionBarActivity {

    TextView tv_city_result, tv_temp_result, tv_weather_desc_result, tv_wind_speed_result, tv_humidity_result;
    WeatherObject wo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout_basic);

        wo = (WeatherObject) getIntent().getSerializableExtra("WeaterObject");

        tv_city_result = (TextView) findViewById(R.id.tv_city_result);
        tv_temp_result = (TextView) findViewById(R.id.tv_temp_result);
        tv_weather_desc_result = (TextView) findViewById(R.id.tv_weather_desc_result);
        tv_wind_speed_result = (TextView) findViewById(R.id.tv_wind_speed_result);
        tv_humidity_result = (TextView) findViewById(R.id.tv_humidity_result);

        if(wo.cod.equals("200")) {
            tv_city_result.setText(wo.name);
            tv_temp_result.setText(wo.main.temp + " °C");
            tv_weather_desc_result.setText(wo.weather.get(0).description);
            tv_wind_speed_result.setText(wo.wind.speed + " km/h");
            tv_humidity_result.setText(wo.main.humidity + " %");
        } else {
            tv_city_result.setText("Fehler");
        }

    }
}
