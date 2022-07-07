package com.example.composediploma

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.composediploma.mqtt.MQTTViewModel
import com.example.composediploma.mqtt.MySqlViewModel
import com.example.composediploma.screens.MainScreen
import com.example.composediploma.ui.theme.ComposeDiplomaTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mqttViewModel: MQTTViewModel by viewModels()
        val mySqlViewModel: MySqlViewModel by viewModels()
        mqttViewModel.connect(this)
        mySqlViewModel.mysqlConnection
//        mySqlViewModel.state()
        setContent {
            ComposeDiplomaTheme {
                MainScreen(this,mqttViewModel,mySqlViewModel)
            }
        }
    }
}

