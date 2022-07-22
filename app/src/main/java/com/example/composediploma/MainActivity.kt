package com.example.composediploma

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.composediploma.mqtt.MQTTViewModel
import com.example.composediploma.mqtt.MySqlViewModel
import com.example.composediploma.screens.MainScreen
import com.example.composediploma.ui.theme.ComposeDiplomaTheme


class MainActivity : ComponentActivity() {

    private val mqttViewModel: MQTTViewModel by viewModels()
    private val mySqlViewModel: MySqlViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mqttViewModel.connect(this)
        mySqlViewModel.mysqlConnection
        setContent {
            ComposeDiplomaTheme {
                MainScreen(this,mqttViewModel,mySqlViewModel)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!mqttViewModel.connected.value) {
            mqttViewModel.connect(this)
        }
        if (!mySqlViewModel.connected.value) {
            mySqlViewModel.mysqlConnection
        }
    }

}

