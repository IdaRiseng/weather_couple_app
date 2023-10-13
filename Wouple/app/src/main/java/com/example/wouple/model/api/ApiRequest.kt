package com.example.wouple.model.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {
    //temperature_unit&windspeed_unit&precipitation_unit
    @GET("https://api.open-meteo.com/v1/forecast/")
    fun getTemperature(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("hourly") hourly: String = "temperature_2m,relativehumidity_2m,dewpoint_2m,apparent_temperature,precipitation_probability,weathercode,visibility,windspeed_10m,uv_index,is_day",
        @Query("models") models: String = "best_match",
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min,weathercode,sunrise,sunset,uv_index_max,precipitation_sum,rain_sum",
        @Query("timezone") timezone: String = "auto",
        @Query("current_weather") current_weather: String = "true",
    ): Call<TemperatureResponse>

    @GET("https://geocode.maps.co/search")
    fun getSearchedLocations(@Query("q") address: String): Call<List<SearchedLocation>>
}