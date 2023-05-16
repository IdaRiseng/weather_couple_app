package com.example.wouple.model.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @GET("v1/forecast")
    fun getTemperature(): Call<List<TemperatureResponse>>
}