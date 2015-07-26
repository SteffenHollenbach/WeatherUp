package com.example.steffen.weatherup;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Steffen on 25.07.2015.
 */
public interface WeatherService {


    @GET("/weather")
    void getWeatherZip(@Query("zip") String zip, @Query("units") String units, Callback<WeatherObject> callback);

    @GET("/weather")
    void getWeatherGPS(@Query("lat") String lat, @Query("lon") String lon, @Query("units") String units, Callback<WeatherObject> callback);
}
