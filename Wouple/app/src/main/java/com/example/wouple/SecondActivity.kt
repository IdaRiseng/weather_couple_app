package com.example.wouple

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.formatter.DateFormatter
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.*
import java.time.LocalDateTime

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val temp = intent.getParcelableExtra<TemperatureResponse>("temp")
            temp?.let { SecondCardView(it) }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    WoupleTheme {
        SecondCardView(temp = )
    }
}*/

@Composable
fun SecondCardView(temp: TemperatureResponse) {
    val scrollStateOne = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .paint(
                when (temp.current_weather.weathercode) {
                    0, 1, 2 -> painterResource(id = R.drawable.sky)
                    3 -> painterResource(id = R.drawable.overcast)
                    51, 53, 55 -> painterResource(id = R.drawable.drizzle)
                    61, 63, 65 -> painterResource(id = R.drawable.rainybackground)

                    85, 86 -> painterResource(id = R.drawable.snowshowers)


                    else -> {
                        painterResource(id = R.drawable.backgroundone)
                    }
                }, contentScale = ContentScale.Crop
            )
            .verticalScroll(scrollStateOne),
        horizontalAlignment = CenterHorizontally
    ) {
        Locationview(temp)
        HourlyForecastView(temp)
        WeeklyForeCastView(temp)
        RainFallView(temp)
        HumidityView(temp)
    }

}

@Composable
fun Locationview(temp: TemperatureResponse) {
    val index =
        temp.hourly?.time?.map { LocalDateTime.parse(it).hour }?.indexOf(LocalDateTime.now().hour)
    val realTemperature = index?.let { temp.hourly?.temperature_2m?.get(it)?.toInt() }
    Column(
        modifier = Modifier
            .padding(top = 40.dp, bottom = 20.dp)
            .fillMaxWidth(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "TOKYO",
            fontSize = 50.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal
        )
        Text(
            text = temp.current_weather.temperature.toInt().toString() + "°",
            color = Color.Black,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp)
        )
        when (temp.current_weather.weathercode) {
            0 -> Text(
                text = "Clear Sky",
                color = Color.Black,
                fontSize = 24.sp,
            )

            1 -> Text(
                text = "Mainly Clear",
                color = Color.Black,
                fontSize = 24.sp,
            )

            2 -> Text(
                text = "Partly Cloudy",
                color = Color.Black,
                fontSize = 24.sp,
            )

            3 -> Text(
                text = "Overcast",
                color = Color.Black,
                fontSize = 24.sp,
            )

            45 -> Text(
                text = "Foggy",
                color = Color.Black,
                fontSize = 24.sp,
            )

            48 -> Text(
                text = "Rime Fog",
                color = Color.Black,
                fontSize = 24.sp,
            )

            51 -> Text(
                text = "Light Drizzle",
                color = Color.Black,
                fontSize = 24.sp,
            )

            53 -> Text(
                text = "Moderate Drizzle",
                color = Color.Black,
                fontSize = 24.sp,
            )

            55 -> Text(
                text = "Heavy Drizzle",
                color = Color.Black,
                fontSize = 24.sp,
            )

            56 -> Text(
                text = "Light Freezing Drizzle",
                color = Color.Black,
                fontSize = 24.sp,
            )

            57 -> Text(
                text = "Heavy Freezing Drizzle",
                color = Color.Black,
                fontSize = 24.sp,
            )

            61 -> Text(
                text = "Slight Rain",
                color = Color.Black,
                fontSize = 24.sp,
            )

            63 -> Text(
                text = "Moderate Rain",
                color = Color.Black,
                fontSize = 24.sp,
            )

            65 -> Text(
                text = "Heavy Rain",
                color = Color.Black,
                fontSize = 24.sp,
            )

            66 -> Text(
                text = "Light Freezing Rain",
                color = Color.Black,
                fontSize = 24.sp,
            )

            67 -> Text(
                text = "Heavy Freezing Rain",
                color = Color.Black,
                fontSize = 24.sp,
            )

            71 -> Text(
                text = "Light Snowfall",
                color = Color.Black,
                fontSize = 24.sp,
            )

            73 -> Text(
                text = "Moderate Snowfall",
                color = Color.Black,
                fontSize = 24.sp,
            )

            75 -> Text(
                text = "Heavy Snowfall",
                color = Color.Black,
                fontSize = 24.sp,
            )

            77 -> Text(
                text = "Snow Grains",
                color = Color.Black,
                fontSize = 24.sp,
            )

            80 -> Text(
                text = "Slight Rain Showers",
                color = Color.Black,
                fontSize = 24.sp,
            )

            81 -> Text(
                text = "Moderate Rain Showers",
                color = Color.Black,
                fontSize = 24.sp,
            )

            82 -> Text(
                text = "Heavy Rain Showers",
                color = Color.Black,
                fontSize = 24.sp,
            )

            85 -> Text(
                text = "Slight Snow Showers",
                color = Color.Black,
                fontSize = 24.sp,
            )

            86 -> Text(
                text = "Heavy Snow Showers",
                color = Color.Black,
                fontSize = 24.sp,
            )

            95 -> Text(
                text = "Thunderstorm",
                color = Color.Black,
                fontSize = 24.sp,
            )

        }
    }
}

