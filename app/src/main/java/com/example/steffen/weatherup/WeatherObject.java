package com.example.steffen.weatherup;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Steffen on 25.07.2015.
 */
public class WeatherObject implements Serializable {

    public WeatherObject(Coord coord, List<Weather> weather, Main main, Wind wind, Clouds clouds, Sys sys, String base, String visibility, String dt, String id, String name, String cod) {
        this.coord = coord;
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.sys = sys;
        this.base = base;
        this.visibility = visibility;
        this.dt = dt;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    @Expose
    private Coord coord;

    public static class Coord implements Serializable {
        String lon;
        String lat;

        public Coord(String lon, String lat) {
            this.lon = lon;
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }

    @Expose
    private List<Weather> weather;

    public static class Weather implements Serializable {
        String id;
        String main;
        String description;
        String icon;

        public Weather(String id, String main, String description, String icon) {
            this.id = id;
            this.main = main;
            this.description = description;
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    @Expose
    private Main main;

    public static class Main implements Serializable {
        String temp;
        String pressure;
        String humidity;
        String temp_min;
        String temp_max;

        public Main(String temp, String pressure, String humidity, String temp_min, String temp_max) {
            this.temp = temp;
            this.pressure = pressure;
            this.humidity = humidity;
            this.temp_min = temp_min;
            this.temp_max = temp_max;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(String temp_min) {
            this.temp_min = temp_min;
        }

        public String getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(String temp_max) {
            this.temp_max = temp_max;
        }
    }

    @Expose
    private Wind wind;

    public static class Wind implements Serializable {
        String speed;
        String deg;

        public Wind(String speed, String deg) {
            this.speed = speed;
            this.deg = deg;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getDeg() {
            return deg;
        }

        public void setDeg(String deg) {
            this.deg = deg;
        }
    }

    @Expose
    private Clouds clouds;

    public static class Clouds implements Serializable {
        String all;

        public Clouds(String all) {
            this.all = all;
        }

        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }
    }

    @Expose
    private Sys sys;

    public static class Sys implements Serializable {
        String type;
        String id;
        String message;
        String country;
        String sunrise;
        String sunset;

        public Sys(String type, String id, String message, String country, String sunrise, String sunset) {
            this.type = type;
            this.id = id;
            this.message = message;
            this.country = country;
            this.sunrise = sunrise;
            this.sunset = sunset;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }
    }


    @Expose
    private String base;
    @Expose
    private String visibility;
    @Expose
    private String dt;
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String cod;

    private String date;
    private String time;
    private String primarykey;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String toString() {
        return getName();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrimarykey() {
        return primarykey;
    }

    public void setPrimarykey(String primarykey) {
        this.primarykey = primarykey;
    }

    public static WeatherObject checkNull(WeatherObject wo) {
        if (wo.getCoord().getLon() == null) {
            wo.getCoord().setLon("Unkown");
        }
        if (wo.getCoord().getLat() == null) {
            wo.getCoord().setLat("Unkown");
        }
        if (wo.getWeather().get(0).getId() == null) {
            wo.getWeather().get(0).setId("Unkown");
        }
        if (wo.getWeather().get(0).getMain() == null) {
            wo.getWeather().get(0).setMain("Unkown");
        }
        if (wo.getWeather().get(0).getDescription() == null) {
            wo.getWeather().get(0).setDescription("Unkown");
        }
        if (wo.getWeather().get(0).getIcon() == null) {
            wo.getWeather().get(0).setIcon("Unkown");
        }
        if (wo.getMain().getTemp() == null) {
            wo.getMain().setTemp("Unkown");
        }
        if (wo.getMain().getPressure() == null) {
            wo.getMain().setPressure("Unkown");
        }
        if (wo.getMain().getHumidity() == null) {
            wo.getMain().setHumidity("Unkown");
        }
        if (wo.getMain().getTemp_min() == null) {
            wo.getMain().setTemp_min("Unkown");
        }
        if (wo.getMain().getTemp_max() == null) {
            wo.getMain().setTemp_max("Unkown");
        }
        if (wo.getWind().getSpeed() == null) {
            wo.getWind().setSpeed("Unkown");
        }
        if (wo.getWind().getDeg() == null) {
            wo.getWind().setDeg("Unkown");
        }
        if (wo.getClouds().getAll() == null) {
            wo.getClouds().setAll("Unkown");
        }
        if (wo.getSys().getType() == null) {
            wo.getSys().setType("Unkown");
        }
        if (wo.getSys().getId() == null) {
            wo.getSys().setId("Unkown");
        }
        if (wo.getSys().getMessage() == null) {
            wo.getSys().setMessage("Unkown");
        }
        if (wo.getSys().getCountry() == null) {
            wo.getSys().setCountry("Unkown");
        }
        if (wo.getSys().getSunrise() == null) {
            wo.getSys().setSunrise("Unkown");
        }
        if (wo.getSys().getSunset() == null) {
            wo.getSys().setSunset("Unkown");
        }
        if (wo.getBase() == null) {
            wo.setBase("Unkown");
        }
        if (wo.getVisibility() == null) {
            wo.setVisibility("Unkown");
        }
        if (wo.getDt() == null) {
            wo.setDt("Unkown");
        }
        if (wo.getId() == null) {
            wo.setId("Unkown");
        }
        if (wo.getName() == null) {
            wo.setName("Unkown");
        }
        if (wo.getCod() == null) {
            wo.setCod("Unkown");
        }
        if (wo.getTime() == null) {
            wo.setTime("Unkown");
        }
        if (wo.getDate() == null) {
            wo.setDate("Unkown");
        }


        return wo;
    }
}
