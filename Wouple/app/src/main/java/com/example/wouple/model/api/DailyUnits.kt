package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyUnits(
    val time: String,
    val weathercode: String
): Parcelable