package com.example.wouple.activities.mainActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.ui.theme.Spiro

@Composable
fun NoTemperatureView(
    locations: List<SearchedLocation>?,
    onSearch: (String) -> Unit,
    onLocationButtonClicked: (SearchedLocation) -> Unit,
    searchedLocation: MutableState<SearchedLocation?>,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
                .padding(bottom = 18.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            HorizontalWave(
                phase = rememberPhaseState(0f),
                alpha = 1f,
                amplitude = 50f,
                frequency = 0.5f
            )
            HorizontalWave(
                phase = rememberPhaseState(15f),
                alpha = 0.5f,
                amplitude = 80f,
                frequency = 0.3f
            )
            HorizontalWave(
                phase = rememberPhaseState(10f),
                alpha = 0.2f,
                amplitude = 40f,
                frequency = 0.6f
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .padding(top = 8.dp),
                text = "Wouple Forecast",
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Cursive,
                fontSize = 32.sp,
                color = Color.White,
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                item {
                    SearchBar(onSearch, onClose)
                }
                items(locations ?: emptyList()) { location ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                searchedLocation.value = location
                                onLocationButtonClicked(location)
                            },
                        elevation = 4.dp,
                        backgroundColor = Color.White
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painter = painterResource(id = R.drawable.pin),
                                contentDescription = null,
                                tint = Color.Red
                            )

                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = location.display_name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize(0.9f)
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sailingboat), contentDescription = null,
                tint = Spiro, modifier = Modifier
                    .align(CenterHorizontally)
                    .size(70.dp)
                    .padding(start = 16.dp, bottom = 8.dp)
            )
        }
    }
}