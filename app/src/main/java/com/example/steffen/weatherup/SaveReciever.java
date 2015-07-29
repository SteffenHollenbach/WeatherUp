package com.example.steffen.weatherup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Steffen on 27.07.2015.
 */
public class SaveReciever extends BroadcastReceiver {

    static SharedPreferences prefs;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent saveServiceIntent = new Intent(context, SaveService.class);
        PendingIntent saveServicePendingIntent =
                PendingIntent.getService(context, 0, saveServiceIntent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        prefs = context.getSharedPreferences(
                "Share", Context.MODE_PRIVATE);

        if ((intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) && prefs.getBoolean("ServiceEnabled", false))|| intent.getAction().equals("StartSaveService")) {
            Log.v("WhatTheDroidService", " Boot komplett bzw. StartService recieved.");
            Toast.makeText(context, "Recieved (Start)", Toast.LENGTH_SHORT).show();

            //Wie gross soll der Intervall sein?
            long interval = DateUtils.SECOND_IN_MILLIS * 10; // Alle 10 Sekunden

            //Wann soll der Service das erste Mal gestartet werden?
            long firstStart = System.currentTimeMillis();

            //am.setRepeating(AlarmManager.RTC, firstStart, interval, saveServicePendingIntent);
            am.setRepeating(AlarmManager.RTC, firstStart, AlarmManager.INTERVAL_HOUR, saveServicePendingIntent);//AlarmManager.INTERVAL_HOUR
            prefs.edit().putBoolean("AlarmSet", true).apply();

            Log.v("WhatTheDroidService", "AlarmManager gesetzt");

        } else if (intent.getAction().equals("StopSaveService")){
            Toast.makeText(context, "Recieved (Stop)", Toast.LENGTH_SHORT).show();

            am.cancel(saveServicePendingIntent);
            prefs.edit().putBoolean("AlarmSet", false).apply();

            Log.v("WhatTheDroidService", "AlarmManager entfernt");
        }


    }
}