@Composable
fun HourlyForecastView(temp: TemperatureResponse) {

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 12.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Dark20)
            .padding(16.dp),

        ) {
        Row(modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)) {


            Icon(
                painter = painterResource(id = R.drawable.clock),
                contentDescription = "",
                tint = Corn.copy(alpha = 0.7f),
                modifier = Modifier
                    .size(20.dp)
            )
            Text(
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(start = 4.dp),
                text = "HOURLY FORECAST",
                color = Whitehis.copy(alpha = 0.9f)
            )
        }
        Divider(
            color = Spiro.copy(alpha = 0.8f),
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 0.dp)
                .horizontalScroll(scrollState),
        ) {
            for (hours in 0..23) {
                val time = DateFormatter.formatDate(temp.hourly?.time?.get(hours).toString())
                Hours(time)
            }
        }
    }
}

@Composable
fun Hours(time: String) {

    Column(
        modifier = Modifier
            .padding(4.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            text = time.toString(), color = Whitehis
        )
        Image(
            painter = painterResource(id = R.drawable.rainyday),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            text = "-5°", color = Whitehis,
            fontSize = 18.sp
        )
    }
}

@Composable
fun WeeklyForeCastView(temp: TemperatureResponse) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Dark20)
            .padding(16.dp)
    ) {
        var text by remember { mutableStateOf(("")) }
        val focusManager = LocalFocusManager.current
        val activity = LocalContext.current
        val intent = Intent(activity, MainActivity::class.java)
        val data = intent.getStringExtra("location")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 8.dp, start = 18.dp, bottom = 8.dp)
        ) {


            Icon(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "",
                tint = Whitehis.copy(alpha = 0.9f),
                modifier = Modifier
                    .size(20.dp)
            )

            Text(
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(start = 4.dp),
                text = "WEEKLY FORECAST",
                color = Whitehis.copy(alpha = 0.9f)
            )


        }
        Divider(
            color = Spiro.copy(alpha = 0.8f),
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        for (days in 1..7) {
            Column() {


                Row(
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "Monday",
                        fontSize = 16.sp,
                        color = Color.LightGray
                    )
                    Image(
                        painter = painterResource(id = R.drawable.cloudsnow),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        modifier = Modifier,
                        text = "-5°",
                        color = Color.LightGray,
                        fontSize = 20.sp
                    )


                }
                Divider(
                    color = Spiro.copy(alpha = 0.5f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                )
            }

        }
    }
}

