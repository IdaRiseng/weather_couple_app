package com.example.wouple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.WeatherCondition.RAINY
import com.example.wouple.WeatherCondition.SNOWY
import com.example.wouple.formatter.DateFormatter
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val temp = intent.getParcelableExtra<TemperatureResponse>("temp")
            temp?.let { SecondCardView(it) }
        }
    }
}

@Composable
fun SecondCardView(temp: TemperatureResponse) {
    val scrollStateOne = rememberScrollState()
    val isDay = temp.current_weather.is_day == 1
    val backgroundResource = when {
        (temp.current_weather.weathercode in listOf(0, 1, 2) && !isDay) -> R.drawable.nightone
        (temp.current_weather.weathercode in listOf(0, 1, 2) && isDay) -> R.drawable.sky
        (temp.current_weather.weathercode == 3) -> R.drawable.overcast
        (temp.current_weather.weathercode in listOf(51, 53, 55)) -> R.drawable.drizzle
        (temp.current_weather.weathercode in listOf(61, 63, 65)) -> R.drawable.rainybackground
        (temp.current_weather.weathercode in listOf(85, 86)) -> R.drawable.snowshowers
        else -> R.drawable.sky
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .paint(
                painter = painterResource(id = backgroundResource),
                contentScale = ContentScale.Crop
            )
            .verticalScroll(scrollStateOne),
    ) {
        val index =
            temp.hourly.time.map { LocalDateTime.parse(it).hour }.indexOf(LocalDateTime.now().hour)
        val feelsLike = index.let { temp.hourly.apparent_temperature[it].toInt() }
        val humidity = index.let { temp.hourly.relativehumidity_2m[it] }
        val dewPoint = index.let { temp.hourly.dewpoint_2m[it].toInt() }
        val rainIndex =
            temp.daily.time.map { LocalDate.parse(it).dayOfWeek }.indexOf(LocalDate.now().dayOfWeek)
        val rainFall = rainIndex.let { temp.daily.rain_sum[rainIndex].toInt() }
        val visibilityInMeters = index.let { temp.hourly.visibility[it].toInt() }

        LocationView(temp)
        Row() {
            SunRise(temp)
            SunSet(temp)
        }
        HourlyForecastView(temp)
        WeeklyForeCastView(temp)
        Spacer(modifier = Modifier.padding(6.dp))
        ExtraCards(
            "Feels Like",
            feelsLike.let {
                temp.hourly.apparent_temperature[it].toInt().toString()
            } + temp.hourly_units.apparent_temperature,
            painterResource(id = R.drawable.temperaturea))
        ExtraCards(
            "Rainfall",
            rainFall.toString() + temp.daily_units.rain_sum,
            painterResource(id = R.drawable.drop)
        )
        ExtraCards(
            "Humidity",
            temp.hourly_units.relativehumidity_2m + humidity.toString(),
            painterResource(id = R.drawable.humidity)
        )

        ExtraCards(
            "Visibility",
            visibilityInMeters.toString() + temp.hourly_units.visibility,
            painterResource(id = R.drawable.eye)
        )
        ExtraCards(
            "Dew Point",
            dewPoint.toString() + temp.hourly_units.temperature_2m,
            painterResource(id = R.drawable.dew)
        )
    }
}

