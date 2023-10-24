package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TemperatureUnit(
    val fahrenheit: String,
    val celsius: String
): Parcelable
