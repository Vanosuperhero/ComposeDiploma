package com.example.composediploma.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    var title: String,
    var icon: ImageVector
    ){
    object Monitoring:Screen(
        "main_screen",
        "Мониторинг",
        Icons.Filled.Bolt
    )
    object Statistics:Screen(
        "statistics",
        "Статистика",
        Icons.Filled.BarChart
    )
    object Weather:Screen(
        "weather",
        "Метеостанция",
        Icons.Outlined.Cloud
    )
}
