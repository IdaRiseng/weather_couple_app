package com.example.wouple


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.model.api.ApiRequest
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Blue10
import com.example.wouple.ui.theme.Blueish
import com.example.wouple.ui.theme.Bubbles
import com.example.wouple.ui.theme.Corn
import com.example.wouple.ui.theme.Dark10
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.Tangerine
import com.example.wouple.ui.theme.Whitehis
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.open-meteo.com"

class MainActivity : ComponentActivity() {
    private val temp: MutableState<TemperatureResponse?> = mutableStateOf(null)
    private val temp1: MutableState<TemperatureResponse?> = mutableStateOf(null)
    private val temp2: MutableState<TemperatureResponse?> = mutableStateOf(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            if (temp1.value == null || temp2.value == null || temp.value == null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Center) {
                    CircularProgressIndicator()
                }
            } else {
                FirstCardView(
                    temp1 = temp1.value!!,
                    temp2 = temp2.value!!,
                    temp = temp.value!!,
                    onSearch = { getCurrentData { temp1.value = it } })
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
        getCurrentData {
            temp.value = it
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

            override fun onResponse(
                call: Call<TemperatureResponse>,
                response: Response<TemperatureResponse>
            ) {
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
fun FirstCardView(
    temp: TemperatureResponse,
    temp1: TemperatureResponse,
    temp2: TemperatureResponse,
    onSearch: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                when (temp1.current_weather.is_day) {
                    1 -> painterResource(id = R.drawable.nightone)
                    0 -> painterResource(id = R.drawable.nightone)
                    else -> return
                },
                contentScale = ContentScale.Crop
            ),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SearchBar()
        //Two Blue cards on one page
        /* Box(Modifier.weight(1f)) {
             BlueCardView(temp1, onSearch)
         }
         Box(Modifier.weight(1f)) {
             BlueCardView(temp2, onSearch)
         }*/
        TildeScreen(temp)


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
            .height(56.dp)
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
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp, // Adjusts the font size as desired
                ),
                placeholder = {
                    Text("Search", color = Color.Gray)
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = androidx.compose.ui.text.input.ImeAction.Search,
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
                tint = if (isSearchExpanded) Color.Black else Color.Black,
                modifier = Modifier.size(42.dp)
            )
        }
    }
}
/*@Composable
fun SingleLineWave() {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val waveOffset = remember { androidx.compose.animation.core.Animatable(0f) }

    LaunchedEffect(Unit) {
        waveOffset.animateTo(
            targetValue = (-(screenWidth / 2)).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val wavePath = Path()
        wavePath.moveTo(0f, size.height / 2)

        val waveAmplitude = 20f

        wavePath.quadraticBezierTo(
            size.width / 4, size.height / 2 + waveAmplitude + waveOffset.value,
            size.width / 2, size.height / 2
        )

        wavePath.quadraticBezierTo(
            size.width * 3 / 4, size.height / 2 - waveAmplitude + waveOffset.value,
            size.width, size.height / 2
        )

        drawPath(
            path = wavePath,
            brush = SolidColor(Whitehis)
        )
    }
}*/

@Composable
fun TildeScreen(temp: TemperatureResponse) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 450.dp)
    ) {
        // Draw the bottom half in white color
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        )
        {

            Text(
                text = "Oslo, Norway",
                modifier = Modifier.align(TopCenter),
                fontSize = 40.sp,
                color = Color.Black,
            )
            val activity = LocalContext.current
            Image(
                painter =  painterResource(id = R.drawable.next), contentDescription = null,
                modifier = Modifier.clickable {
                    val intent = Intent(activity, SecondActivity::class.java)
                    intent.putExtra("temp", temp)
                    activity.startActivity(intent)
                }.size(50.dp).align(TopEnd).padding(top = 8.dp),
            )
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

        val activity = LocalContext.current
        /*Button(
            onClick = { /* Handle button click here */ val intent = Intent(activity, SecondActivity::class.java)
                intent.putExtra("temp", temp)
                activity.startActivity(intent) },
            modifier = Modifier
                .align(Alignment.Center),
            colors = ButtonDefaults.buttonColors(Color.Gray)
        ) {
            Text("Click Me")
        }*/
        Divider(
            color = Color.Gray.copy(alpha = 0.8f),
            thickness = 0.5.dp,
            modifier = Modifier
                .padding(bottom = 28.dp)
                .align(BottomCenter)
                .padding(horizontal = 20.dp)
        )
    }

}


/*@Composable
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
            Box(Modifier.weight(1f)) { TimeView() }
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