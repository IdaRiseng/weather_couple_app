package com.example.wouple.model.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @GET("/v1/forecast?latitude=52.52&longitude=13.41&current_weather=true&hourly=temperature_2m")
    fun getTemperature(): Call<TemperatureResponse>
}