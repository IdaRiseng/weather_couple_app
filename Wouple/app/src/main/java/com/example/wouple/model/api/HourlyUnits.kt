package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HourlyUnits(
    val showers: String,
    val temperature_80m: String,
    val time: String
): Parcelable