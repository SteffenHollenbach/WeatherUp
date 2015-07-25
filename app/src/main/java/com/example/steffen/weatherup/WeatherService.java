package com.example.steffen.weatherup;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Steffen on 25.07.2015.
 */
public interface WeatherService {


    @GET("/weather")
    void getWeatherZip(@Query("zip") String zip, @Query("units") String units,  Callback<WeatherObject> callback);

    @GET("/weather")
    void getWeathersZip(@Query("zip") String zip,  Callback<List<WeatherObject>> callback);
}
