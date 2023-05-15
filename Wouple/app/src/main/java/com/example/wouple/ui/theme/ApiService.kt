package com.example.wouple.ui.theme

import com.example.wouple.model.TemperatureResponse
import retrofit2.http.GET

interface ApiService {
    @GET("/temperature_2m")
    suspend fun getTemperature(): List<TemperatureResponse>
}