package com.example.wouple.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class TemperatureResponse(
    val daily: @RawValue Daily?,
    val daily_units: @RawValue DailyUnits?,
    val elevation: Double,
    val generationtime_ms: Double,
    val hourly: @RawValue Hourly?,
    val hourly_units: @RawValue HourlyUnits?,
    val latitude: Double?,
    val longitude: Double?,
    val timezone: String?,
    val timezone_abbreviation: String?,
    val utc_offset_seconds: Int?
): Parcelable