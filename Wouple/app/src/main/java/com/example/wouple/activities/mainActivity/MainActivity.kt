package com.example.wouple.activities.mainActivity


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.wouple.activities.startScreen.StartActivity
import com.example.wouple.activities.detailActivity.SecondActivity
import com.example.wouple.activities.splashScreen.Navigation
import com.example.wouple.activities.startScreen.StartScreenView
import com.example.wouple.manager.WeatherManager
import com.example.wouple.manager.WeatherManager.getCurrentWeather
import com.example.wouple.manager.WeatherManager.getSearchedLocations
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.preferences.LocationPref
import com.example.wouple.preferences.TemperatureUnitPref
import com.example.wouple.ui.theme.WoupleTheme

class MainActivity : ComponentActivity() {
    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private val temp: MutableState<TemperatureResponse?> = mutableStateOf(null)
    private val searchedLocations: MutableState<List<SearchedLocation>?> = mutableStateOf(null)
    private val searchedLocation: MutableState<SearchedLocation?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

        setContent {
            WoupleTheme {
                if (isFirstLaunch)
                    NoTemperatureView(
                        onStartButtonClicked = {
                            Log.d("NoTemperatureView", "Start button clicked")
                            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
                            val intent = Intent(this@MainActivity, StartActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    )
                else {
                    displayFirstCardView()
                }
            }
        }
    }
    private fun displayNoTemperatureView() {
        setContent {
            WoupleTheme {
                NoTemperatureView(
                    onStartButtonClicked = {
                        sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
                        startActivity(Intent(this@MainActivity, StartActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
    private fun displayFirstCardView() {
        setContent {
            if (temp.value !== null) {
            FirstCardView(
                temp = temp.value!! ,
                locations = searchedLocations.value,
                onLocationButtonClicked = { location ->
                    onLocationButtonClicked(location)
                },
                searchedLocation = searchedLocation,
                onClose = { searchedLocations.value = null },
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
                },
                onTemperatureUnitChanged = { temperatureUnit ->
                    TemperatureUnitPref.setTemperatureUnit(this, temperatureUnit)
                    getCurrentWeather(
                        context = this,
                        location = searchedLocation.value,
                        temperaUnit = temperatureUnit,
                        onSuccessCall = { temperature ->
                            temp.value = temperature
                        })
                }
            )
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
            }, temperaUnit = TemperatureUnitPref.getTemperatureUnit(this)
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
            temperaUnit = TemperatureUnitPref.getTemperatureUnit(this),
            onSuccessCall = { temperature ->
                temp.value = temperature
            },
        )
    }
}


