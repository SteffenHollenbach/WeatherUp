package com.example.steffen.weatherup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

/**
 * Created by Steffen on 27.07.2015.
 */
public class AutoSave_Class extends Activity{

    Button btn_start, btn_stop;
    CheckBox cb_running;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_layout);

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        cb_running = (CheckBox) findViewById(R.id.cb_running);
        c = this;

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




    }

    public void refreshCeckBox(){
        cb_running.setChecked(SaveReciever.prefs.getBoolean("AlarmSet", false));
    }

    public void startIntent(String s){
        SaveReciever sat = new SaveReciever();
        Intent intent = new Intent();
        intent.setAction(s);
        sat.onReceive(c, intent);
    }



}
