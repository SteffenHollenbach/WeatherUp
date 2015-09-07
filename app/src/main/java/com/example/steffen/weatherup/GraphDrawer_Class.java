package com.example.steffen.weatherup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Steffen on 28.07.2015.
 */
public class GraphDrawer_Class extends ActionBarActivity{

    Context c;
    XYSeries temp_series;
    LinearLayout chart_lay;
    List<RetrofitToRealmAdapter> woList = new ArrayList<>();
    List<XYSeries> seriesList = new ArrayList<>();


    String dateFilter, cityFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);

        Intent intent = getIntent();
        cityFilter = intent.getStringExtra("cityFilter");
        dateFilter = intent.getStringExtra("dateFilter");

        chart_lay = (LinearLayout) findViewById(R.id.chart);
        c = this;



        loadData(cityFilter, dateFilter);
        createXYSeries();
        drawGraph(seriesList);

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
            Toast.makeText(c, "Kein Eintrag gefunden", Toast.LENGTH_LONG).show();
        }
    }

    public void drawGraph(List<XYSeries> seriesList){
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

        // We want to avoid black border
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        // Disable Pan on two axis
        mRenderer.setPanEnabled(false, false);
        mRenderer.setYAxisMax(40);
        mRenderer.setYAxisMin(0);
        mRenderer.setShowGrid(true); // we show the grid

        mRenderer.setChartTitle("Temprature Overview");
        mRenderer.setXTitle("Time");
        mRenderer.setYTitle("Temprature");

        mRenderer.setAxisTitleTextSize(30);
        mRenderer.setLegendTextSize(30);
        mRenderer.setLabelsTextSize(20);
        mRenderer.setChartTitleTextSize(40);

        mRenderer.setGridColor(Color.GRAY);
        mRenderer.setLabelsColor(Color.BLACK);

        mRenderer.setMargins(new int[] { 50, 50, 200, 22 });
        mRenderer.setFitLegend(true);

        //mRenderer.setZoomEnabled(true, false);

        mRenderer.setPanEnabled(true, true);
        mRenderer.setClickEnabled(false);

        XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();

        for(XYSeries xy : seriesList){
            mDataset.addSeries(xy);

            // Now we create the renderer
            XYSeriesRenderer renderer = new XYSeriesRenderer();
            renderer.setLineWidth(2);

            int red = (int)(Math.random() * 245);
            int green = (int)(Math.random() * 245);
            int blue = (int)(Math.random() * 245);


            renderer.setColor(Color.rgb(red, green, blue));
            // Include low and max value
            renderer.setDisplayBoundingPoints(true);
            // we add point markers
            renderer.setPointStyle(PointStyle.CIRCLE);
            renderer.setPointStrokeWidth(3);

            mRenderer.addSeriesRenderer(renderer);
        }


        GraphicalView chartView = ChartFactory.getLineChartView(c, mDataset, mRenderer);

        chart_lay.addView(chartView,0);
    }

    public void createXYSeries(){
        Map<String, String> cityMap = new HashMap<String, String>();
        seriesList.clear();

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
                createXYSeriesPart2(woList2);
            }
        }


    }



    public void createXYSeriesPart2(List<RetrofitToRealmAdapter> woList2){
        XYSeries temp_series = new XYSeries("Temprature " + woList2.get(0).getName());
        int hour = 0;

        for(RetrofitToRealmAdapter woA : woList2){
            temp_series.add(hour++, Double.parseDouble(new RealmToRetrofitAdapter(woA).createWeatherObject().getMain().getTemp()));
        }

        seriesList.add(temp_series);
    }

}
