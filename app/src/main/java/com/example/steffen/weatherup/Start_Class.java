package com.example.steffen.weatherup;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.special.ResideMenu.ResideMenu;

/**
 * Created by Steffen on 07.09.2015.
 */
public class Start_Class extends Activity {

    static Context c;
    ResideMenu resideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);

        c = this;

        resideMenu = ResideMenus_Class.getMainMenu(c, this);

        TextView tv = (TextView) findViewById(R.id.tv_wu);

        Animation anim = AnimationUtils.loadAnimation(c,android.R.anim.slide_in_left);
        anim.setDuration(2000);

        tv.startAnimation(anim);


    }
}
