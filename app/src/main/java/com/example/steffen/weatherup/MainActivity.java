package com.example.steffen.weatherup;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    String ENDPOINT = "http://api.openweathermap.org/data/2.5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        WeatherService weatherservice = restAdapter.create(WeatherService.class);

        weatherservice.getWeatherZip("55435,de", "metric", new Callback<WeatherObject>() {

            @Override
            public void success(WeatherObject wo, Response response) {
                Log.i("Gefunden", "True");
                Log.i("Ergebnis", wo.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Gefunden", "Nein: " + error);
            }
        });

        /*weatherservice.getWeathersZip("55435,de", new Callback<List<WeatherObject>>() {

            @Override
            public void success(List<WeatherObject> weatherObjects, Response response) {
                Log.i("Gefunden", "True");
                Log.i("Ergebnis", weatherObjects.get(0).toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Gefunden", "Nein: " + error);
            }
        });*/




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
