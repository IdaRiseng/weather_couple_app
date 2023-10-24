package com.example.wouple.activities.mainActivity

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.wouple.R
import com.example.wouple.activities.detailActivity.WeatherCondition
import com.example.wouple.activities.lightningMap.LightningMapActivity
import com.example.wouple.activities.rainMap.WeatherRadarWebView
import com.example.wouple.elements.GetWeatherCodes
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.model.api.TemperatureUnit
import com.example.wouple.ui.theme.Dark20
import com.example.wouple.ui.theme.Spiro


/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val temperature = TemperatureResponse(
        elevation = 3.0,
        generationtime_ms = 3.0,
        hourly = Hourly(listOf(), listOf()),
        hourly_units = HourlyUnits("", ""),
        latitude = 3.0,
        longitude = 3.0,
        timezone = "yeet",
        timezone_abbreviation = "woop",
        utc_offset_seconds = 2
    )

    WoupleTheme {
        FirstCardView(temperature, temperature)
    }
}*/

@Composable
fun FirstCardView(
    temp: TemperatureResponse,
    locations: List<SearchedLocation>?,
    onSearch: (String) -> Unit,
    searchedLocation: MutableState<SearchedLocation?>,
    onLocationButtonClicked: (SearchedLocation) -> Unit,
    onDetailsButtonClicked: (TemperatureResponse) -> Unit,
    onClose: () -> Unit,
    temperatureUnit: TemperatureUnit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .background(Dark20)
                .padding(bottom = 18.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.padding(bottom = 32.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(onSearch, onClose)
                if (locations != null) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(locations) { location ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp, horizontal = 16.dp)
                                    .clip(RoundedCornerShape(30.dp))
                                    .clickable {
                                        searchedLocation.value = location
                                        onLocationButtonClicked(location)
                                    },
                                elevation = 4.dp,
                                backgroundColor = White
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.pin),
                                        contentDescription = null,
                                        tint = Unspecified
                                    )
                                    getProperDisplayName(location.display_name)?.let {
                                        Text(
                                            text = it,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Text(
                    text = getProperDisplayName(searchedLocation.value?.display_name) ?: "N/D",
                    fontWeight = FontWeight.Thin,
                    textAlign = TextAlign.Center,
                    fontSize = 50.sp,
                    color = Color.White,
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
                    text = temp.current_weather.temperature.toInt()
                        .toString() + temp.hourly_units.temperature_2m[0],
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Thin,
                    fontSize = 50.sp,
                    color = Color.White,
                )
                val todayWeatherConditions = when (temp.current_weather.weathercode) {
                    0, 1 -> WeatherCondition.SUNNY
                    2, 3 -> WeatherCondition.CLOUDY
                    51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> WeatherCondition.RAINY
                    71, 73, 75, 77, 85, 86 -> WeatherCondition.SNOWY
                    else -> WeatherCondition.SUNNY
                }
                Image(
                    painter = painterResource(id = todayWeatherConditions.imageResourceId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(bottom = 8.dp, end = 8.dp),
                    alignment = Alignment.BottomCenter
                )
                Row(
                    modifier = Modifier.padding(bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    /*     IconButton(
                             modifier = Modifier.padding(bottom = 16.dp),
                             onClick = { onDetailsButtonClicked(temp) }
                         ) {
                             Icon(
                                 painter = painterResource(id = R.drawable.arrowforward),
                                 contentDescription = null,
                                 tint = Color.White
                             )
                         }*/
                    /*  Button(
                            modifier = Modifier.padding(bottom = 16.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(Whitehis),
                            onClick = {
                                onDetailsButtonClicked(temp)
                            }) {
                            Text(text = "Forecast details")
                        }*/
                    DropDownMenu(temperatureUnit)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        modifier = Modifier.padding(bottom = 16.dp),
                        onClick = { onDetailsButtonClicked(temp) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrowforward),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
            HorizontalWave(
                phase = rememberPhaseState(0f),
                alpha = 1f,
                amplitude = 50f,
                frequency = 0.5f,
                gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
            )
            HorizontalWave(
                phase = rememberPhaseState(15f),
                alpha = 0.5f,
                amplitude = 80f,
                frequency = 0.3f,
                gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
            )
            HorizontalWave(
                phase = rememberPhaseState(10f),
                alpha = 0.2f,
                amplitude = 40f,
                frequency = 0.6f,
                gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .background(Color.White)
                .padding(top = 16.dp)
        ) {
            TodayWeatherCard(temp, onDetailsButtonClicked)
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(1f)
                .background(Color.White)
                .padding(bottom = 16.dp),
            contentAlignment = Center
        ) {
            searchedLocation.value?.let { ClickableCardDemo(it) }
        }
    }
}

@Composable
fun DropDownMenu(temperatureUnit: TemperatureUnit) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedTemperatureUnit by remember { mutableStateOf(TemperatureUnit("Fahrenheit", "Celsius")) }

    Box(modifier = Modifier.padding(start = 16.dp)) {
        IconButton(
            onClick = { isExpanded = !isExpanded }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menuicon), contentDescription = null,
                modifier = Modifier.size(30.dp), tint = Color.White,
            )
        }
        DropdownMenu(expanded = isExpanded,
            onDismissRequest = { isExpanded = false })
        {
            DropdownMenuItem(
                onClick = {
                    selectedTemperatureUnit = TemperatureUnit("Fahrenheit", "Celsius")
                    isExpanded = false
                }
            ) {
                Text("Fahrenheit")
            }
            DropdownMenuItem(
                onClick = {
                    selectedTemperatureUnit = TemperatureUnit("Celsius", "Fahrenheit")
                    isExpanded = false
                }
            ) {
                Text("Celsius")
            }
        }
    }
}


private fun getProperDisplayName(displayName: String?) = displayName?.split(",")?.firstOrNull()

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun TodayWeatherCard(
    temp: TemperatureResponse,
    onDetailsButtonClicked: (TemperatureResponse) -> Unit,
) {

    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable {
                    showDialog = true
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.map),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "World Weather Map",
                fontWeight = FontWeight.Light,
                color = Dark20,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Center)
            )
            Icon(
                painter = painterResource(id = R.drawable.pin), contentDescription = null,
                modifier = Modifier
                    .align(BottomEnd)
                    .padding(16.dp), tint = Unspecified
            )
        }
    }
    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                WeatherRadarWebView(url = "https://map.worldweatheronline.com/temperature?lat=36.884804&lng=30.704044")
            }
        }
    }
}


