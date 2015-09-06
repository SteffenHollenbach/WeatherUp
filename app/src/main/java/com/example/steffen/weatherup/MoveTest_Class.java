package com.example.steffen.weatherup;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by Steffen on 06.09.2015.
 */
public class MoveTest_Class extends ActionBarActivity {

    static Context c;
    TextView xy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_layout);

        c = this;



        xy = (TextView)findViewById(R.id.tv_city);
    }
}
