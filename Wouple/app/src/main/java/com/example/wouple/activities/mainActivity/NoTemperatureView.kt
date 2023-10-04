package com.example.wouple.activities.mainActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wouple.model.api.SearchedLocations

@Composable
fun NoTemperatureView(
    locations: List<SearchedLocations>?,
    onSearch: (String) -> Unit,
    onLocationButtonClicked: (SearchedLocations) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.primary).fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        SearchBar(onSearch)
        locations?.forEach { location ->
            Button(onClick = {
                onLocationButtonClicked(location)
            }) {
                Text(text = location.display_name)
            }
        }
    }
}