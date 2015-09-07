package com.example.steffen.weatherup;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.special.ResideMenu.ResideMenu;

/**
 * Created by Steffen on 27.07.2015.
 */
public class AutoSave_Class extends ActionBarActivity {

    Button btn_start, btn_stop, btn_show, btn_reset;
    CheckBox cb_running;
    Context c;
    static SharedPreferences prefs;
    ResideMenu resideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_layout);

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_show = (Button) findViewById(R.id.btn_show);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        cb_running = (CheckBox) findViewById(R.id.cb_running);
        c = this;

        resideMenu = ResideMenus_Class.getMainMenu(c, this);

        prefs = c.getSharedPreferences(
                "Share", Context.MODE_PRIVATE);

        refreshCeckBox();


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startIntent("StartSaveService");
                SaveReciever.prefs.edit().putBoolean("ServiceEnabled", true).apply();
                refreshCeckBox();



            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startIntent("StopSaveService");
                SaveReciever.prefs.edit().putBoolean("ServiceEnabled", false).apply();
                refreshCeckBox();

            }
        });

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, ShowServiceCities_Class.class);
                c.startActivity(intent);
            }
        });

       /* btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().putString("ServiceCities", "").apply();
            }
        });*/


    }

    public void refreshCeckBox(){
        cb_running.setChecked(prefs.getBoolean("AlarmSet", false));
    }

    public void startIntent(String s){
        SaveReciever sat = new SaveReciever();
        Intent intent = new Intent();
        intent.setAction(s);
        sat.onReceive(c, intent);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }



}
