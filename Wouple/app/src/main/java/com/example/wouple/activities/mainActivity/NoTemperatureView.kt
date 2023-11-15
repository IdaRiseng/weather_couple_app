package com.example.wouple.activities.mainActivity

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
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
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wouple.R
import com.example.wouple.activities.detailActivity.SecondActivity
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.ui.theme.Corn
import com.example.wouple.ui.theme.Dark10
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.Tangerine
import com.example.wouple.ui.theme.Whitehis
import com.example.wouple.ui.theme.vintage

@Composable
fun NoTemperatureView(
    onStartButtonClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark10),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally

    ) {
        /*  Spacer(modifier = Modifier.padding(16.dp))
          Text(
              modifier = Modifier.padding(12.dp),
              textAlign = TextAlign.Center,
              text = "Sun, Rain, or Snow – Know Before You Go!",
              fontWeight = FontWeight.Normal,
              fontFamily = FontFamily.Default,
              fontSize = 16.sp,
              color = Spiro,
          )
          SimpleSearchBar(onSearch)
          if (locations != null) {
              LazyColumn(
                  modifier = Modifier.fillMaxSize(),
                  contentPadding = PaddingValues(vertical = 4.dp)
              ) {
                  items(locations) { location ->
                      Card(
                          modifier = Modifier
                              .fillMaxWidth()
                              .padding(vertical = 4.dp, horizontal = 16.dp)
                              .clip(RoundedCornerShape(30.dp))
                              .clickable {
                                  searchedLocation.value = location
                                  onLocationButtonClicked(location)
                              },
                          elevation = 4.dp,
                          backgroundColor = White
                      ) {
                          Row(
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .padding(12.dp),
                              verticalAlignment = CenterVertically
                          ) {
                              Icon(
                                  painter = painterResource(id = R.drawable.pin),
                                  contentDescription = null,
                                  tint = Color.Unspecified
                              )
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
          }*/
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.bluecloud),
            contentDescription = "cloud",
            alignment = Center,
            modifier = Modifier.size(130.dp)
        )
        Text(
            modifier = Modifier
                .align(CenterHorizontally),
            textAlign = TextAlign.Center,
            text = "Weather",
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.Default,
            fontSize = 32.sp,
            color = vintage,
        )
        Text(
            modifier = Modifier
                .align(CenterHorizontally),
            textAlign = TextAlign.Center,
            text = "WOUPLE FORECAST",
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.Default,
            fontSize = 32.sp,
            color = Corn,
        )
        Text(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
            text = "the ultimate weather companion designed to make your daily life easier. Our app delivers accurate, real-time weather forecasts, ensuring you're prepared for whatever Mother Nature has in store.",
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.Default,
            fontSize = 16.sp,
            color = Whitehis.copy(alpha = 0.8f),
        )
        Button(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(vintage),
            onClick = {
              onStartButtonClicked()
            }
        ) {
            Text(
                text = "Get Started"
            )
        }
        Spacer(modifier = Modifier.weight(2f))
    }
}
@Composable
fun SimpleSearchBar(
    onSearch: (String) -> Unit,
) {
    var query by remember { mutableStateOf("") }
    val gradient = Brush.horizontalGradient(
        colors = listOf(White, Color(0xFF56CCF2))
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp)
            .padding(horizontal = 24.dp)
            .height(55.dp)
            .background(
                brush = gradient,
                shape = RoundedCornerShape(28.dp)
            ),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextField(
            value = query,
            maxLines = 1,
            onValueChange = {
                query = it
                onSearch(it)
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
                    modifier = Modifier.padding(start = 24.dp),
                    text = "Search a city or airport",
                    color = Color.Black.copy(alpha = 0.7f)
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                capitalization = KeyboardCapitalization.Characters,
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(query)
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
}