@Composable
fun RainFallView(temp: TemperatureResponse) {

    val index =
        temp.hourly?.time?.map { LocalDateTime.parse(it).hour }?.indexOf(LocalDateTime.now().hour)
    val feelsLike = index?.let { temp.hourly?.apparent_temperature?.get(it)?.toInt() }

    Row(modifier = Modifier.padding(top = 8.dp)) {
        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                    .background(Dark20)
                    .padding(16.dp)
                    .height(90.dp)
                    .fillMaxWidth(),
                horizontalAlignment = CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(top = 10.dp, bottom = 2.dp),

                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.temperaturea),
                        contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(top = 2.dp),
                        Tangerine.copy(alpha = 0.8f)

                    )

                    Text(
                        text = "FEELS LIKE",
                        color = Corn
                    )
                }
                Text(
                    modifier = Modifier,
                    text = feelsLike.toString() + "°",
                    color = Corn,
                    fontSize = 32.sp,

                    )
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            val index = temp.hourly?.time?.map { LocalDateTime.parse(it).hour }
                ?.indexOf(LocalDateTime.now().hour)
            val rainFall = index?.let { temp.hourly?.precipitation?.get(it)?.toInt() }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .fillMaxWidth()
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                    .background(Dark20)
                    .padding(16.dp)
                    .height(90.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.drop), contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(top = 2.dp),
                        Tangerine.copy(alpha = 0.8f),
                    )
                    Text(
                        modifier = Modifier,
                        text = "RAINFALL",
                        color = Corn
                    )
                }
                Text(
                    modifier = Modifier,
                    text = rainFall.toString() + " mm",
                    color = Corn,
                    fontSize = 32.sp
                )

                Text(
                    modifier = Modifier,
                    text = "Expected Today",
                    color = Corn
                )

            }
        }
    }
}

@Composable
fun HumidityView(temp: TemperatureResponse) {

    val index =
        temp.hourly?.time?.map { LocalDateTime.parse(it).hour }?.indexOf(LocalDateTime.now().hour)
    val humidity = index?.let { temp.hourly?.relativehumidity_2m?.get(it)?.toInt() }

    Row(modifier = Modifier.padding(top = 8.dp)) {
        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                    .background(Dark20)
                    .padding(16.dp)
                    .height(90.dp)
                    .fillMaxWidth(),
                horizontalAlignment = CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(top = 10.dp, bottom = 2.dp),

                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.humidity),
                        contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(top = 2.dp),
                        Tangerine.copy(alpha = 0.8f)

                    )

                    Text(
                        text = "HUMIDITY",
                        color = Corn
                    )
                }
                Text(
                    modifier = Modifier,
                    text = "%" + humidity.toString(),
                    color = Corn,
                    fontSize = 32.sp,

                    )
                val index = temp.hourly?.time?.map { LocalDateTime.parse(it).hour }
                    ?.indexOf(LocalDateTime.now().hour)
                val dewPoint = index?.let { temp.hourly?.dewpoint_2m?.get(it)?.toInt() }

                Text(
                    modifier = Modifier,
                    text = "DewPoint is " + dewPoint.toString() + "°",
                    color = Corn,
                    fontSize = 14.sp,

                    )
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            val index = temp.hourly?.time?.map { LocalDateTime.parse(it).hour }
                ?.indexOf(LocalDateTime.now().hour)
            val visibilityInMeters = index?.let { temp.hourly?.visibility?.get(it)?.toInt() }
            val visibilityInKilometers = visibilityInMeters?.times(0.001)?.toInt()

            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .fillMaxWidth()
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                    .background(Dark20)
                    .padding(16.dp)
                    .height(90.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.eye), contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(top = 2.dp),
                        Tangerine.copy(alpha = 0.8f),
                    )
                    Text(
                        modifier = Modifier,
                        text = "VISIBILITY",
                        color = Corn
                    )
                }
                visibilityInKilometers?.let {
                    Text(
                        modifier = Modifier,
                        text = it.toString() + " km",
                        color = Corn,
                        fontSize = 28.sp
                    )
                }

                Text(
                    modifier = Modifier,
                    text = "Expected Today",
                    color = Corn
                )

            }
        }
    }
}