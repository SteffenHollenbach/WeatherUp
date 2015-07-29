package com.example.steffen.weatherup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Steffen on 27.07.2015.
 */
public class SaveService extends Service {

    final String ENDPOINT = "http://api.openweathermap.org/data/2.5";
    WeatherService weatherservice;
    Context c;


    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        Log.v("WhatTheDroidService", System.currentTimeMillis()
                + ": WhatTheDroidService erstellt.");

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        weatherservice = restAdapter.create(WeatherService.class);
        c = this;


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("WhatTheDroidService", System.currentTimeMillis()
                + ": WhatTheDroidService gestartet.");

        // Unsere auszufuehrende Methode.


        getWeatherID("2922309", "metric");


        // Nachdem unsere Methode abgearbeitet wurde, soll sich der Service
        // selbst stoppen.
        stopSelf();

        // Um den Service laufen zu lassen, bis er explizit gestoppt wird,
        // geben wir "START_STICKY" zurueck.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.v("WhatTheDroidService", System.currentTimeMillis()
                + ": WhatTheDroidService zerstoert.");
    }

    public void getWeatherID(String id, String units){
        weatherservice.getWeatherID(id, units, new Callback<WeatherObject>() {

            @Override
            public void success(WeatherObject wo, Response response) {
                Log.i("Gefunden", "True");

                wo = WeatherObject.checkNull(wo);
                new History_Class().add(wo, c);
            }

            @Override
            public void failure(RetrofitError error) {

                Intent saveServiceIntent = new Intent(c, SaveService.class);
                PendingIntent saveServicePendingIntent =
                        PendingIntent.getService(c, 0, saveServiceIntent, 0);
                AlarmManager am = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);

                //Wie gross soll der Intervall sein?
                long interval = DateUtils.MINUTE_IN_MILLIS * 5; // Alle 5 Minuten

                am.set(AlarmManager.RTC, System.currentTimeMillis()+interval, saveServicePendingIntent);
                Toast.makeText(c, "Can't save weater-data, retry in 5 minutes", Toast.LENGTH_SHORT).show();

                Log.e("Gefunden", "Nein: " + error);
            }
        });
    }
}
