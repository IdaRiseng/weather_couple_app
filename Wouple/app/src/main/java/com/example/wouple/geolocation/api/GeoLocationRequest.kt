package com.example.wouple.geolocation.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class Location(val name: String, val latitude: Double, val longitude: Double)
interface GeoLocationRequest {
    @GET("https://geocoding-api.open-meteo.com/v1/search")
    suspend fun searchLocations(@Query("name") city: String): List<Location>
}
val retrofit: Retrofit
    get() = Retrofit.Builder()
        .baseUrl("https://open-meteo.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

val apiService: GeoLocationRequest
    get() = retrofit.create(GeoLocationRequest::class.java)