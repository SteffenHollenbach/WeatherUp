package com.example.steffen.weatherup;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.special.ResideMenu.ResideMenu;

/**
 * Created by Steffen on 06.09.2015.
 */
public class MoveTest_Class extends AppCompatActivity {

    static Context c;
    TextView xy;
    ResideMenu resideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        c = this;

        resideMenu = ResideMenus_Class.getTestMenu(c, this);


    }
}
