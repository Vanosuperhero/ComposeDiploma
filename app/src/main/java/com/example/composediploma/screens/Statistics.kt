package com.example.composediploma.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import me.bytebeats.views.charts.pie.render.SimpleSliceDrawer
import me.bytebeats.views.charts.simpleChartAnimation


@Preview(showBackground = true)
@Composable
fun Statistics(){
    Box(
        modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        contentAlignment = Alignment.Center)
    {
//        Text(
//            text = "Statistics",
//            color = Color.White,
//            fontWeight = FontWeight.Bold,
//            fontSize = 40.sp)
//    }

    PieChart(
        pieChartData = PieChartData(
            slices = listOf(
                PieChartData.Slice(10F, Color.Cyan),
                PieChartData.Slice(4F, Color.Red),
                PieChartData.Slice(6F, Color.Yellow)
            )
        ),
        // Optional properties.
        modifier = Modifier.size(150.dp),
        animation = simpleChartAnimation(),
        sliceDrawer = SimpleSliceDrawer()
    )
}}