@Composable
private fun CardInformation(temp: TemperatureResponse) {
    val weatherDesc = GetWeatherCodes(temp)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row {
            val day = 0
            val forecastMini = temp.daily.temperature_2m_min[day].toInt().toString()

            Text(
                text = forecastMini + temp.daily_units.temperature_2m_min[0],
                fontWeight = FontWeight.Thin,
                fontSize = 36.sp,
                color = Color.Black
            )
            Icon(
                painter = painterResource(id = R.drawable.arrowdropdown), contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .align(CenterVertically), tint = Dark20.copy(alpha = 0.8f)
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = weatherDesc,
            fontSize = 24.sp,
            color = Color.Black
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        )
        {
            val day = 0
            val maximumDegree = temp.daily.temperature_2m_max[day].toInt().toString()
            Icon(
                painter = painterResource(id = R.drawable.arrowdropup), contentDescription = null,
                modifier = Modifier.size(40.dp), tint = Dark20
            )
            Text(
                text = maximumDegree + temp.daily_units.temperature_2m_max[0],
                fontWeight = FontWeight.Thin,
                fontSize = 36.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ClickableCardDemo(searchedLocation: SearchedLocation) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                context.startActivity(LightningMapActivity.newIntent(context, searchedLocation))
            }
            .padding(16.dp),
        backgroundColor = Color.Black,
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp), contentAlignment = Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.map),
                contentDescription = null, contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Lightning Hunt",
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
            )
            Icon(
                painter = painterResource(id = R.drawable.pin), contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .align(BottomEnd), tint = Unspecified
            )

        }
    }
}

@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
    onClose: () -> Unit
) {
    var isSearchExpanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var query by remember { mutableStateOf("") }
    val gradient = Brush.horizontalGradient(
        colors = if (isSearchExpanded) listOf(Color(0xFF2F80ED), Color(0xFF56CCF2))
        else listOf(Color.Transparent, Color.Transparent)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 16.dp)
            .background(
                brush = gradient,
                shape = RoundedCornerShape(28.dp)
            ),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AnimatedVisibility(
            visible = isSearchExpanded,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it })
        ) {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                    if (query.length >= 3) {
                        onSearch(query)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 18.dp),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                ),
                placeholder = {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Search a city or airport",
                        color = Color.Black.copy(alpha = 0.7f)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    capitalization = KeyboardCapitalization.Characters
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus(true)
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Spiro,
                )
            )
        }

        IconButton(
            onClick = {
                isSearchExpanded = !isSearchExpanded
                if (!isSearchExpanded) {
                    query = ""
                }
                onClose()
            },
            modifier = Modifier
                .padding(end = 16.dp)
                .rotate(if (isSearchExpanded) 1f else 360f)
        ) {
            Icon(
                imageVector = if (isSearchExpanded) Icons.Default.Clear else Icons.Default.Search,
                contentDescription = "Search",
                tint = if (isSearchExpanded) Color.Black else Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}