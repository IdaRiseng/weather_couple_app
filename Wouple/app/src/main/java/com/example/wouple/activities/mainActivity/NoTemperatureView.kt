package com.example.wouple.activities.mainActivity

import android.content.Intent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
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
import com.example.wouple.activities.startScreen.StartActivity
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
    val colors = listOf(
        Color(0xFF1D244D),
        Color(0xFF25508C),
        Color(0xFF4180B3),
        Color(0xFF8ABFCC),
        Color(0xFFC0DDE1),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = colors,
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally

    ) {
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
            text = "NorthFlurry",
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.Default,
            fontSize = 32.sp,
            color = vintage,
        )
        Text(
            modifier = Modifier
                .align(CenterHorizontally),
            textAlign = TextAlign.Center,
            text = "Weather Forecast",
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.SansSerif,
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