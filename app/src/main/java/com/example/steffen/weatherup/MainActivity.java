package com.example.steffen.weatherup;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    String ENDPOINT = "http://api.openweathermap.org/data/2.5";
    TextView tv_city_result, tv_temp_result, tv_weather_desc_result, tv_wind_speed_result, tv_humidity_result;
    Button btn_get;
    EditText et_zip;
    WeatherService weatherservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_city_result = (TextView) findViewById(R.id.tv_city_result);
        tv_temp_result = (TextView) findViewById(R.id.tv_temp_result);
        tv_weather_desc_result = (TextView) findViewById(R.id.tv_weather_desc_result);
        tv_wind_speed_result = (TextView) findViewById(R.id.tv_wind_speed_result);
        tv_humidity_result = (TextView) findViewById(R.id.tv_humidity_result);

        btn_get = (Button) findViewById(R.id.btn_get);
        et_zip = (EditText) findViewById(R.id.et_zip);


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        weatherservice = restAdapter.create(WeatherService.class);

        btn_get.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String zip = et_zip.getText().toString();
                getWeatherZip(zip + ",de", "metric");

            }

        });










    }


    public void getWeatherZip(String zip, String units){
        weatherservice.getWeatherZip(zip, units, new Callback<WeatherObject>() {

            @Override
            public void success(WeatherObject wo, Response response) {
                Log.i("Gefunden", "True");
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

            @Override
            public void failure(RetrofitError error) {
                Log.e("Gefunden", "Nein: " + error);
            }
        });
    }




    @Override
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
    }
}
