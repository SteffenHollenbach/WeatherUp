package com.example.steffen.weatherup;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Steffen on 25.07.2015.
 */
public class WeatherObject {
    @Expose
    Coord coord;

    public static class Coord {
        String lon;
        String lat;
    }

    @Expose
    List<Weather> weather;

    public static class Weather {
        String id;
        String main;
        String description;
        String icon;

    }

    @Expose
    Main main;

    public static class Main {
        String temp;
        String pressure;
        String humidity;
        String temp_min;
        String temp_max;
    }

    @Expose
    Wind wind;

    public static class Wind {
        String speed;
        String deg;
    }

    @Expose
    Clouds clouds;

    public static class Clouds {
        String all;
    }

    @Expose
    Sys sys;

    public static class Sys {
        String type;
        String id;
        String message;
        String country;
        String sunrise;
        String sunset;
    }


    @Expose
    String base;
    @Expose
    String visibility;
    @Expose
    String dt;
    @Expose
    String id;
    @Expose
    String name;
    @Expose
    String cod;


    public String toString() {
        return "Visibility: " + visibility + ", Coord: " + coord.lat + ", Weather: " + weather.get(0).description;
    }
}
