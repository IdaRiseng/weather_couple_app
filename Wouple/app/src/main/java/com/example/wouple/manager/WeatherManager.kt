package com.example.wouple.manager

import android.content.Context
import android.widget.Toast
import com.example.wouple.BuildConfig
import com.example.wouple.model.api.ApiRequest
import com.example.wouple.model.api.SearchedLocations
import com.example.wouple.model.api.TemperatureResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherManager {

    private const val OPEN_METEO_BASE_URL = "https://api.open-meteo.com"
    private const val GEOCODE_BASE_URL = "https://geocode.maps.co"
    fun getSearchedLocations(context: Context, address: String, onSuccessCall: (List<SearchedLocations>) -> Unit) {
        val api = getApiBuilder(GEOCODE_BASE_URL)
        api.getSearchedLocations(address).enqueue(object : Callback<List<SearchedLocations>> {
            override fun onFailure(call: Call<List<SearchedLocations>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<SearchedLocations>>, response: Response<List<SearchedLocations>>) {
                response.body()?.let { onSuccessCall(it) }
            }
        })
    }

    fun getCurrentWeather(context: Context, location: SearchedLocations, onSuccessCall: (TemperatureResponse) -> Unit) {
        if (BuildConfig.DEBUG) {
            getDataFromMock(onSuccessCall)
        } else {
            fetchDataFromBackend(context, location, onSuccessCall)
        }
    }

    private fun getDataFromMock(onSuccessCall: (TemperatureResponse) -> Unit) {
        onSuccessCall(TemperatureResponse.getMockInstance())
    }

    private fun fetchDataFromBackend(context: Context, location: SearchedLocations, onSuccessCall: (TemperatureResponse) -> Unit) {
        val api = getApiBuilder(OPEN_METEO_BASE_URL)
        api.getTemperature(location.lat, location.lon).enqueue(object : Callback<TemperatureResponse> {
            override fun onFailure(call: Call<TemperatureResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<TemperatureResponse>, response: Response<TemperatureResponse>) {
                response.body()?.let { onSuccessCall(it) }
            }
        })
    }

    private fun getApiBuilder(baseUrl: String) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)


}