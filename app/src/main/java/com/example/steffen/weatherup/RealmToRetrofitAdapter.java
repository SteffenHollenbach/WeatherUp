package com.example.steffen.weatherup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steffen on 26.07.2015.
 */
public class RealmToRetrofitAdapter {

    WeatherObject wo;

    public RealmToRetrofitAdapter(RetrofitToRealmAdapter woA){
        WeatherObject.Coord coord = new WeatherObject.Coord(woA.getLon(), woA.getLat());

        WeatherObject.Weather weather = new WeatherObject.Weather(woA.getId(), woA.getMain(), woA.getDescription(), woA.getIcon());
        List<WeatherObject.Weather> weatherList = new ArrayList<WeatherObject.Weather>();
        weatherList.add(weather);

        WeatherObject.Main main = new WeatherObject.Main(woA.getTemp(), woA.getPressure(), woA.getHumidity(), woA.getTemp_min(), woA.getTemp_max());
        WeatherObject.Wind wind = new WeatherObject.Wind(woA.getSpeed(), woA.getDeg());
        WeatherObject.Clouds clouds = new WeatherObject.Clouds(woA.getAll());
        WeatherObject.Sys sys = new WeatherObject.Sys(woA.getType(), woA.getId(), woA.getMessage(), woA.getCountry(), woA.getSunrise(), woA.getSunset());

        wo = new WeatherObject(coord, weatherList, main, wind, clouds, sys, woA.getBase(), woA.getVisibility(), woA.getDt(), woA.getId(), woA.getName(), woA.getCod());
        wo.setTime(woA.getTime());
        wo.setDate(woA.getDate());
        wo.setPrimarykey(woA.getPrimarykey());

    }



    public WeatherObject createWeatherObject(){
        return wo;
    }
}
