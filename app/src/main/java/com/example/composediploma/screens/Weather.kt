package com.example.composediploma.screens

import androidx.compose.foundation.background
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composediploma.R
import android.content.Context
import android.graphics.drawable.Icon
import android.media.Image
import android.util.Log
import android.widget.GridLayout
import android.widget.GridView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.DeviceThermostat
import androidx.compose.material.icons.outlined.Umbrella
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.example.composediploma.MainActivity
import com.example.composediploma.mqtt.*
//import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

//private val viewModel: MQTTViewModel by viewModels

//@Preview(showBackground = true)
@Composable
fun Weather(context: Context, viewModel: MQTTViewModel) {
    LazyColumn() {
        item {
            Box {
                Image(
                    painter = painterResource(com.example.composediploma.R.drawable.weatherview),
                    contentDescription = "mainView",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
//            .constrainAs(mainImage) {
//                top.linkTo(parent.top)
//                start.linkTo(parent.start)
//            },
                )
                Row(Modifier.padding(start = 22.dp, top = 30.dp)) {

                    TextWithShadow( text = "${viewModel.ta.value.toDouble().toInt()}", modifier = Modifier, fontSize = 80.sp)
                    TextWithShadow( text = "°C", modifier = Modifier.padding(top = 15.dp, start = 5.dp), fontSize = 35.sp)
//                    Text(
//                        text = "${viewModel.ta.value.toDouble().toInt()}",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 80.sp,
//                        color = Color.White,
//                    )
//                    Text(
//                        text = "°C",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 35.sp,
//                        color = Color.White,
//                        modifier = Modifier.padding(top = 15.dp, start = 10.dp)
//                    )
                }
            }
        }
        item {
            Row(Modifier.fillMaxWidth()){
                Column(Modifier.padding(start = 22.dp, end = 11.dp)) {
                    Index(title = "Температура", value = "${viewModel.ta.value}°C", R.drawable.device_thermostat_48px)
                    Index(title = "Скорость ветра", value = "${viewModel.ws1avg.value} м/с", R.drawable.air_48px)
                    Index(title = "Атм. давление", value = "${viewModel.pa.value} мм.рс", R.drawable.icons8_barometer_64)
                }
                Column(Modifier.padding(start = 11.dp, end = 22.dp)) {
                    Index(title = "Влажность", value = "${viewModel.rh.value}%", icon = R.drawable.humidity_low_48px)
                    Index(title = "Кол-во осадков", value = "${viewModel.pr24h.value} мм", icon = R.drawable.umbrella_48px)
                    Index(title = "Напряжение", value = "${viewModel.v.value} В", icon = R.drawable.bolt_48px)
                }
            }
        }
    }
}

@Composable
fun Index(title:String, value:String, icon: Int) {
    Box(Modifier.padding(top = 20.dp)) {
        Row {
            Icon(painterResource(id = icon), title,
                Modifier
                    .padding(end = 14.dp)
                    .align(Alignment.CenterVertically))
            Column() {
                Text(text = title,fontWeight = FontWeight.Bold)
                Text(text = value,fontWeight = FontWeight.Normal)
            }
        }
    }
}

@Composable
fun TextWithShadow(
    text: String,
    modifier: Modifier,
    fontSize: TextUnit
) {
    Box {
        Text(
            text = text,
            color = Color.DarkGray,
            modifier = modifier
            .offset(
                x = 2.dp,
                y = 2.dp
            )
                .alpha(0.35f),
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
        )
        Text(
            text = text,
            color = Color.White,
            modifier = modifier,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
        )
    }
}



