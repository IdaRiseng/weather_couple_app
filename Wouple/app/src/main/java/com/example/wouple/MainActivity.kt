package com.example.wouple


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val BASE_URL = "https://api.open-meteo.com"

class MainActivity : ComponentActivity() {


    private val temp1: MutableState<TemperatureResponse?> = mutableStateOf(null)
    private val temp2: MutableState<TemperatureResponse?> = mutableStateOf(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            if (temp1.value == null || temp2.value == null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Center) {
                    CircularProgressIndicator()
                }
            } else {
                FirstCardView(temp1 = temp1.value!!, temp2 = temp2.value!!, onSearch = {getCurrentData { temp1.value = it }})
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getCurrentData {
            temp1.value = it
        }
        getCurrentData {
            temp2.value = it
        }
    }

    private fun getCurrentData(onSuccessCall: (TemperatureResponse) -> Unit) {
        val context = this.applicationContext
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        api.getTemperature().enqueue(object : Callback<TemperatureResponse> {
            override fun onFailure(call: Call<TemperatureResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<TemperatureResponse>, response: Response<TemperatureResponse>) {
                response.body()?.let { onSuccessCall(it) }
            }
        })
    }
}


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
fun FirstCardView(temp1: TemperatureResponse, temp2: TemperatureResponse, onSearch: (String) -> Unit) {
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
            BlueCardView(temp1, onSearch)
        }
        Box(Modifier.weight(1f)) {
            BlueCardView(temp2, onSearch)
        }
    }
}

@Composable
fun BlueCardView(temp: TemperatureResponse, onSearch: (String) -> Unit) {
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
        SetLocationTextField(onSearch)

        //Row goes that way -->>
        //Weight makes the boxes equal sizes no matter what phone they have
        Row(Modifier.weight(1f)) {
            Box(Modifier.weight(1f)) { TimeView(temp) }
            Box(Modifier.weight(1f)) { TemperatureView(temp) }
        }
        Row(Modifier.weight(1f)) {
            Box(Modifier.weight(1f)) { WindView(temp) }
            Box(Modifier.weight(1f)) { ImageWeatherView(R.drawable.rainyday) }
        }
    }
}

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
fun TimeView(temp: TemperatureResponse) {
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
            color = Corn,
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
    val index = temp.hourly?.time?.map { LocalDateTime.parse(it).hour }?.indexOf(LocalDateTime.now().hour)
    val some = index?.let { temp.hourly?.temperature_2m?.get(it)?.toInt() }

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
            color = Corn,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Row {
            Text(
                fontSize = 50.sp,
                text = some.toString(),
                color = Bubbles.copy(alpha = 1f),
                modifier = Modifier.padding(start = 15.dp)
            )
            Text(
                fontSize = 32.sp,
                text = "°",
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
    val index = temp.hourly?.time?.map { LocalDateTime.parse(it).hour }?.indexOf(LocalDateTime.now().hour)
    val windSpeed = index?.let { temp.hourly?.windspeed_10m?.get(it)?.toInt() }

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
            text = windSpeed.toString() + " Km/h",
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