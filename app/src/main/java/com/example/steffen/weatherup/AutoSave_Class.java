package com.example.steffen.weatherup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Steffen on 27.07.2015.
 */
public class AutoSave_Class extends Activity{

    Button btn_start;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_layout);

        btn_start = (Button) findViewById(R.id.btn_start);
        c = this;

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveReciever sat = new SaveReciever();
                Intent intent = new Intent();
                intent.setAction("StartSaveService");
                sat.onReceive(c, intent);

            }
        });


    }



}
