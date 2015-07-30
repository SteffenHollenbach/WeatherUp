package com.example.steffen.weatherup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Steffen on 26.07.2015.
 */
public class ShowResult_Class extends ActionBarActivity {

    TextView tv_city_result, tv_temp_result, tv_weather_desc_result, tv_wind_speed_result, tv_humidity_result, tv_pressure_result, tv_visibility_result;
    Button btn_save, btn_service, btn_view;
    TableRow t6, t7;
    WeatherObject wo;
    Context c;
    Boolean basic = true;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout_extended);

        c = this;
        wo = (WeatherObject) getIntent().getSerializableExtra("WeaterObject");

        prefs = c.getSharedPreferences(
                "Share", Context.MODE_PRIVATE);

        tv_city_result = (TextView) findViewById(R.id.tv_city_result);
        tv_temp_result = (TextView) findViewById(R.id.tv_temp_result);
        tv_weather_desc_result = (TextView) findViewById(R.id.tv_weather_desc_result);
        tv_wind_speed_result = (TextView) findViewById(R.id.tv_wind_speed_result);
        tv_humidity_result = (TextView) findViewById(R.id.tv_humidity_result);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_view = (Button) findViewById(R.id.btn_view);
        btn_service = (Button) findViewById(R.id.btn_service);

        if(wo.getCod().equals("200")) {
            wo = WeatherObject.checkNull(wo);

            tv_city_result.setText(wo.getName());
            tv_temp_result.setText(wo.getMain().getTemp() + " °C");
            tv_weather_desc_result.setText(wo.getWeather().get(0).getDescription());
            tv_wind_speed_result.setText(wo.getWind().getSpeed() + " km/h");
            tv_humidity_result.setText(wo.getMain().getHumidity() + " m");
        } else {
           tv_city_result.setText("Fehler");
           btn_save.setEnabled(false);
           btn_service.setEnabled(false);
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new History_Class().add(wo);
            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(basic && wo.getCod().equals("200")){
                    basic = false;

                    t6 = (TableRow) findViewById(R.id.tableRow6);
                    t7 = (TableRow) findViewById(R.id.tableRow7);

                    t6.setVisibility(View.VISIBLE);
                    t7.setVisibility(View.VISIBLE);

                    tv_pressure_result = (TextView) findViewById(R.id.tv_pressure_result);
                    tv_visibility_result = (TextView) findViewById(R.id.tv_visibility_result);

                    tv_pressure_result.setText(wo.getMain().getPressure() + " hPa");
                    tv_visibility_result.setText(wo.getVisibility() + " % ");

                    Toast.makeText(c, "Extended",Toast.LENGTH_SHORT).show();

                }else if(basic == false){
                    basic = true;

                    t6.setVisibility(View.GONE);
                    t7.setVisibility(View.GONE);

                    Toast.makeText(c, "Basic",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wo.getCod().equals("200")){
                    String s = prefs.getString("ServiceCities", "");
                    s = s.replace(wo.getId()+",", "");
                    prefs.edit().putString("ServiceCities", s + wo.getId() + ",").apply();
                    prefs.edit().putString(wo.getId()+"", wo.getName()).apply();
                }
            }
        });

    }
}
