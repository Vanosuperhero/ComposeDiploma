package com.example.composediploma.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.bytebeats.views.charts.bar.BarChart
import me.bytebeats.views.charts.bar.BarChartData
import me.bytebeats.views.charts.bar.render.bar.SimpleBarDrawer
import me.bytebeats.views.charts.bar.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.bar.render.yaxis.SimpleYAxisDrawer
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import me.bytebeats.views.charts.pie.render.SimpleSliceDrawer
import me.bytebeats.views.charts.simpleChartAnimation


@Preview(showBackground = true)
@Composable
fun Statistics() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 20.dp),
        contentAlignment = Alignment.TopCenter
    )
    {
        BarChartView()
//        Text(
//            text = "Statistics",
//            color = Color.White,
//            fontWeight = FontWeight.Bold,
//            fontSize = 40.sp)
    }
}
        @Composable
        fun BarChartView() {
            BarChart(
                barChartData = BarChartData(
                    bars = listOf(
                        BarChartData.Bar(
                            label = "Bar 1",
                            value = 200F,
                            color = Color.Red
                        ),
                        BarChartData.Bar(
                            label = "Bar 2",
                            value = 300F,
                            color = Color.Blue
                        ),

//                        BarChartData.Bar(
//                            label = "Bar 3",
//                            value = 500F,
//                            color = Color.Green
//                        ),
//                        BarChartData.Bar(
//                            label = "Bar 4",
//                            value = 400F,
//                            color = Color.Yellow
//                        ),
                    )
                ),
                // Optional properties.
                modifier = Modifier.size(250.dp),
                animation = simpleChartAnimation(),
                barDrawer = SimpleBarDrawer(),
                xAxisDrawer = SimpleXAxisDrawer(),
                yAxisDrawer = SimpleYAxisDrawer(),
//                labelDrawer = SimpleValueDrawer()
            )
        }
