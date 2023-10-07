package com.example.wouple.activities.mainActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.SearchedLocations
import com.example.wouple.ui.theme.Spiro

@Composable
fun NoTemperatureView(
    locations: List<SearchedLocations>?,
    onSearch: (String) -> Unit,
    onLocationButtonClicked: (SearchedLocations) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
                .padding(bottom = 18.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            HorizontalWave(
                phase = rememberPhaseState(0f),
                alpha = 1f,
                amplitude = 50f,
                frequency = 0.5f
            )
            HorizontalWave(
                phase = rememberPhaseState(15f),
                alpha = 0.5f,
                amplitude = 80f,
                frequency = 0.3f
            )
            HorizontalWave(
                phase = rememberPhaseState(10f),
                alpha = 0.2f,
                amplitude = 40f,
                frequency = 0.6f
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                modifier = Modifier.padding(vertical =8.dp, horizontal = 16.dp).padding(top = 8.dp),
                text = "Wouple Forecast",
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Cursive,
                fontSize = 32.sp,
                color = Color.White,
            )

            SearchBar(onSearch)
            locations?.forEach { location ->
                Button(
                    onClick = {
                    onLocationButtonClicked(location)
                }) {
                    Text(text = location.display_name)
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize(0.9f)
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sailingboat), contentDescription = null,
                tint = Spiro, modifier = Modifier
                    .align(CenterHorizontally)
                    .size(70.dp)
                    .padding(start = 16.dp, bottom = 8.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            DropDownMenu()
        }
    }
}

@Composable
fun DropDownMenu() {
    var isExpanded by remember { mutableStateOf(false) }
    Box {
        IconButton(
            onClick = { isExpanded = !isExpanded }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menuicon), contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
        DropdownMenu(expanded = isExpanded,
            onDismissRequest = { isExpanded = false })
        {
            DropdownMenuItem(onClick = { /*TODO*/ })
            {
                Column {
                    Text(text = "Celcius C")
                    Text(text = "Fahreneight F")
                }
            }
        }
    }
}