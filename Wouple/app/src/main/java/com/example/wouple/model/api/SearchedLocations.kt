package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchedLocations(
    val lat: String,
    val lon: String,
    val display_name: String
): Parcelable
