package com.example.wouple.model.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @GET("v1/forecast?latitude=52.52&longitude=13.41&hourly=temperature_2m,relativehumidity_2m,dewpoint_2m,apparent_temperature,precipitation,visibility,windspeed_10m&timezone=auto")
    fun getTemperature(): Call<TemperatureResponse>
}