package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Daily(
    val precipitation_sum: List<Double>,
    val time: List<String>,
    val uv_index_max: List<Double>,
    val weathercode: List<Int>
): Parcelable