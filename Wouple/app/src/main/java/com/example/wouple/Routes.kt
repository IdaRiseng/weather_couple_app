package com.example.wouple

sealed class Routes(val route: String) {
    object Location : Routes("location")
}
