package com.example.wouple.activities.mainActivity


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import com.example.wouple.activities.detailActivity.SecondActivity
import com.example.wouple.manager.WeatherManager.getCurrentWeather
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.WoupleTheme

const val BASE_URL = "https://api.open-meteo.com"

class MainActivity : ComponentActivity() {
    private val temp: MutableState<TemperatureResponse?> = mutableStateOf(null)
    private val temp2: MutableState<TemperatureResponse?> = mutableStateOf(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WoupleTheme {
                if (temp.value == null || temp2.value == null) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    FirstCardView(
                        temp = temp.value!!,
                        temp2 = temp2.value!!,
                        onSearch = { getCurrentWeather(this) { temp.value = it } }
                    ) { temp ->
                        val intent = Intent(this, SecondActivity::class.java)
                        intent.putExtra("temp", temp)
                        this.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getCurrentWeather(this) {
            temp.value = it
        }
        getCurrentWeather(this) {
            temp2.value = it
        }
    }
}