@Composable
fun TildeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 500.dp)
    ) {
        // Draw the bottom half in white color
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        )
        {

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
                -curveWidth, curveHeight,
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

}
@Composable
fun LocationView(temp: TemperatureResponse) {
    Column(
        modifier = Modifier
            .padding(top = 40.dp, bottom = 20.dp)
            .fillMaxWidth(1f),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = "OSLO",
            fontSize = 50.sp,
            color = Color.Black,
        )
        Text(
            text = "${temp.current_weather.temperature.toInt()}°",
            color = Color.Black,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp)
        )
        val weatherDescriptions = mapOf(
            0 to "Clear Sky",
            1 to "Mainly Clear",
            2 to "Partly Cloudy",
            3 to "Overcast",
            45 to "Foggy",
            48 to "Rime Fog",
            51 to "Light Drizzle",
            53 to "Moderate Drizzle",
            55 to "Heavy Drizzle",
            56 to "Light Freezing Drizzle",
            57 to "Heavy Freezing Drizzle",
            61 to "Slight Rain",
            63 to "Moderate Rain",
            65 to "Heavy Rain",
            66 to "Light Freezing Rain",
            67 to "Heavy Freezing Rain",
            71 to "Light Snowfall",
            73 to "Moderate Snowfall",
            75 to "Heavy Snowfall",
            77 to "Snow Grains",
            80 to "Slight Rain Showers",
            81 to "Moderate Rain Showers",
            82 to "Heavy Rain Showers",
            85 to "Slight Snow Showers",
            86 to "Heavy Snow Showers",
            95 to "Thunderstorm"
        )
        val weatherCode = temp.current_weather.weathercode
        val weatherDescription = weatherDescriptions[weatherCode] ?: "Unknown"

        Text(
            text = weatherDescription,
            color = Color.Black,
            fontSize = 24.sp
        )
    }
}

@Composable
fun HourlyForecastView(temp: TemperatureResponse) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(vertical = 14.dp, horizontal = 12.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Dark20)
            .padding(16.dp),

        ) {
        Row(modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)) {

            Icon(
                painter = painterResource(id = R.drawable.twentyfour), contentDescription = null,
                tint = Whitehis
            )

            Text(
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(start = 10.dp),
                text = "HOURLY FORECAST",
                color = Corn
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
            val currentHour = LocalTime.now().hour
            for (index in currentHour..(currentHour + 23)) {
                val time = DateFormatter.formatDate(temp.hourly.time[index])
                val temperature = temp.hourly.temperature_2m[index].toInt().toString()
                val isDaytime = temp.hourly.is_day.getOrNull(index) == 1

                if (isDaytime) {
                    val hourlyWeatherCondition = when (temp.hourly.weathercode[index]) {
                        1, 2 -> WeatherCondition.SUNNY
                        3, 4 -> WeatherCondition.CLOUDY
                        66 -> RAINY
                        else -> WeatherCondition.SUNNY // Set a default weather condition in case of an unknown code
                    }
                    Hours(time, temperature, hourlyWeatherCondition)
                }
                if (!isDaytime) {
                    val hourlyWeatherConditionNight = when (temp.hourly.weathercode[index]) {
                        1, 2 -> WeatherCondition.NIGHT
                        3, 4 -> WeatherCondition.NIGHT
                        else -> {
                            WeatherCondition.NIGHT
                        }
                    }
                    Hours(time, temperature, hourlyWeatherConditionNight)
                }
            }

        }
    }
}

@Composable
fun SunRise(temp: TemperatureResponse) {
    val now = LocalDateTime.now().toLocalDate()
    val todaySunrise = temp.daily.sunrise.firstOrNull {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .toLocalDate()
            .isEqual(now)
    }

    val formattedSunrise = todaySunrise?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .format(DateTimeFormatter.ofPattern("HH:mm"))
    } ?: ""

    Row(
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 22.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp))
            .background(Dark20)
            .padding(6.dp),
        horizontalArrangement = Arrangement.Start
    ) {

        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "Sunrise",
            fontSize = 16.sp,
            color = Spiro
        )
        todaySunrise?.let {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = formattedSunrise,
                fontSize = 16.sp,
                color = Corn
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.sunrise),
            contentDescription = null,
            modifier = Modifier.padding(start = 4.dp),
            tint = Tangerine
        )


    }
}

@Composable
fun SunSet(temp: TemperatureResponse) {
    val now = LocalDateTime.now().toLocalDate()
    val todaySunrise = temp.daily.sunset.firstOrNull {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .toLocalDate()
            .isEqual(now)
    }

    val formattedSunset = todaySunrise?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .format(DateTimeFormatter.ofPattern("HH:mm"))
    } ?: ""
    Row(
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 2.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp))
            .background(Dark20)
            .padding(6.dp),
        horizontalArrangement = Arrangement.End
    ) {

        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "Sunset",
            fontSize = 16.sp,
            color = Spiro
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = formattedSunset,
            fontSize = 16.sp,
            color = Corn
        )
        Icon(
            painter = painterResource(id = R.drawable.sunrise), contentDescription = null,
            modifier = Modifier.padding(start = 4.dp), tint = Tangerine
        )


    }

}

