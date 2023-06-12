package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Daily(
    val time: List<String>,
    val weathercode: List<Int>
): Parcelable