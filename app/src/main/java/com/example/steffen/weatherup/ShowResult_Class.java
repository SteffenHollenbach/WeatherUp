package com.example.steffen.weatherup;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Steffen on 26.07.2015.
 */
public class ShowResult_Class extends ActionBarActivity {

    TextView tv_city_result, tv_temp_result, tv_weather_desc_result, tv_wind_speed_result, tv_humidity_result;
    Button btn_save, btn_refresh, btn_view;
    WeatherObject wo;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout_basic);

        c = this;
        wo = (WeatherObject) getIntent().getSerializableExtra("WeaterObject");
        wo = WeatherObject.checkNull(wo);

        tv_city_result = (TextView) findViewById(R.id.tv_city_result);
        tv_temp_result = (TextView) findViewById(R.id.tv_temp_result);
        tv_weather_desc_result = (TextView) findViewById(R.id.tv_weather_desc_result);
        tv_wind_speed_result = (TextView) findViewById(R.id.tv_wind_speed_result);
        tv_humidity_result = (TextView) findViewById(R.id.tv_humidity_result);

        btn_save = (Button) findViewById(R.id.btn_save);

        if(wo.getCod().equals("200")) {
            tv_city_result.setText(wo.getName());
            tv_temp_result.setText(wo.getMain().getTemp() + " °C");
            tv_weather_desc_result.setText(wo.getWeather().get(0).getDescription());
            tv_wind_speed_result.setText(wo.getWind().getSpeed() + " km/h");
            tv_humidity_result.setText(wo.getMain().getHumidity() + " %");
        } else {
           tv_city_result.setText("Fehler");
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new History_Class().add(wo);
            }
        });

    }
}
