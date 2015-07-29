package com.example.steffen.weatherup;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Steffen on 29.07.2015.
 */
public class Filter_Class extends ActionBarActivity {

    Context c;
    Button btn_go;
    EditText et_city, et_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_layout);

        c = this;
        btn_go = (Button) findViewById(R.id.btn_go);
        et_city = (EditText) findViewById(R.id.et_city);
        et_date = (EditText) findViewById(R.id.et_date);

        et_city.setText(History_Class.prefs.getString("CityFilter", ""));
        et_date.setText(History_Class.prefs.getString("DateFilter", ""));


        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History_Class.prefs.edit().putString("CityFilter", et_city.getText().toString()).apply();
                History_Class.prefs.edit().putString("DateFilter", et_date.getText().toString().replace(" ","")).apply();

                finish();
            }
        });
    }


}
