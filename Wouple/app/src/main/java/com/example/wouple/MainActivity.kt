package com.example.wouple

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateSizeAsState

import androidx.compose.foundation.Image

import androidx.compose.foundation.background

import androidx.compose.foundation.clickable


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction


import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition


import com.example.wouple.ui.theme.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
      FirstCardView()
}



            }
    }


 @Composable
 fun FirstCardView(){
     val activity = LocalContext.current
    
     Column(
         modifier = Modifier
             .fillMaxSize(1f)
             .paint(
                 painter = painterResource(id = R.drawable.rainybackground),
                 contentScale = ContentScale.Crop
             )
             .padding(10.dp, 20.dp, 10.dp, 20.dp),
         horizontalAlignment = CenterHorizontally
     ){

         Box(modifier = Modifier
             .size(300.dp, 360.dp)
             .weight(1f, true)
             .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
             .background(Dark20)
         ) {
             var text by remember { mutableStateOf(("")) }
             val focusManager = LocalFocusManager.current

             Row(modifier = Modifier
                 .padding(start = 10.dp)
                 .fillMaxWidth(1f)
                 .height(50.dp)){

                 TextField(
                     value = text.uppercase(),
                     shape = RoundedCornerShape(20.dp),
                     colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Spiro, unfocusedBorderColor = Bubbles.copy(alpha = 0.6f), textColor = Corn),
                     singleLine = true,
                     keyboardOptions = KeyboardOptions.Default.copy(
                         imeAction = ImeAction.Search
                     ),
                     keyboardActions = KeyboardActions(
                         onSearch = { focusManager.clearFocus()}
                     ),
                     onValueChange = {
                         newText -> text = newText
                     },
                     placeholder = { Text(modifier = Modifier.padding(start = 65.dp),
                         color = Bubbles,
                         text = "Set Location")
                     }
                 )
             }

             Box(modifier = Modifier
                 .padding(bottom = 90.dp, start = 5.dp)
                 .align(CenterStart)
                 .size(140.dp)
                 .background(Dark10, shape = RoundedCornerShape(20.dp))
             ){
                 Icon(painter = painterResource(id = R.drawable.ic_time_foreground), contentDescription = "",
                     modifier = Modifier
                         .size(40.dp)
                         .alpha(0.9f)
                         .align(BottomCenter),
                     tint = Tangerine)
                 Text(modifier = Modifier
                     .padding(top = 10.dp)
                     .align(TopCenter),
                     text = "TIME",
                     color = Corn,
                     fontSize = 14.sp,
                     fontWeight = FontWeight.Bold )

                 Text(modifier = Modifier
                     .align(Center),
                     fontSize = 45.sp,
                     text = "03:00",
                     color = Bubbles.copy(alpha = 1f),
                 )

             }

             Box(modifier = Modifier
                 .clickable {
                     val intent = Intent(activity, SecondActivity::class.java)
                     activity.startActivity(intent)
                 }
                 .align(CenterEnd)
                 .padding(bottom = 90.dp, end = 5.dp)
                 .size(140.dp)
                 .background(Dark10, RoundedCornerShape(20.dp))
             ){
                 Icon(painter = painterResource(id = R.drawable.heat), contentDescription = "",
                     modifier = Modifier
                         .alpha(0.9f)
                         .size(70.dp)
                         .padding(top = 10.dp, end = 25.dp)
                         .align(CenterEnd),
                     tint = Tangerine)

                 Text(modifier = Modifier
                     .padding(top = 10.dp)
                     .align(TopCenter),
                     text = "TEMPERATURE",
                     color = Corn,
                     fontSize = 14.sp,
                     fontWeight = FontWeight.Bold )

                 Text(modifier = Modifier
                     .align(Center)
                     .padding(end = 25.dp, top = 10.dp),
                     fontSize = 50.sp,
                     text = "-5°",
                     color = Bubbles.copy(alpha = 1f),
                 )

                 Text(modifier = Modifier
                     .padding(bottom = 10.dp)
                     .align(BottomCenter),
                     text = "Snowy",
                     color = Corn,
                     fontSize = 15.sp,
                     fontWeight = FontWeight.Light )

             }

             Box(modifier = Modifier
                 .padding(bottom = 5.dp, end = 5.dp)
                 .align(BottomEnd)
                 .size(140.dp)
                 .background(Dark10, RoundedCornerShape(20.dp))
             ){


                 Image(painter = painterResource(id = R.drawable.cloudsnow), contentDescription ="", contentScale = ContentScale.Crop,
                     modifier = Modifier
                         .clickable {
                             val intent = Intent(activity, SecondActivity::class.java)
                             activity.startActivity(intent)
                         }
                         .align(BottomCenter)
                         .size(130.dp)
                         .padding(bottom = 2.dp, end = 2.dp))
             }

             Box(modifier = Modifier
                 .padding(bottom = 5.dp, start = 5.dp)
                 .align(BottomStart)
                 .size(140.dp)
                 .background(Dark10, RoundedCornerShape(20.dp))
             ){
                 Icon(painter = painterResource(id = R.drawable.wind), contentDescription = "",
                     modifier = Modifier
                         .alpha(0.9f)
                         .size(38.dp)
                         .padding(top = 6.dp, bottom = 10.dp)
                         .align(BottomCenter),
                     tint = Tangerine)
                 Text(modifier = Modifier
                     .align(Center),
                     fontSize = 30.sp,
                     text = "16km/h",
                     color = Bubbles.copy(alpha = 1f) )

                 Text(modifier = Modifier
                     .padding(top = 10.dp)
                     .align(TopCenter),
                     text = "WIND",
                     color = Corn,
                     fontSize = 14.sp,
                     fontWeight = FontWeight.Bold )


             }

         }

         Spacer(modifier = Modifier.padding(10.dp))

         Box(modifier = Modifier
             .size(300.dp, 360.dp)
             .weight(1f, true)
             .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
             .background(Dark20)
         ) {
             var text by remember { mutableStateOf(("")) }
             val focusManager = LocalFocusManager.current
             Row(modifier = Modifier
                 .padding(start = 10.dp)
                 .fillMaxWidth(1f)
                 .height(60.dp)){

                 TextField(
                     value = text.uppercase(),
                     shape = CircleShape,
                     colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Tangerine, unfocusedBorderColor = Bubbles.copy(alpha = 0.6f), textColor = Corn),
                     placeholder = { Text(modifier = Modifier.padding(start = 65.dp),
                         color = Bubbles,
                         text = "Set Location")},
                     singleLine = true,
                     keyboardOptions = KeyboardOptions.Default.copy(
                         imeAction = ImeAction.Search
                     ),
                     keyboardActions = KeyboardActions(
                         onSearch = { focusManager.clearFocus()}
                     ),
                     onValueChange = {
                         text = it
                     }
                 )
             }

             Box(modifier = Modifier
                 .padding(bottom = 90.dp, start = 5.dp)
                 .align(CenterStart)
                 .size(140.dp)
                 .background(Dark10, shape = RoundedCornerShape(20.dp))
             ){
                 Icon(painter = painterResource(id = R.drawable.ic_time_foreground), contentDescription = "",
                     modifier = Modifier
                         .size(40.dp)
                         .alpha(0.9f)
                         .align(BottomCenter),
                     tint = Tangerine)
                 Text(modifier = Modifier
                     .padding(top = 10.dp)
                     .align(TopCenter),
                     text = "TIME",
                     color = Corn,
                     fontSize = 14.sp,
                     fontWeight = FontWeight.Bold )

                 Text(modifier = Modifier
                     .align(Center),
                     fontSize = 45.sp,
                     text = "03:00",
                     color = Bubbles.copy(alpha = 1f),
                 )

             }

             Box(modifier = Modifier
                 .padding(bottom = 90.dp, end = 5.dp)
                 .align(CenterEnd)
                 .size(140.dp)
                 .background(Dark10, RoundedCornerShape(20.dp))
             ){
                 Icon(painter = painterResource(id = R.drawable.heat), contentDescription = "",
                     modifier = Modifier
                         .alpha(0.9f)
                         .size(70.dp)
                         .padding(top = 10.dp, end = 25.dp)
                         .align(CenterEnd),
                     tint = Tangerine)

                 Text(modifier = Modifier
                     .padding(top = 10.dp)
                     .align(TopCenter),
                     text = "TEMPERATURE",
                     color = Corn,
                     fontSize = 14.sp,
                     fontWeight = FontWeight.Bold )

                 Text(modifier = Modifier
                     .align(Center)
                     .padding(end = 25.dp, top = 10.dp),
                     fontSize = 50.sp,
                     text = "7°",
                     color = Bubbles.copy(alpha = 1f),
                 )
                 Text(modifier = Modifier
                     .padding(bottom = 10.dp)
                     .align(BottomCenter),
                     text = "Rainy",
                     color = Corn,
                     fontSize = 15.sp,
                     fontWeight = FontWeight.Light )

             }

             Box(modifier = Modifier
                 .padding(bottom = 5.dp, end = 5.dp)
                 .align(BottomEnd)
                 .size(140.dp)
                 .background(Dark10, RoundedCornerShape(20.dp))
             ){

                 Image(painter = painterResource(id = R.drawable.rainyday), contentDescription ="", contentScale = ContentScale.Crop,
                     modifier = Modifier
                         .align(BottomCenter)
                         .size(130.dp)
                         .padding(bottom = 2.dp, end = 2.dp))
             }

             Box(modifier = Modifier
                 .padding(bottom = 5.dp, start = 5.dp)
                 .align(BottomStart)
                 .size(140.dp)
                 .background(Dark10, RoundedCornerShape(20.dp))
             ){
                 Icon(painter = painterResource(id = R.drawable.wind), contentDescription = "",
                     modifier = Modifier
                         .alpha(0.9f)
                         .size(38.dp)
                         .padding(top = 6.dp, bottom = 10.dp)
                         .align(BottomCenter),
                     tint = Tangerine)
                 Text(modifier = Modifier
                     .align(Center),
                     fontSize = 30.sp,
                     text = "16km/h",
                     color = Bubbles.copy(alpha = 1f) )

                 Text(modifier = Modifier
                     .padding(top = 10.dp)
                     .align(TopCenter),
                     text = "WIND",
                     color = Corn,
                     fontSize = 14.sp,
                     fontWeight = FontWeight.Bold )


             }

         }
         }
 }

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WoupleTheme {
    FirstCardView()
    }
}
