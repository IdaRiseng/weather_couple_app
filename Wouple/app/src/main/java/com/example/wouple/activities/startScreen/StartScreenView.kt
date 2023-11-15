package com.example.wouple.activities.startScreen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
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
import com.example.wouple.R
import com.example.wouple.activities.detailActivity.SecondActivity
import com.example.wouple.activities.mainActivity.SimpleSearchBar
import com.example.wouple.activities.splashScreen.Navigation
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Dark10
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.vintage

@Composable
fun StartScreenView(
    locations: List<SearchedLocation>?,
    onSearch: (String) -> Unit,
    onButtonClicked: (SearchedLocation) -> Unit,
    searchedLocation: MutableState<SearchedLocation?>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark10),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Navigation()
        Text(
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            text = "Sun, Rain, or Snow – Know Before You Go!",
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Default,
            fontSize = 16.sp,
            color = vintage,
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
                                onButtonClicked(location)
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
        }
    }
}
