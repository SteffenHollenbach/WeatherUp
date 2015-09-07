package com.example.steffen.weatherup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Steffen on 26.07.2015.
 */
public class SearchByID_Class extends AppCompatActivity {
    final String ENDPOINT = "http://api.openweathermap.org/data/2.5";
    Button btn_get;
    EditText et_id;
    WeatherService weatherservice;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_id_layout);

        c = this;

        btn_get = (Button) findViewById(R.id.btn_get);
        et_id = (EditText) findViewById(R.id.et_id);


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        weatherservice = restAdapter.create(WeatherService.class);

        btn_get.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String id = et_id.getText().toString();
                getWeatherID(id, "metric");

            }

        });


    }

    public void getWeatherID(String id, String units){
        weatherservice.getWeatherID(id, units, new Callback<WeatherObject>() {

            @Override
            public void success(WeatherObject wo, Response response) {
                Log.i("Gefunden", "True");

                Intent intent = new Intent(c, ShowResult_Class.class);
                intent.putExtra("WeaterObject", wo);
                c.startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Gefunden", "Nein: " + error);
            }
        });
    }
}
