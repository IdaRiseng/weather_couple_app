package com.example.wouple.model.api


import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @GET("v1/forecast?latitude=-41.29&longitude=174.78&hourly=temperature_2m,relativehumidity_2m,dewpoint_2m,apparent_temperature,precipitation_probability,precipitation,weathercode,visibility,windspeed_10m,uv_index&daily=weathercode,uv_index_max,precipitation_sum&current_weather=true&timezone=auto")
    fun getTemperature(): Call<TemperatureResponse>

}