@Composable
fun Hours(time: String, temperature: String, hourlyWeatherCondition: WeatherCondition) {

    Column(
        modifier = Modifier
            .padding(4.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            text = time,
            color = Spiro,
            fontSize = 16.sp
        )
        Image(
            painter = painterResource(id = hourlyWeatherCondition.imageResourceId),
            contentDescription = null,
            modifier = Modifier.size(26.dp)
        )
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            text = "$temperature°",
            color = Whitehis,
            fontSize = 17.sp
        )
    }
}

enum class WeatherCondition(val imageResourceId: Int) {
    SUNNY(R.drawable.sun),
    RAINY(R.drawable.rainyday),
    CLOUDY(R.drawable.cloudydaylight),
    SNOWY(R.drawable.cloudsnow),
    NIGHT(R.drawable.nighttime)
    // Add more weather conditions and their associated images as needed
}

@Composable
fun WeeklyForeCastView(temp: TemperatureResponse) {
    Column(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 14.dp)
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Dark20)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sevendays), contentDescription = null,
                tint = Whitehis
            )


            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(CenterVertically),
                text = "WEEKLY FORECAST",
                color = Corn
            )
            Text(
                modifier = Modifier
                    .padding(start = 40.dp),
                text = "Min",
                color = Spiro.copy(alpha = 0.9f),
                textAlign = TextAlign.End,
            )
            Text(
                modifier = Modifier
                    .padding(start = 50.dp),
                text = "Max",
                color = Spiro,
                textAlign = TextAlign.End,
            )


        }
        Divider(
            color = Spiro.copy(alpha = 0.8f),
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        for (days in 0..6) {
            val daysOfWeek = temp.daily.time[days]
            val somessd = LocalDate.parse(daysOfWeek).dayOfWeek.toString()
            val forecastMin = temp.daily.temperature_2m_min[days].toInt()
            val forecastMax = temp.daily.temperature_2m_max[days].toInt()

            val weatherCondition = when (temp.daily.weathercode[days]) {
                in 0..2 -> WeatherCondition.SUNNY
                3, 4 -> WeatherCondition.CLOUDY
                in listOf(51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82) -> RAINY
                in listOf(71, 73, 75, 77) -> SNOWY
                else -> WeatherCondition.SUNNY // Set a default weather condition in case of an unknown code
            }

            val imageResource = weatherCondition.imageResourceId
            Column {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = somessd.lowercase()
                            .replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(locale = Locale.ENGLISH) else it.toString()
                            },
                        fontSize = 16.sp,
                        color = Whitehis
                    )

                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        modifier = Modifier.padding(start = 26.dp),
                        text = "$forecastMin°",
                        color = Whitehis,
                        fontSize = 18.sp,

                        )

                    Text(
                        modifier = Modifier,
                        text = "$forecastMax°",
                        color = Whitehis,
                        fontSize = 18.sp,

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
fun ExtraCards(
    Text: String,
    Numbers: String,
    Icon: Painter
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 2.dp)
            .size(120.dp)
            .padding(start = 16.dp, end = 16.dp)
            .shadow(1.dp, RoundedCornerShape(20.dp))
            .background(Dark20),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = CenterVertically

        ) {

            Text(
                modifier = Modifier,
                text = Text,
                color = Spiro,
                fontSize = 22.sp,

                )

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = Numbers,
                color = Corn,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Icon(
                painter = Icon,
                contentDescription = null, tint = Spiro,
                modifier = Modifier
                    .size(48.dp)
                    .padding(start = 16.dp)
            )

        }

        Text(
            modifier = Modifier,
            text = "Expected Today",
            color = Whitehis,
            fontSize = 16.sp
        )

    }
}
