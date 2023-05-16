package com.example.wouple


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.model.api.ApiRequest
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Bubbles
import com.example.wouple.ui.theme.Corn
import com.example.wouple.ui.theme.Dark10
import com.example.wouple.ui.theme.Dark20
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.Tangerine
import com.example.wouple.ui.theme.WoupleTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

const val BASE_URL = "https://api.open-meteo.com"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val firstWeatherImage = R.drawable.rainyday
            val secondWeatherImage = R.drawable.cloudsnow

            FirstCardView(firstWeatherImage, secondWeatherImage)
            getCurrentData()
        }
    }

    private fun getCurrentData() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        api.getTemperature().enqueue(object : Callback<List<TemperatureResponse>> {
            override fun onFailure(call: Call<List<TemperatureResponse>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<TemperatureResponse>>, response: Response<List<TemperatureResponse>>) {
              
            }
        })
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val firstWeatherImage = R.drawable.rainyday
    val secondWeatherImage = R.drawable.cloudsnow

    WoupleTheme {
        FirstCardView(firstWeatherImage, secondWeatherImage)
    }
}

@Composable
fun FirstCardView(firstWeatherImage: Int, secondWeatherImage: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.backgroundthree),
                contentScale = ContentScale.Crop
            ),
        horizontalAlignment = CenterHorizontally
    ) {
        //Two Blue cards on one page
        Box(Modifier.weight(1f)) {
            BlueCardView(firstWeatherImage)
        }
        Box(Modifier.weight(1f)) {
            BlueCardView(secondWeatherImage)
        }
    }
}

@Composable
fun BlueCardView(weatherImage: Int) {
    //Blue Card
    Column(
        Modifier
            .padding(vertical = 8.dp, horizontal = 40.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Dark20)
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        //This is the top "set location view
        SetLocationTextField()

        //Row goes that way -->>
        //Weight makes the boxes equal sizes no matter what phone they have
        Row(Modifier.weight(1f)) {
            Box(Modifier.weight(1f)) { TimeView() }
            Box(Modifier.weight(1f)) { TemperatureView() }
        }
        Row(Modifier.weight(1f)) {
            Box(Modifier.weight(1f)) { WindView() }
            Box(Modifier.weight(1f)) { ImageWeatherView(weatherImage) }
        }
    }
}

@Composable
fun SetLocationTextField() {
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
            color = Corn,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier,
            fontSize = 45.sp,
            text = "03:00",
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
fun TemperatureView() {
    val activity = LocalContext.current
    Column(modifier = Modifier
        .clickable {
            val intent = Intent(activity, SecondActivity::class.java)
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
            color = Corn,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Row {
            Text(
                fontSize = 50.sp,
                text = "-5°",
                color = Bubbles.copy(alpha = 1f),
            )
            Icon(
                painter = painterResource(id = R.drawable.heat), contentDescription = "",
                modifier = Modifier
                    .alpha(0.9f)
                    .padding(top = 12.dp)
                    .size(50.dp),
                tint = Tangerine
            )
        }
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = "Snowy",
            color = Corn,
            fontSize = 15.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
fun WindView() {
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
            color = Corn,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier,
            fontSize = 30.sp,
            text = "16km/h",
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