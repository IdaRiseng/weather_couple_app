package com.example.wouple.model.api

data class Hourly(
    val temperature_2m: List<Double>,
    val time: List<String>
)