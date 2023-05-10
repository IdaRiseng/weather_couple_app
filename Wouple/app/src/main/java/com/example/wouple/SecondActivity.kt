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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.ui.theme.*

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecondCardView()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    WoupleTheme {
        SecondCardView()
    }
}

@Composable
fun SecondCardView() {
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
        HourlyForecastView()
        TenDayForeCastView()
        RainFallView()
    }

}

@Composable
fun HourlyForecastView() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 12.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Dark20)
    ) {
        Row(modifier = Modifier.padding(start = 16.dp)) {


            Icon(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "",
                tint = Bubbles.copy(alpha = 0.8f),
                modifier = Modifier
                    .size(20.dp)
            )
            Text(
                modifier = Modifier
                    .align(CenterVertically),
                text = "HOURLY FORECAST",
                color = Color.LightGray.copy(alpha = 0.8f)
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
            for (hours in 1..24) {
                Hours()
            }
        }
    }
}

@Composable
fun Hours() {
    Column(
        modifier = Modifier
            .padding(4.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            text = "Now", color = Whitehis
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
fun TenDayForeCastView() {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Dark20)
    ) {
        var text by remember { mutableStateOf(("")) }
        val focusManager = LocalFocusManager.current
        val activity = LocalContext.current
        val intent = Intent(activity, MainActivity::class.java)
        val data = intent.getStringExtra("location")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 8.dp, start = 18.dp)
        ) {


            Icon(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "",
                tint = Bubbles.copy(alpha = 0.8f),
                modifier = Modifier
                    .size(20.dp)
            )

            Text(
                modifier = Modifier
                    .align(CenterVertically),
                text = "10 - DAY FORECAST",
                color = Color.LightGray.copy(alpha = 0.8f)
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
fun RainFallView() {
    Row(modifier = Modifier.padding(top = 8.dp)) {
        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                    .background(Dark20)
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "0mm expected")
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                    .background(Dark20)
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.padding(top = 8.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.drop), contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .padding(top = 2.dp),
                    Corn,
                )
                    Text(modifier = Modifier,
                        text = "RAINFALL",
                        color = Corn)
                }
                Text(modifier = Modifier,
                    text = "O mm",
                color = Corn,
               fontSize = 32.sp)

                Text(modifier = Modifier,
                    text = "Expected Today",
                color = Corn)
            }
        }
    }
}