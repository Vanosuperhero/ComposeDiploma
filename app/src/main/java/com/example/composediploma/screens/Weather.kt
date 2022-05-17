package com.example.composediploma.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.example.composediploma.MainActivity
import com.example.composediploma.mqtt.connect
import com.example.composediploma.mqtt.disconnect
import com.example.composediploma.mqtt.publish
import com.example.composediploma.mqtt.subscribe
//import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*


//@Preview(showBackground = true)
@Composable
fun Weather(context: Context){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
        contentAlignment = Alignment.Center)
    {
        Column {
            Text(
                text = "Weather",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp)

            Button(
                onClick = {
                    connect(context = context)
//                    subscribe("a/b")
                             },
                modifier = Modifier
                    .padding(top = 50.dp)
                    .align(Alignment.CenterHorizontally)) {
                Text(text = "Connect")
            }
            Button(
                onClick = {
                    disconnect()
//                    subscribe("a/b")
                },
                modifier = Modifier
                    .padding(top = 50.dp)
                    .align(Alignment.CenterHorizontally)) {
                Text(text = "DisConnect")
            }
            Button(
                onClick = {
                    subscribe("a/b")
                },
                modifier = Modifier
                    .padding(top = 50.dp)
                    .align(Alignment.CenterHorizontally)) {
                Text(text = "Subscribe")
            }
            Button(
                onClick = {
                    publish("a/b","{\n" +
                            "  \"msg\": \"zoom in\"\n" +
                            "}")
//                    subscribe("a/b")
                },
                modifier = Modifier
                    .padding(top = 50.dp)
                    .align(Alignment.CenterHorizontally)) {
                Text(text = "publish")
            }
        }

    }
}





