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
                painter = painterResource(id = R.drawable.rainybackground),
                contentScale = ContentScale.Crop
            )
            .verticalScroll(scrollStateOne),
        horizontalAlignment = CenterHorizontally
    ) {
        Locationview()
        HourlyForecastView(temp)
        WeeklyForeCastView(temp)
        RainFallView(temp)
        HumidityView(temp)
    }

}

@Composable
fun Locationview() {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "OSLO",
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "-5°",
            color = Color.Black,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Snowy",
            color = Color.Black,
            fontSize = 24.sp,
        )
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
                tint = Corn.copy(alpha = 0.6f),
                modifier = Modifier
                    .size(20.dp)
            )
            Text(
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(start = 4.dp),
                text = "HOURLY FORECAST",
                color = Bubbles.copy(alpha = 0.8f)
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
                tint = Corn.copy(alpha = 0.8f),
                modifier = Modifier
                    .size(20.dp)
            )

            Text(
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(start = 4.dp),
                text = "WEEKLY FORECAST",
                color = Bubbles.copy(alpha = 0.8f)
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
                    color = Tangerine.copy(alpha = 0.8f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                )
            }

        }
    }
}

@Composable
fun RainFallView(temp: TemperatureResponse) {

    val index = temp.hourly?.time?.map { LocalDateTime.parse(it).hour }?.indexOf(LocalDateTime.now().hour)
    val feelsLike = index?.let { temp.hourly?.temperature_2m?.get(it)?.toInt() }

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
            val index = temp.hourly?.time?.map { LocalDateTime.parse(it).hour }?.indexOf(LocalDateTime.now().hour)
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

    val index = temp.hourly?.time?.map { LocalDateTime.parse(it).hour }?.indexOf(LocalDateTime.now().hour)
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
                val index = temp.hourly?.time?.map { LocalDateTime.parse(it).hour }?.indexOf(LocalDateTime.now().hour)
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
            val index = temp.hourly?.time?.map { LocalDateTime.parse(it).hour }?.indexOf(LocalDateTime.now().hour)
            val visibilityInMeters = index?.let { temp.hourly?.visibility?.get(it)?.toInt()}
            val visibilityInKilometers = visibilityInMeters?.times(0.001)?.toString()

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
                        text = it + " km",
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