package com.example.wouple.activities.mainActivity


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.wouple.activities.detailActivity.SecondActivity
import com.example.wouple.manager.WeatherManager.getCurrentWeather
import com.example.wouple.manager.WeatherManager.getSearchedLocations
import com.example.wouple.model.api.SearchedLocations
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.WoupleTheme

class MainActivity : ComponentActivity() {

    private val temp: MutableState<TemperatureResponse?> = mutableStateOf(null)
    private val searchedLocations: MutableState<List<SearchedLocations>?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WoupleTheme {
                if (temp.value == null) {
                    NoTemperatureView(
                        locations = searchedLocations.value,
                        onLocationButtonClicked = { location ->
                            getCurrentWeather(
                                context = this,
                                location = location,
                                onSuccessCall = { temperature ->
                                    temp.value = temperature
                                }
                            )
                            searchedLocations.value = null
                        },
                        onSearch = { query ->
                            getSearchedLocations(
                                context = this,
                                address = query,
                                onSuccessCall = { location ->
                                    searchedLocations.value = location
                                })
                        }
                    )
                } else {
                    FirstCardView(
                        temp = temp.value!!,
                        locations = searchedLocations.value,
                        onLocationButtonClicked = { location ->
                            getCurrentWeather(
                                context = this,
                                location = location,
                                onSuccessCall = { temperature ->
                                    temp.value = temperature
                                }
                            )
                            searchedLocations.value = null
                        },
                        onSearch = { query ->
                            getSearchedLocations(
                                context = this,
                                address = query,
                                onSuccessCall = { location ->
                                    searchedLocations.value = location
                                })
                        }
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
        // getCurrentWeather(this) {
        //     temp.value = it
        // }
    }
}

