package com.example.steffen.weatherup;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import java.util.List;

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

    int hour = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);


        chart_lay = (LinearLayout) findViewById(R.id.chart);
        c = this;

        temp_series = new XYSeries("Temperature Gau-Algehseim");
        temp_series.setTitle("Temperature Gau-Algehseim");

        loadData();

        for(RetrofitToRealmAdapter woA : woList){
            temp_series.add(hour++, Double.parseDouble(new RealmToRetrofitAdapter(woA).createWeatherObject().getMain().getTemp()));
        }

        drawGraph();
    }

    public void loadData() {
        Realm realm = Realm.getInstance(this);
        RealmResults<RetrofitToRealmAdapter> query = realm.where(RetrofitToRealmAdapter.class)
                .findAll();
        for (RetrofitToRealmAdapter woA : query) {
            woList.add(woA);
            //saved.add(woA.getName() + "," + woA.getDate()+ "," + woA.getTime());
        }

        if (woList.size() == 0){
            Toast.makeText(MainActivity.c, "Kein Eintrag gefunden", Toast.LENGTH_LONG).show();
        }
    }

    public void drawGraph(){
        // Now we create the renderer
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        // Include low and max value
        renderer.setDisplayBoundingPoints(true);
        // we add point markers
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        // We want to avoid black border
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        // Disable Pan on two axis
        mRenderer.setPanEnabled(false, false);
        mRenderer.setYAxisMax(40);
        mRenderer.setYAxisMin(0);
        mRenderer.setShowGrid(true); // we show the grid

        mRenderer.setXTitle("Time");
        mRenderer.setYTitle("Temprature");

        mRenderer.setAxisTitleTextSize(40);

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(temp_series);

        GraphicalView chartView = ChartFactory.getLineChartView(c, dataset, mRenderer);

        chart_lay.addView(chartView,0);
    }

}
