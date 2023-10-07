package com.example.wouple.preferences

import android.content.Context
import com.example.wouple.model.api.SearchedLocations
import com.google.gson.Gson

object LocationPref {
    fun getSearchedLocation(context: Context): SearchedLocations? {
        val sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE) ?: return null
        val gson = Gson()
        val json = sharedPref.getString("locationKey", "")
        return gson.fromJson(json, SearchedLocations::class.java)
    }

    fun setSearchedLocation(context: Context, location: SearchedLocations) {
        val gson = Gson()
        val json = gson.toJson(location)
        val sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("locationKey", json)
            apply()
        }
    }
}