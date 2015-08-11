package com.example.steffen.weatherup;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Steffen on 11.08.2015.
 */
public class Upload_Class extends Activity{

    List<RetrofitToRealmAdapter> woList = new ArrayList<>();
    //List<XYSeries> seriesList = new ArrayList<>();
    String dateFilter, cityFilter;
    TextView tv_status, tv_finish;
    ImageView iv_check;
    ProgressBar pb_upload;
    int error = 0, all= 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_layout);

        Intent intent = getIntent();
        cityFilter = intent.getStringExtra("cityFilter");
        dateFilter = intent.getStringExtra("dateFilter");

        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_finish = (TextView) findViewById(R.id.tv_finish);
        iv_check = (ImageView) findViewById(R.id.iv_check);
        pb_upload = (ProgressBar) findViewById(R.id.pb_upload);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //static UploadOperation UO = new UploadOperation();
        new UploadOperation().execute();

    }




    public void loadData(String cityFilter, String dateFilter) {
        Realm realm = Realm.getInstance(this);
        RealmResults<RetrofitToRealmAdapter> query;

        Log.e("******", "CityFilter: " + cityFilter);
        Log.e("******", "DateFilter: " + dateFilter);

        if (cityFilter.equals("") && dateFilter.equals("")) {
            query = realm.where(RetrofitToRealmAdapter.class)
                    .findAll();
        }else if (!cityFilter.equals("") && dateFilter.equals("")){
            query = realm.where(RetrofitToRealmAdapter.class)
                    .equalTo("name", cityFilter)
                    .findAll();
        }else if (!dateFilter.equals("") && cityFilter.equals("")){
            query = realm.where(RetrofitToRealmAdapter.class)
                    .equalTo("date", dateFilter)
                    .findAll();
        }else{
            query = realm.where(RetrofitToRealmAdapter.class)
                    .equalTo("name", cityFilter)
                    .equalTo("date", dateFilter)
                    .findAll();
        }

        woList.clear();

        for (RetrofitToRealmAdapter woA : query) {
            woList.add(woA);
            //saved.add(woA.getName() + "," + woA.getDate()+ "," + woA.getTime());
        }

        Log.e("******", "WoList-Size: " + query.size());

        if (woList.size() == 0){
            Toast.makeText(MainActivity.c, "Kein Eintrag gefunden", Toast.LENGTH_LONG).show();
        }
    }

    public void sortData(){
        Map<String, String> cityMap = new HashMap<String, String>();
        //seriesList.clear();

        for (RetrofitToRealmAdapter woA : woList) {
            cityMap.put(woA.getName(), woA.getName());
        }

        Log.e("******", "CityMap-Size create: " + cityMap.size());

        for(String key : cityMap.values()) {

            Log.e("******", "Key: " + key);

            Realm realm = Realm.getInstance(this);
            RealmResults<RetrofitToRealmAdapter> query;

            if (!dateFilter.equals("")) {
                query = realm.where(RetrofitToRealmAdapter.class)
                        .equalTo("date", dateFilter)
                        .equalTo("name", key)
                        .findAll();
            } else {
                query = realm.where(RetrofitToRealmAdapter.class)
                        .equalTo("name", key)
                        .findAll();
            }

            Log.e("******", "Query-Size create: " + query.size());

            List<RetrofitToRealmAdapter> woList2 = new ArrayList<>();

            for (RetrofitToRealmAdapter woA : query) {
                woList2.add(woA);
            }

            if (woList2.size() != 0) {
                Log.e("******", "Create Table: " + woList2.get(0).getName());
                createOnlineTable(woList2.get(0).getName(), woList2.get(0).getId());

                for (RetrofitToRealmAdapter woA2 : woList2) {
                    Log.e("******", "Send Data: ");
                    sendData(woA2);
                }

            }
        }


    }

    public boolean createOnlineTable(String CityName, String CityId){

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.178.53/ownstuff/createTable.php");

        CityName = CityName.replace(" ","").replace("-","").replace("ü","ue").replace("ä", "ae").replace("ö","oe").replace("Ü","Ue").replace("Ä","Ae").replace("Ö","Oe").replace("ß","ss");

        try {

            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("CityName", CityName));
            nameValuePairs.add(new BasicNameValuePair("CityID", CityId));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            ResponseHandler<String> resonseHandler = new BasicResponseHandler();
            String response = httpclient.execute(httppost, resonseHandler);
            Log.e("******", response);



        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            Log.e("******", "Caught ClientProtocolException: " + e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("******", "Caught IOException: " + e.getMessage());
        }
        return true;
    }

    public boolean sendData(RetrofitToRealmAdapter woA2){ //Daten des Objektes an den Server übertragen
        all++;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.178.53/ownstuff/insertEntry.php");

        String CityName =  woA2.getName();
        CityName = CityName.replace(" ","").replace("-","").replace("ü","ue").replace("ä", "ae").replace("ö","oe").replace("Ü","Ue").replace("Ä","Ae").replace("Ö","Oe").replace("ß","ss");

        try {

            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("CityName", CityName));
            nameValuePairs.add(new BasicNameValuePair("CityID", woA2.getId()));
            nameValuePairs.add(new BasicNameValuePair("Date", woA2.getDate()));
            nameValuePairs.add(new BasicNameValuePair("Time", woA2.getTime()));
            nameValuePairs.add(new BasicNameValuePair("Tempreature", woA2.getTemp()));
            nameValuePairs.add(new BasicNameValuePair("Weather", woA2.getDescription()));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            ResponseHandler<String> resonseHandler = new BasicResponseHandler();
            String response = httpclient.execute(httppost, resonseHandler);
            Log.e("******", response);
            if (response.toLowerCase().contains("error")){
                error++;
            }
            System.out.println("Uploaded: Entry " + woA2.getName() + ", " + woA2.getDate()+ ", " + woA2.getTime());


        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return true;
    }

    private class UploadOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            loadData(cityFilter, dateFilter);
            sortData();

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            tv_status.setVisibility(View.INVISIBLE);
            pb_upload.setVisibility(View.INVISIBLE);
            tv_finish.setVisibility(View.VISIBLE);
            iv_check.setVisibility(View.VISIBLE);

            tv_finish.setText("Upload finished, " + all + " values sent, " + error + " had errors.");

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }



}
