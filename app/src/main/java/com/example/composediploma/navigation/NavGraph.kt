package com.example.composediploma.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composediploma.mqtt.MQTTViewModel
import com.example.composediploma.screens.Monitoring
import com.example.composediploma.screens.Statistics
import com.example.composediploma.screens.Weather

@Composable
fun BottomNavGraph(
    context: Context,
    navController:NavHostController,
    viewModel: MQTTViewModel
){
    NavHost(
        navController = navController,
        startDestination = Screen.Monitoring.route
    ){
        composable(
            route = Screen.Monitoring.route
        ){
            Monitoring(viewModel)
        }
        composable(
            route = Screen.Weather.route
        ){
            Weather(context, viewModel)
        }
        composable(
            route = Screen.Statistics.route
        ){
            Statistics()
        }
    }
}