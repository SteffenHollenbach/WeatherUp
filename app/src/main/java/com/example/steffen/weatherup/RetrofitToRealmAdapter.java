package com.example.steffen.weatherup;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Steffen on 26.07.2015.
 */
public class RetrofitToRealmAdapter extends RealmObject{

    private String lon;
    private String lat;

    private String weather_id;
    private String main;
    private String description;
    private String icon;

    private String temp;
    private String pressure;
    private String humidity;
    private String temp_min;
    private String temp_max;

    private String speed;
    private String deg;

    private String all;

    private String type;
    private String sys_id;
    private String message;
    private String country;
    private String sunrise;
    private String sunset;

    private String base;
    private String visibility;
    private String dt;
    private String id;
    private String name;
    private String cod;

    private String date;
    private String time;

    @PrimaryKey
    private String primarykey;

    public RetrofitToRealmAdapter() {
    }

    public RetrofitToRealmAdapter(WeatherObject wo) {
        this.lon = wo.getCoord().getLon();
        this.lat = wo.getCoord().getLat();
        this.weather_id = wo.getWeather().get(0).getId();
        this.main = wo.getWeather().get(0).getMain();
        this.description = wo.getWeather().get(0).getDescription();
        this.icon = wo.getWeather().get(0).getIcon();
        this.temp = wo.getMain().getTemp();
        this.pressure = wo.getMain().getPressure();
        this.humidity = wo.getMain().getHumidity();
        this.temp_min = wo.getMain().getTemp_min();
        this.temp_max = wo.getMain().getTemp_max();
        this.speed = wo.getWind().getSpeed();
        this.deg = wo.getWind().getDeg();
        this.all = wo.getClouds().getAll();
        this.type = wo.getSys().getType();
        this.sys_id =  wo.getSys().getId();
        this.message =  wo.getSys().getMessage();
        this.country =  wo.getSys().getCountry();
        this.sunrise =  wo.getSys().getSunrise();
        this.sunset =  wo.getSys().getSunset();
        this.base = wo.getBase();
        this.visibility = wo.getVisibility();
        this.dt = wo.getDt();
        this.id = wo.getId();
        this.name = wo.getName();
        this.cod = wo.getCod();
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

    public String getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(String weather_id) {
        this.weather_id = weather_id;
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

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSys_id() {
        return sys_id;
    }

    public void setSys_id(String sys_id) {
        this.sys_id = sys_id;
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
}
