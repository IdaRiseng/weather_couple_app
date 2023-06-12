package com.example.wouple.model.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {
    @GET("v1/forecast?latitude=35.69&longitude=139.69&hourly=temperature_2m,relativehumidity_2m,dewpoint_2m,apparent_temperature,precipitation,weathercode,visibility,windspeed_10m&models=best_match&daily=weathercode&current_weather=true&timezone=auto&current_weather&latitude,longitude&temperature_unit&windspeed_unit&precipitation_unit")
    fun getTemperature(): Call<TemperatureResponse>
}