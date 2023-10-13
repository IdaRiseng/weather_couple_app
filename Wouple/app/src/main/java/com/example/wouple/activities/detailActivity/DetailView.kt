package com.example.wouple.activities.detailActivity

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.android.style.BaselineShiftSpan
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.formatter.DateFormatter
import com.example.wouple.model.api.SearchedLocations
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Corn
import com.example.wouple.ui.theme.Dark20
import com.example.wouple.ui.theme.PagerColor
import com.example.wouple.ui.theme.Spir
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.Whitehis
import com.example.wouple.ui.theme.Yellow20
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SecondCardView(temp: TemperatureResponse, searchedLocations: SearchedLocations) {
    val scrollStateOne = rememberScrollState()
    val isDay = temp.current_weather.is_day == 1
    val backgroundResource = when {
        (temp.current_weather.weathercode in listOf(0, 1, 2) && !isDay) -> R.drawable.nightone
        (temp.current_weather.weathercode in listOf(0, 1, 2) && isDay) -> R.drawable.sky
        (temp.current_weather.weathercode == 3) -> R.drawable.overcast
        (temp.current_weather.weathercode in listOf(51, 53, 55)) -> R.drawable.drizzle
        (temp.current_weather.weathercode in listOf(61, 63, 65)) -> R.drawable.drizzle
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
        val windSpeed = index.let { temp.hourly.windspeed_10m[it].toInt() }
        LocationView(temp, searchedLocations)
        SunsetSunriseCard(temp)
        HourlyForecastView(temp)
        WeeklyForeCastView(temp)
        Spacer(modifier = Modifier.padding(6.dp))

        val pagerState = rememberPagerState()
        HorizontalPager(state = pagerState, count = 6, modifier = Modifier)
        { page ->
            when (page) {
                0 -> ExtraCards(
                    Text = "Feels Like",
                    Numbers = feelsLike.let {
                        temp.hourly.apparent_temperature[it].toInt().toString()
                    } + temp.hourly_units.apparent_temperature,
                    Icon = painterResource(id = R.drawable.temperaturea))

                1 -> ExtraCards(
                    Text = "Rainfall",
                    Numbers = rainFall.toString() + temp.daily_units.rain_sum,
                    Icon = painterResource(id = R.drawable.drop)
                )
                2 -> ExtraCards(
                    Text = "Wind Speed",
                    Numbers = windSpeed.toString() + temp.hourly_units.windspeed_10m,
                    Icon = painterResource(id = R.drawable.windicon)
                )

                3 -> ExtraCards(
                    Text = "Visibility",
                    Numbers = visibilityInMeters.toString() + temp.hourly_units.visibility,
                    Icon = painterResource(id = R.drawable.eye)
                )

                4 -> ExtraCards(
                    Text = "Humidity",
                    Numbers = temp.hourly_units.relativehumidity_2m + humidity.toString(),
                    Icon = painterResource(id = R.drawable.humidity)
                )

                5 -> ExtraCards(
                    Text = "Dew Point",
                    Numbers = dewPoint.toString() + temp.hourly_units.temperature_2m,
                    Icon = painterResource(id = R.drawable.dew)
                )
            }
        }
        HorizontalPagerIndicator(step = pagerState.currentPage, totalSteps = pagerState.pageCount)
    }
}

@Composable
private fun HorizontalPagerIndicator(step: Int, totalSteps: Int) {

    @Composable
    fun Dot(isSelected: Boolean) {
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 16.dp)
                .clip(CircleShape)
                .background(PagerColor)
                .width(if (isSelected) 14.dp else 8.dp)
                .height(8.dp)
        )
    }

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        repeat(totalSteps) {
            if (it == step) {
                Dot(true)
            } else {
                Dot(false)
            }
        }
    }
}

private fun getProperDisplayName(displayName: String?) = displayName?.split(",")?.firstOrNull()

@Composable
fun LocationView(
    temp: TemperatureResponse,
    searchedLocations: SearchedLocations
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 16.dp, vertical = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = getProperDisplayName(searchedLocations.display_name) ?: "N/D",
            fontSize = 50.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "${temp.current_weather.temperature.toInt()}°",
            color = Color.Black,
            modifier = Modifier.padding(start = 4.dp),
            fontWeight = FontWeight.Normal,
            fontSize = 64.sp,
            textAlign = TextAlign.Center
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
        Spacer(modifier = Modifier.padding(8.dp))
        val weatherCode = temp.current_weather.weathercode
        val weatherDescription = weatherDescriptions[weatherCode] ?: "Unknown"
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val day = 0
            val forecastMini = temp.daily.temperature_2m_min[day].toInt().toString()
            val maximumDegree = temp.daily.temperature_2m_max[day].toInt().toString()
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.arrowdropdown),
                contentDescription = null
            )
            Text(
                text = forecastMini + temp.hourly_units.temperature_2m.firstOrNull(),
                color = Color.Black,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = weatherDescription,
                color = Color.Black,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = maximumDegree + temp.hourly_units.temperature_2m.firstOrNull(),
                color = Color.Black,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.arrowdropup),
                contentDescription = null
            )
        }
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
                    .align(Alignment.CenterVertically)
                    .padding(start = 10.dp),
                text = "HOURLY FORECAST",
                color = Corn,
                fontWeight = FontWeight.Light
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
                        0, 1 -> WeatherCondition.SUNNY
                        2, 3 -> WeatherCondition.CLOUDY
                        51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> WeatherCondition.RAINY
                        71, 73, 75, 77, 85, 86 -> WeatherCondition.SNOWY
                        else -> WeatherCondition.SUNNY // Sets a default weather condition in case of an unknown code
                    }
                    Hours(time, temperature, hourlyWeatherCondition)
                }
                if (!isDaytime) {
                    val hourlyWeatherConditionNight = when (temp.hourly.weathercode[index]) {
                        0, 1 -> WeatherCondition.CLEARNIGHT
                        2, 3 -> WeatherCondition.CLOUDYNIGHT
                        51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> WeatherCondition.RAINYNIGHT
                        else -> {
                            WeatherCondition.CLEARNIGHT
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
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier,
            text = formattedSunrise,
            fontWeight = FontWeight.Thin,
            fontSize = 30.sp,
            color = Corn,
        )
    }
}

