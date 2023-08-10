package com.example.wouple.manager

import android.content.Context
import android.widget.Toast
import com.airbnb.lottie.BuildConfig
import com.example.wouple.BASE_URL
import com.example.wouple.model.api.ApiRequest
import com.example.wouple.model.api.TemperatureResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherManager {

    fun getCurrentWeather(context: Context, onSuccessCall: (TemperatureResponse) -> Unit) {
        if (BuildConfig.DEBUG) {
            getDataFromMock(onSuccessCall)
        } else {
            fetchDataFromBackend(context, onSuccessCall)
        }
    }

    private fun getDataFromMock(onSuccessCall: (TemperatureResponse) -> Unit) {
        onSuccessCall(TemperatureResponse.getMockInstance())
    }

    private fun fetchDataFromBackend(context: Context, onSuccessCall: (TemperatureResponse) -> Unit) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        api.getTemperature().enqueue(object : Callback<TemperatureResponse> {
            override fun onFailure(call: Call<TemperatureResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

            }

            override fun onResponse(
                call: Call<TemperatureResponse>,
                response: Response<TemperatureResponse>
            ) {
                response.body()?.let { onSuccessCall(it) }
            }
        })
    }
}