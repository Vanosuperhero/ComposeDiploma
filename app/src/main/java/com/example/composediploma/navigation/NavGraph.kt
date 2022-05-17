package com.example.composediploma.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composediploma.screens.Monitoring
import com.example.composediploma.screens.Statistics
import com.example.composediploma.screens.Weather

@Composable
fun BottomNavGraph(
    context: Context,
    navController:NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.Monitoring.route
    ){
        composable(
            route = Screen.Monitoring.route
        ){
            Monitoring()
        }
        composable(
            route = Screen.Weather.route
        ){
            Weather(context)
        }
        composable(
            route = Screen.Statistics.route
        ){
            Statistics()
        }
    }
}