@Composable
fun SunSet(temp: TemperatureResponse) {
    val now = LocalDateTime.now().toLocalDate()
    val todaySunSet = temp.daily.sunset.firstOrNull {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .toLocalDate()
            .isEqual(now)
    }

    val formattedSunset = todaySunSet?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .format(DateTimeFormatter.ofPattern("HH:mm"))
    } ?: ""
    Column(
        modifier = Modifier,
        horizontalAlignment = End,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier,
            text = formattedSunset,
            fontWeight = FontWeight.Thin,
            fontSize = 30.sp,
            color = Corn,
        )
    }
}

@Composable
fun DayLength(temp: TemperatureResponse) {
    val now = LocalDateTime.now().toLocalDate()
    val todaySunrise = temp.daily.sunrise.firstOrNull {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .toLocalDate()
            .isEqual(now)
    }

    val todaySunset = temp.daily.sunset.firstOrNull {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .toLocalDate()
            .isEqual(now)
    }

    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    val formattedSunrise = todaySunrise?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .format(formatter)
    } ?: ""

    val formattedSunset = todaySunset?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .format(formatter)
    } ?: ""

    val lengthOfTheDay = if (formattedSunrise.isNotEmpty() && formattedSunset.isNotEmpty()) {
        val sunriseTime = LocalTime.parse(formattedSunrise, formatter)
        val sunsetTime = LocalTime.parse(formattedSunset, formatter)
        val length = Duration.between(sunriseTime, sunsetTime)
        val hours = length.toHours()
        val minutes = length.minusHours(hours).toMinutes()
        "$hours hours $minutes minutes"
    } else {
        "N/A"
    }

    Column(
        modifier = Modifier,
        horizontalAlignment = End,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier,
            text = "Day Length",
            fontWeight = FontWeight.Thin,
            fontSize = 30.sp,
            color = Color.White,
        )
        Text(
            modifier = Modifier.align(End),
            text = lengthOfTheDay,
            fontSize = 16.sp,
            color = Whitehis,
        )
    }
}

@Composable
private fun SunsetSunriseCard(temp: TemperatureResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Dark20),
            contentAlignment = BottomCenter
        ) {
            HorizontalWave(
                phase = rememberPhaseState(startPosition = 10f),
                alpha = 0.2f,
                amplitude = 50f,
                frequency = 0.5f
            )
            HorizontalWave(
                phase = rememberPhaseState(startPosition = 15f),
                alpha = 0.3f,
                amplitude = 80f,
                frequency = 0.4f
            )
            HorizontalWave(
                phase = rememberPhaseState(20f),
                alpha = 0.2f,
                amplitude = 60f,
                frequency = 0.4f
            )
        }
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                SunRise(temp)
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.arrowdropup),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp),
                        tint = Whitehis.copy(alpha = 0.9f),
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.arrowdropdown),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp),
                        tint = Whitehis.copy(alpha = 0.8f),
                    )
                }
                SunSet(temp)
            }
            Spacer(modifier = Modifier.padding(18.dp))
            Icon(
                painter = painterResource(id = R.drawable.sunforicon), contentDescription = null,
                modifier = Modifier
                    .size(70.dp),
                tint = Yellow20,
            )
            Spacer(modifier = Modifier.padding(18.dp))
            DayLength(temp)
        }
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
    RAINYNIGHT(R.drawable.rainynight),
    CLEARNIGHT(R.drawable.clearnight),
    CLOUDYNIGHT(R.drawable.cloudynight)
    // Adds weather conditions and their associated images
}

@Composable
fun WeeklyForeCastView(temp: TemperatureResponse) {
    Column(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Dark20)
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sevendays), contentDescription = null,
                tint = Whitehis
            )


            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically),
                text = "WEEKLY FORECAST",
                color = Corn,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.arrowdropdown),
                contentDescription = null,
                tint = Spir.copy(alpha = 0.9f),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .size(30.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.arrowdropup),
                contentDescription = null,
                tint = Spir,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .size(30.dp)
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
                in listOf(
                    51,
                    53,
                    55,
                    56,
                    57,
                    61,
                    63,
                    65,
                    66,
                    67,
                    80,
                    81,
                    82
                ) -> WeatherCondition.RAINY

                in listOf(71, 73, 75, 77) -> WeatherCondition.SNOWY
                else -> WeatherCondition.SUNNY // Set a default weather condition in case of an unknown code
            }

            val imageResource = weatherCondition.imageResourceId
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
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
                    modifier = Modifier,
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
                color = Color.Gray.copy(alpha = 0.5f),
                thickness = 1.dp,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            )
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically

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
        HorizontalWave(
            phase = rememberPhaseState(10f),
            alpha = 0.3f,
            amplitude = 80f,
            frequency = 0.6f
        )
        HorizontalWave(
            phase = rememberPhaseState(startPosition = 15f),
            alpha = 0.2f,
            amplitude = 60f,
            frequency = 0.4f
        )

    }
}