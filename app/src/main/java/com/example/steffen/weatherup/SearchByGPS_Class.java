package com.example.steffen.weatherup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.victor.loading.newton.NewtonCradleLoading;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Steffen on 26.07.2015.
 */
public class SearchByGPS_Class extends AppCompatActivity {

    final String ENDPOINT = "http://api.openweathermap.org/data/2.5";
    WeatherService weatherservice;
    Context c;
    String provider;

    LocationManager locationManager;
    LocationListener locationListener;
    double aktPos[];
    String lat, lon;
    Boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_gps_layout);

        NewtonCradleLoading newton = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);
        newton.start();

        c = this;
        aktPos = new double[2];
        lat = "0.0";
        lon = "0.0";


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        weatherservice = restAdapter.create(WeatherService.class);


        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                aktPos[0] = location.getLatitude();
                aktPos[1] = location.getLongitude();

                lat = location.getLatitude() + "";
                lon = location.getLongitude() + "";

                Log.e("Location", lat + ", " + lon);
                if (first) {
                    first = false;
                    getWeatherGPS(lat, lon, "metric");
                }
            }

            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };


        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }


    public void getWeatherGPS(String lat, String lon, String units) {

        locationManager.removeUpdates(locationListener);
        locationManager = null;

        weatherservice.getWeatherGPS(lat, lon, units, new Callback<WeatherObject>() {

            @Override
            public void success(WeatherObject wo, Response response) {
                Log.i("Gefunden", "True");

                Intent intent = new Intent(c, ShowResult_Class.class);
                intent.putExtra("WeaterObject", wo);
                c.startActivity(intent);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Gefunden", "Nein: " + error);

                new AlertDialog.Builder(c)
                        .setTitle("Error")
                        .setMessage("Can't connect to weather service. Please make sure you have a working internet connection.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setIcon(R.drawable.error).show();


            }
        });
    }
}
