package com.example.steffen.weatherup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.BounceInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.special.ResideMenu.ResideMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steffen on 30.07.2015.
 */
public class ShowServiceCities_Class extends AppCompatActivity {

    Context c;
    SharedPreferences prefs;
    private SwipeMenuListView mListView;
    ArrayAdapter<String> arrayAdapter;
    final List<String> saved = new ArrayList<String>();
    ResideMenu resideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_out_list);

        c = this;
        prefs = c.getSharedPreferences(
                "Share", Context.MODE_PRIVATE);

        resideMenu = ResideMenus_Class.getServiceCitiesMenu(c, this);
        // add gesture operation's ignored views
        SwipeMenuListView ignored_SwipeMenuListView = (SwipeMenuListView) findViewById(R.id.listview);
        resideMenu.addIgnoredView(ignored_SwipeMenuListView);


        mListView = (SwipeMenuListView) findViewById(R.id.listview);
        setSwipeListView();

        String[] seperated = prefs.getString("ServiceCities", "").split(",");
        //Log.e("*******", seperated.length+"");

        //saved = Arrays.asList(seperated);
        for (int i = 0; i < seperated.length; i++) {
            if (!seperated[i].equals("")) {
                saved.add(prefs.getString(seperated[i], "Error") + " (" + seperated[i] + ")");
            }

        }

        arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.white_list_item_text,
                saved);

        mListView.setAdapter(arrayAdapter);

        /*mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String s = mListView.getAdapter().getItem(position).toString();
                s = s.substring(s.indexOf("(")+1, s.length()-1);

                String t = prefs.getString("ServiceCities", "");
                t = t.replace(s + ",", "");

                prefs.edit().putString("ServiceCities", t).apply();

                Toast.makeText(MainActivity.c, "City '" + s + "' removed", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(c, ShowServiceCities_Class.class);
                c.startActivity(intent);

                finish();

                return true;
            }
        });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_swipe_left_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.swipeLeft) {
            resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setSwipeListView() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(150);
                // set a icon
                deleteItem.setIcon(R.drawable.trash_medium);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        String s = mListView.getAdapter().getItem(position).toString();
                        s = s.substring(s.indexOf("(") + 1, s.length() - 1);

                        String t = prefs.getString("ServiceCities", "");
                        t = t.replace(s + ",", "");

                        prefs.edit().putString("ServiceCities", t).apply();

                        Toast.makeText(c, "City '" + s + "' removed", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(c, ShowServiceCities_Class.class);
                        c.startActivity(intent);

                        finish();

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        // Close Interpolator
        mListView.setCloseInterpolator(new BounceInterpolator());
        // Open Interpolator
        mListView.setOpenInterpolator(new BounceInterpolator());
    }
}
