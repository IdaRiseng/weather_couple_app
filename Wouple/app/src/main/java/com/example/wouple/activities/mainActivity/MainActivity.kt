package com.example.wouple.activities.mainActivity


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.wouple.activities.detailActivity.SecondActivity
import com.example.wouple.manager.WeatherManager
import com.example.wouple.manager.WeatherManager.getCurrentWeather
import com.example.wouple.manager.WeatherManager.getSearchedLocations
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.model.api.TemperatureUnit
import com.example.wouple.preferences.LocationPref
import com.example.wouple.ui.theme.WoupleTheme

class MainActivity : ComponentActivity() {
    private val temp: MutableState<TemperatureResponse?> = mutableStateOf(null)
    private val searchedLocations: MutableState<List<SearchedLocation>?> = mutableStateOf(null)
    private val searchedLocation: MutableState<SearchedLocation?> = mutableStateOf(null)
    private val temperatureUnit: MutableState<TemperatureUnit> = mutableStateOf(TemperatureUnit("°F", "°C"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoupleTheme {
                DropDownMenu(temperatureUnit = temperatureUnit.value)
                if (temp.value == null) {
                    NoTemperatureView(
                        locations = searchedLocations.value,
                        onLocationButtonClicked = { location ->
                            onLocationButtonClicked(location)
                        },
                        searchedLocation = searchedLocation,
                        onClose = {searchedLocations.value = null},
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
                        temperatureUnit = temperatureUnit.value,
                        locations = searchedLocations.value,
                        onLocationButtonClicked = { location ->
                            onLocationButtonClicked(location)
                        },
                        searchedLocation = searchedLocation,
                        onClose = {searchedLocations.value = null},
                        onSearch = { query ->
                            getSearchedLocations(
                                context = this,
                                address = query,
                                onSuccessCall = { location ->
                                    searchedLocations.value = location
                                })
                        },
                        onDetailsButtonClicked = { temp ->
                            val intent = Intent(this, SecondActivity::class.java)
                            intent.putExtra("temp", temp)
                            intent.putExtra("location", searchedLocation.value)
                            this.startActivity(intent)
                        }
                    )
                }
            }
        }
    }

    private fun onLocationButtonClicked(location: SearchedLocation) {
        LocationPref.setSearchedLocation(this, location)
        getCurrentWeather(
            context = this,
            location = location,
            onSuccessCall = { temperature ->
                temp.value = temperature
            },
            unit = temperatureUnit.value
        )
        searchedLocations.value = null
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onResume() {
        super.onResume()
        searchedLocation.value = LocationPref.getSearchedLocation(this)
        getCurrentWeather(
            context = this,
            location = searchedLocation.value,
            onSuccessCall = { temperature ->
                temp.value = temperature
            },
            unit = temperatureUnit.value
        )
    }
}

