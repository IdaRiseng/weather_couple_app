package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hourly(
    val showers: List<Double>,
    val temperature_80m: List<Double>,
    val time: List<String>
): Parcelable