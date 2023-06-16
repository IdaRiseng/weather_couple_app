package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyUnits(
    val precipitation_sum: String,
    val time: String,
    val uv_index_max: String,
    val weathercode: String
): Parcelable