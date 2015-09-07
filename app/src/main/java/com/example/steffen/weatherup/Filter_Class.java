package com.example.steffen.weatherup;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by Steffen on 29.07.2015.
 */
public class Filter_Class extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Context c;
    Button btn_go, btn_reset;
    EditText et_city, et_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_layout);

        c = this;
        btn_go = (Button) findViewById(R.id.btn_go);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        et_city = (EditText) findViewById(R.id.et_city);
        et_date = (EditText) findViewById(R.id.et_date);

        et_city.setText(History_Class.prefs.getString("CityFilter", ""));
        et_date.setText(History_Class.prefs.getString("DateFilter", ""));

        Calendar now = Calendar.getInstance();
        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });



        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History_Class.prefs.edit().putString("CityFilter", et_city.getText().toString().replace(" ", "").replace("-", "").replace("ü", "ue").replace("ä", "ae").replace("ö", "oe").replace("Ü", "Ue").replace("Ä", "Ae").replace("Ö", "Oe").replace("ß", "ss")).apply();
                History_Class.prefs.edit().putString("DateFilter", et_date.getText().toString().replace(" ","")).apply();

                finish();
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History_Class.prefs.edit().putString("CityFilter", "").apply();
                History_Class.prefs.edit().putString("DateFilter", "").apply();

                finish();
            }
        });


    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String day = dayOfMonth+"";
        String month = ((int)(++monthOfYear))+"";

        Log.e("vvvvv",month);

        if (dayOfMonth < 10){
            day = "0" + dayOfMonth;
        }
        if (monthOfYear < 10){
            month = "0" + monthOfYear;
        }
        String date = day+"."+month+"."+year;
        et_date.setText(date);
    }
}
