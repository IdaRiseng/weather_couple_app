package com.example.wouple.model.api


import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @GET("https://api.open-meteo.com/v1/forecast?latitude=35.6895&longitude=139.6917&hourly=temperature_2m,relativehumidity_2m,dewpoint_2m,apparent_temperature,precipitation_probability,weathercode,visibility,windspeed_10m,uv_index,is_day&models=best_match&daily=temperature_2m_max,temperature_2m_min,weathercode,sunrise,sunset,uv_index_max,precipitation_sum,rain_sum&timezone=auto&current_weather=true&temperature_unit&windspeed_unit&precipitation_unit")
    fun getTemperature(): Call<TemperatureResponse>
    
}