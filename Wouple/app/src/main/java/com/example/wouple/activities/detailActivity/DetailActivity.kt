package com.example.wouple.activities.detailActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val location = intent.getParcelableExtra<SearchedLocation>("location")?: throw IllegalStateException("location is wrong")
            val temp = intent.getParcelableExtra<TemperatureResponse>("temp")?: throw IllegalStateException("temp is wrong")
             SecondCardView(temp, location)
        }
    }
}

