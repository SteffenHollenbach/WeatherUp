package com.example.steffen.weatherup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.victor.loading.newton.NewtonCradleLoading;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Steffen on 05.09.2015.
 */
public class Download_Class extends AppCompatActivity {

    static Context c;
    SharedPreferences prefs;
    ListView lv_source;
    String[] sourcesFull;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_gps_layout);

        TextView tv = (TextView) findViewById(R.id.tv_waiting);
        tv.setText("Waiting for server");

        NewtonCradleLoading newton = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);
        newton.start();

        c = this;

        setTitle("Connect to server");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        lv_source = (ListView) findViewById(R.id.listview);

        Thread networkThread = new Thread() {
            public void run() {

                getCitiesFromServer();
            }
        };

        networkThread.start();

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                createDialog("Error", "Error contacting server or no data on server", 0);
            }
        };

    }

    public void getCitiesFromServer() {
        String[] result = null;
        StringBuilder total = new StringBuilder();

        try {
            URL url = new URL("http://steffen-dell.khicprtogzhehhpq.myfritz.net:18188/getAllCities.php"); //Skript ausführen
            HttpURLConnection mUrlConnection;
            mUrlConnection = (HttpURLConnection) url.openConnection();
            mUrlConnection.setDoInput(true);
            InputStream is = new BufferedInputStream(mUrlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = r.readLine()) != null) { //Ergebnis einlesen
                total.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            result = total.toString().split(";");
            sourcesFull = total.toString().split(";");

            for (int i = 0; i < result.length; i++) {
                result[i] = result[i].substring(0, result[i].indexOf('_'));
            }
        } catch (Exception e) {

            //createDialog("Error","Error contacting server or no data on server",0);
            Message message = mHandler.obtainMessage();
            message.sendToTarget();
        }

        Bundle b = new Bundle();
        b.putStringArray("sources", result);
        Intent i = new Intent(c, Download_Class_Stage2.class);
        i.putExtras(b);
        c.startActivity(i);

        //Log.e("*******************","HERE");


    }

    void createDialog(String title, String text, final int afterClick) { // 0 = finish(), 1 = close dialog

        new AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(text)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (afterClick == 0) {
                            finish();
                        } else if (afterClick == 1) {

                        }

                    }
                }).setIcon(R.drawable.error).show();
    }


}
