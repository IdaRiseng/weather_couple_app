package com.example.wouple.activities.mainActivity

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
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
import com.example.wouple.R
import com.example.wouple.activities.detailActivity.Hours
import com.example.wouple.activities.detailActivity.WeatherCondition
import com.example.wouple.formatter.DateFormatter
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.ColdBlue
import com.example.wouple.ui.theme.Corn
import com.example.wouple.ui.theme.Dark20
import com.example.wouple.ui.theme.Orange
import com.example.wouple.ui.theme.Pastel
import com.example.wouple.ui.theme.Spir
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.Whitehis
import com.example.wouple.ui.theme.Yellow20
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import java.time.LocalTime
import kotlin.math.PI


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
    temp2: TemperatureResponse,
    onSearch: (String) -> Unit,
    onDetailsButtonClicked: (TemperatureResponse) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
        /* .paint(
             when (temp1.current_weather.is_day) {
                 1 -> painterResource(id = R.drawable.sky)
                 else -> return
             },
             contentScale = ContentScale.None
         ),*/
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
                    .align(Alignment.TopCenter)
                    .padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    text = "17°",
                    fontSize = 80.sp,
                    color = Color.White,
                )
                Text(
                    text = "NEW YORK",
                    fontSize = 50.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(id = R.drawable.sun), contentDescription = null,
                    modifier = Modifier.size(60.dp), alignment = Alignment.BottomEnd
                )
                Button(
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(Whitehis),
                    onClick = {
                        onDetailsButtonClicked(temp)
                    }) {
                    Text(text = "Forecast details")
                }
                /*  val pagerState = rememberPagerState()
                  HorizontalPager(state = pagerState, count = 2, modifier = Modifier)
                  { page ->
                      when (page) {
                          0 -> TildeScreen(temp = temp1,onDetailsButtonClicked )
                          1 -> TildeScreen(temp = temp2, onDetailsButtonClicked)
                      }
                  }*/
            }
            Column(modifier = Modifier.fillMaxSize()) {
                SearchBar()
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
/*@Composable
fun ExpandableCards(onDetailsButtonClicked: (TemperatureResponse) -> Unit) {
    var isCard1Expanded by remember { mutableStateOf(false) }
    var isCard2Expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        ExpandableCard(
            isExpanded = isCard1Expanded,
            onToggle = { isCard1Expanded = !isCard1Expanded },
            title = "Weather Details",
            content = {
                Text(
                    text = "This is the content of Card 1",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ExpandableCard(
            isExpanded = isCard2Expanded,
            onToggle = { isCard2Expanded = !isCard2Expanded },
            title = "Card 2",
            content = {
                Text(
                    text = "This is the content of Card 2",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        )
    }
}*/

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
            CardInformation()
        }
    }
}

@Composable
private fun CardInformation() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row() {
            Text(
                text = "7°",
                fontSize = 40.sp,
                color = Color.Black
            )
            Icon(
                painter = painterResource(id = R.drawable.arrowdropdown), contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .align(CenterVertically)
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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
            Icon(
                painter = painterResource(id = R.drawable.arrowdropup), contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = "19°",
                fontSize = 40.sp,
                color = Color.Black
            )
        }
    }
}

/*@Composable
fun ExpandableCard(
    isExpanded: Boolean,
    onToggle: () -> Unit,
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures { Offset ->
                    onToggle()
                }
            },
        elevation = 4.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row() {

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = null, tint = Orange
                )
            }

            if (isExpanded) {
                content()
            }
        }
    }
}
*/
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
            painter = painterResource(id = R.drawable.drizzle),
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
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }
    var isSearchExpanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = if (isSearchExpanded) Color.White else Color.Transparent,
                shape = RoundedCornerShape(28.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AnimatedVisibility(
            visible = isSearchExpanded,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it })
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it.uppercase() },
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
                    searchText = ""
                }
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

@Composable
private fun HorizontalWave(
    phase: MutableState<Float>,
    alpha: Float,
    amplitude: Float,
    frequency: Float
) {
    Canvas(
        modifier = Modifier.fillMaxWidth(),
        onDraw = {
            val wavePath = Path()
            val centerY = size.height / 2f
            var x = 0

            wavePath.moveTo(0f, centerY + amplitude)

            while (x < size.width.toInt()) {
                val y =
                    centerY + amplitude * kotlin.math.cos(2 * PI * frequency * x / size.width + phase.value)
                wavePath.lineTo(x.toFloat(), y.toFloat())
                x++
            }
            wavePath.lineTo(x.toFloat(), centerY + amplitude)

            drawPath(
                path = wavePath,
                color = Color.White,
                alpha = alpha,
                style = Fill
            )
        }
    )
}

@Composable
private fun rememberPhaseState(startPosition: Float): MutableState<Float> {
    val step: Long = 100 //countdown seconds
    val phase = remember { mutableStateOf(startPosition) }
    LaunchedEffect(phase, step) {
        while (isActive) {
            phase.value = phase.value + 0.02f
            delay(step)
        }
    }
    return phase
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
