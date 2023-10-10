package com.example.wouple.activities.mainActivity

import android.content.Intent
import android.transition.Visibility
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
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
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.wouple.R
import com.example.wouple.activities.detailActivity.WeatherCondition
import com.example.wouple.elements.GetWeatherCodes
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.SearchedLocations
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Dark20
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.Whitehis


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
    locations: List<SearchedLocations>?,
    onSearch: (String) -> Unit,
    searchedLocation: MutableState<SearchedLocations?>,
    onLocationButtonClicked: (SearchedLocations) -> Unit,
    onDetailsButtonClicked: (TemperatureResponse) -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
                modifier = Modifier
                    .align(TopCenter)
                    .padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp),
                    text = temp.current_weather.temperature.toInt()
                        .toString() + temp.hourly_units.temperature_2m[0],
                    fontWeight = FontWeight.Thin,
                    fontSize = 70.sp,
                    color = Color.White,
                )
                Text(
                    text = getProperDisplayName(searchedLocation.value?.display_name) ?: "N/D",
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
                    modifier = Modifier.size(60.dp),
                    alignment = Alignment.BottomCenter
                )
            }
            Button(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(BottomCenter),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Whitehis),
                onClick = {
                    onDetailsButtonClicked(temp)
                }) {
                Text(text = "Forecast details")
            }
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
                            verticalAlignment = CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.pin),
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
                .padding(bottom = 16.dp)
        ) {
            ClickableCardDemo()
        }
    }
}

@Composable
fun DropDownMenu() {
    var isExpanded by remember { mutableStateOf(false) }
    Box {
        IconButton(
            onClick = { isExpanded = !isExpanded }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menuicon), contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
        DropdownMenu(expanded = isExpanded,
            onDismissRequest = { isExpanded = false })
        {
            DropdownMenuItem(onClick = { /*TODO*/ })
            {
                Column {
                    Text(text = "Celcius C")
                    Text(text = "Fahreneight F")
                }
            }
        }
    }
}

private fun getProperDisplayName(displayName: String?) = displayName?.split(",")?.firstOrNull()

@Composable
private fun TodayWeatherCard(
    temp: TemperatureResponse,
    onDetailsButtonClicked: (TemperatureResponse) -> Unit
) {
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
        ) {
            Image(
                painter = painterResource(id = R.drawable.snowshowers),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "Today's Summary",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(TopCenter)
                    .padding(8.dp)
            )
            CardInformation(temp)
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
        Row() {
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
fun ClickableCardDemo() {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        backgroundColor = Color.Black,
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp
    ) {
        // Card content goes here
        Image(
            painter = painterResource(id = R.drawable.map),
            contentDescription = null, contentScale = ContentScale.Crop
        )
        Text(
            text = "Lightning Hunt",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )

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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = if (isSearchExpanded) Color.White else Color.Transparent,
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
                        color = Color.Gray
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    capitalization = KeyboardCapitalization.Characters
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        /* Handles search button click */
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
                .size(32.dp)
                .rotate(if (isSearchExpanded) 1f else 360f)
        ) {
            Icon(
                imageVector = if (isSearchExpanded) Icons.Default.Clear else Icons.Default.Search,
                contentDescription = "Search",
                tint = if (isSearchExpanded) Color.Black else Color.White,
                modifier = Modifier.size(42.dp)
            )
        }
    }
}

/*@Composable
fun TildeScreen(
    temp: TemperatureResponse,
    onDetailsButtonClicked: (TemperatureResponse) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 350.dp),
        Alignment.TopCenter
    ) {
        // Draw the bottom half in white color
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically)
            {

                Text(
                    text = "OSLO",
                    fontSize = 40.sp,
                    color = Dark20,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                shape = CircleShape,
                onClick = {
                    onDetailsButtonClicked(temp)
                }) {
                Text(text = "Weather details")
            }
        }

        // Draw the tilde sign (top half) in white color
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val tildePath = Path()
            val curveWidth = size.width / 8
            val curveHeight = size.height / -12

            // Start from the top-left corner of the canvas
            tildePath.moveTo(-30f, 0f)

            // First curve (bottom)
            tildePath.cubicTo(
                -curveWidth, -curveHeight,
                curveWidth * -1, curveHeight * 8,
                curveWidth * 11, 12f
            )

            // Draw the tilde sign in white color
            drawPath(
                path = tildePath,
                brush = SolidColor(Color.White)
            )
        }
    }
}*/
/*
@Composable
fun SetLocationTextField(onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf(("")) }
    val focusManager = LocalFocusManager.current


    TextField(
        value = text.uppercase(),
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Spiro,
            unfocusedBorderColor = Bubbles.copy(alpha = 0.6f),
            textColor = Corn
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { focusManager.clearFocus() }
        ),
        onValueChange = { newText ->
            text = newText
            onSearch(text)
        },
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Bubbles,
                text = "Set Location"
            )
        },
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun TimeView() {
    val xxx = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .background(Dark10, shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(top = 20.dp),
            text = "TIME",
            color = Corn.copy(alpha = 0.9f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier,
            fontSize = 45.sp,
            text = xxx.toString(),
            color = Bubbles.copy(alpha = 1f),
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_time_foreground),
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .alpha(0.9f),
            tint = Tangerine
        )
    }
}


@Composable
fun TemperatureView(temp: TemperatureResponse) {
    val activity = LocalContext.current
    Column(modifier = Modifier
        .clickable {
            val intent = Intent(activity, SecondActivity::class.java)
            intent.putExtra("temp", temp)
            activity.startActivity(intent)
        }
        .padding(4.dp)
        .fillMaxSize()
        .background(Dark10, RoundedCornerShape(20.dp)),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(top = 20.dp),
            text = "TEMPERATURE",
            color = Corn.copy(alpha = 0.9f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Row {
            Text(
                fontSize = 50.sp,
                text = temp.current_weather.temperature.toInt().toString(),
                color = Bubbles.copy(alpha = 1f),
                modifier = Modifier.padding(start = 15.dp)
            )
            Text(
                fontSize = 32.sp,
                text = temp.hourly_units.temperature_2m,
                color = Bubbles.copy(alpha = 1f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.thermo), contentDescription = "",
            modifier = Modifier
                .alpha(0.9f)
                .padding(bottom = 4.dp)
                .size(50.dp),
            tint = Tangerine
        )
    }
}

@Composable
fun WindView(temp: TemperatureResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .background(Dark10, RoundedCornerShape(20.dp)),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(top = 10.dp),
            text = "WIND",
            color = Corn.copy(alpha = 0.9f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier,
            fontSize = 30.sp,
            text = temp.current_weather.windspeed.toInt().toString() +" " + temp.hourly_units.windspeed_10m,
            color = Bubbles.copy(alpha = 1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.wind), contentDescription = "",
            modifier = Modifier
                .alpha(0.9f)
                .size(38.dp)
                .padding(top = 6.dp, bottom = 10.dp),
            tint = Tangerine
        )
    }
}

@Composable
fun ImageWeatherView(weatherImage: Int) {
    val activity = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .background(Dark10, RoundedCornerShape(20.dp))
    ) {
        Image(painter = painterResource(id = weatherImage),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clickable {
                    val intent = Intent(activity, SecondActivity::class.java)
                    activity.startActivity(intent)
                }
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}
 */
