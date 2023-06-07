package com.example.wouple.model.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @GET("/v1/forecast?latitude=52.52&longitude=13.41&hourly=showers,temperature_80m&daily=uv_index_max,precipitation_probability_max&timezone=Europe%2FLondon")
    fun getTemperature(): Call<TemperatureResponse>
}