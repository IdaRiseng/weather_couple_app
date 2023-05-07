package com.example.wouple

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.wouple.ui.theme.*

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           SecondCardView()
        }
    }
}
@Composable
fun SecondCardView(){
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .paint(
                painter = painterResource(id = R.drawable.rainybackground),
                contentScale = ContentScale.Crop
            )
            .padding(30.dp, 30.dp, 30.dp, 30.dp),
    ){
        Box(modifier = Modifier
            .size(450.dp, 260.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Dark20)
        ) {
            var text by remember { mutableStateOf(("")) }
            val focusManager = LocalFocusManager.current
            val activity = LocalContext.current
            val intent  = Intent(activity, MainActivity::class.java)
            val data = intent.getStringExtra("location")
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Icon(painter = painterResource(id = R.drawable.calendar), contentDescription ="", tint = Corn, modifier = Modifier
                    .size(30.dp)
                    .padding(start = 10.dp, top = 10.dp))

                Divider(
                    color = Corn,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(top = 30.dp)
                )
            }


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