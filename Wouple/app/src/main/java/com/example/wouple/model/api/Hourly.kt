package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hourly(
    val apparent_temperature: List<Double>,
    val dewpoint_2m: List<Double>,
    val precipitation: List<Double>,
    val relativehumidity_2m: List<Int>,
    val temperature_2m: List<Double>,
    val time: List<String>,
    val visibility: List<Double>,
    val weathercode: List<Int>,
    val windspeed_10m: List<Double>
): Parcelable