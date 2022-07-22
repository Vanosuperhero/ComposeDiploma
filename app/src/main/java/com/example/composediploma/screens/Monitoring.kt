package com.example.composediploma.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
//import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.composediploma.R
import com.example.composediploma.mqtt.MQTTViewModel
import com.example.composediploma.mqtt.MySqlViewModel


@Composable
fun Monitoring(mqttViewModel: MQTTViewModel) {
    Column() {
        ConstraintLayout(
        ) {
            val mainImage = createRef()
            val toPanelUp = createRef()
            val toPanelDown = createRef()
            val toConsumerUp = createRef()
            val toConsumerDown = createRef()
            val fromGridUp = createRef()
            val fromGridDown = createRef()
            val toGridUp = createRef()
            val toGridDown = createRef()

            val startToPanel = createGuidelineFromStart(0.35f)
            val endToPanel = createGuidelineFromEnd(0.352f)
            val topToPanel = createGuidelineFromTop(0.26f)
            val bottomToPanel = createGuidelineFromBottom(0.405f)
            val middleToPanelUp = createGuidelineFromBottom(0.55f)
            val middleToPanelDown = createGuidelineFromBottom(0.525f)

            val startToConsumer = createGuidelineFromStart(0.39f)
            val endToConsumer = createGuidelineFromEnd(0.5f)
            val topToConsumer = createGuidelineFromTop(0.818f)
            val bottomToConsumer = createGuidelineFromBottom(0.057f)
            val middleToConsumerUp = createGuidelineFromBottom(0.122f)
            val middleToConsumerDown = createGuidelineFromBottom(0.108f)
            val startToGrid = createGuidelineFromStart(0.59f)
            val endToGrid = createGuidelineFromEnd(0.3f)

            val startFromGrid = createGuidelineFromStart(0.717f)
            val endFromGrid = createGuidelineFromEnd(0.108f)
            val topFromGrid = createGuidelineFromTop(0.513f)
            val bottomFromGrid = createGuidelineFromBottom(0.29f)
            val middleFromGridUp = createGuidelineFromBottom(0.38f)
            val middleFromGridDown = createGuidelineFromBottom(0.37f)

            Image(
                painter = painterResource(
                    if (mqttViewModel.fgtc.value) R.drawable.mainviewfgtc else {
                        if (mqttViewModel.fatc.value) R.drawable.mainviewfatc else {
                            if (mqttViewModel.fsta.value) R.drawable.mainviewfsta else {
                                if (mqttViewModel.fstc.value) R.drawable.mainviewfstc else {
                                    R.drawable.mainviewfstc
                                }
                            }
                        }
                    }
                ),
                contentDescription = "mainView",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(mainImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
            )
            /**
            ПАНЕЛИ
             **/
            Box(modifier = Modifier
                .constrainAs(toPanelUp) {
                    start.linkTo(startToPanel)
                    end.linkTo(endToPanel)
                    bottom.linkTo(middleToPanelDown)
                }
            ) {
                Text(
                    fontSize = 35.sp,
                    text = "${mqttViewModel.pv.value}",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colors.onBackground,
                )
            }
            Box(modifier = Modifier
                .constrainAs(toPanelDown) {
                    top.linkTo(middleToPanelUp)
                    start.linkTo(startToPanel)
                    end.linkTo(endToPanel)
                }
            ) {
                Text(
                    fontSize = 17.sp,
                    text = "кВт",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colors.onBackground,
                )
            }
            /**
            ПОТРЕБИТЕЛЬ
             **/
            Box(modifier = Modifier
                .constrainAs(toConsumerUp) {
                    start.linkTo(startToConsumer)
                    end.linkTo(endToConsumer)
                    bottom.linkTo(middleToConsumerDown)

                }
            ) {
                Text(
                    fontSize = 16.sp,
                    text = "${mqttViewModel.pv1.value}",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
            Box(modifier = Modifier
                .constrainAs(toConsumerDown) {
                    top.linkTo(middleToConsumerUp)
                    start.linkTo(startToConsumer)
                    end.linkTo(endToConsumer)
                }
            ) {
                Text(
                    fontSize = 12.sp,
                    text = "кВт",
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                )
            }
            /**
            В СЕТЬ
             **/
            Box(modifier = Modifier
                .constrainAs(toGridUp) {
//                top.linkTo(topToConsumer)
                    start.linkTo(startToGrid)
                    end.linkTo(endToGrid)
                    bottom.linkTo(middleToConsumerDown)
                }
            ) {
                Text(
                    fontSize = 16.sp,
                    text = if (mqttViewModel.toGrid.value > 0) "${mqttViewModel.toGrid.value}" else "",
//            fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
            Box(modifier = Modifier
                .constrainAs(toGridDown) {
                    top.linkTo(middleToConsumerUp)
                    start.linkTo(startToGrid)
                    end.linkTo(endToGrid)
//                bottom.linkTo(bottomToConsumer)
                }
            ) {
                Text(
                    fontSize = 12.sp,
                    text = if (mqttViewModel.toGrid.value > 0) "кВт" else "",
//            fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                )
            }
            /**
            ИЗ СЕТИ
             **/
            Box(modifier = Modifier
                .constrainAs(fromGridUp) {
//                top.linkTo(topFromGrid)
                    start.linkTo(startFromGrid)
                    end.linkTo(endFromGrid)
                    bottom.linkTo(middleFromGridDown)
                }
            ) {
                Text(
                    fontSize = 20.sp,
                    text = if (mqttViewModel.fromGrid.value > 0) "${mqttViewModel.fromGrid.value}" else "",
//            fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                )
            }
            Box(modifier = Modifier
                .constrainAs(fromGridDown) {
                    top.linkTo(middleFromGridUp)
                    start.linkTo(startFromGrid)
                    end.linkTo(endFromGrid)
//                bottom.linkTo(bottomFromGrid)
                }
            ) {
                Text(
                    fontSize = 14.sp,
                    text = if (mqttViewModel.fromGrid.value > 0) "кВт" else "",
//            fontFamily = MyFontsFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                )
            }
        }
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp, top = 10.dp, bottom = 10.dp)
        ) {
            item {
                IndexInverter(title = "Мощность выходная", value = "${mqttViewModel.pvBef.value} кВт", "P")
            }
            item {
                IndexInverter(title = "Мощность PV1", value = "${mqttViewModel.pv1Bef.value} кВт", "P")
            }
            item {
                IndexInverter(title = "Напряжение PV1", value = "${mqttViewModel.pv1_voltage.value} В", "V")
            }
            item {
                IndexInverter(title = "Ток PV1", value = "${mqttViewModel.pv1_current.value} А", "A")
            }
            item {
                IndexInverter(title = "Мощность PV2", value = "${mqttViewModel.pv2.value} кВт", "P")
            }
            item {
                IndexInverter(title = "Напряжение PV2", value = "${mqttViewModel.pv2_voltage.value} В", "V")
            }
            item {
                IndexInverter(title = "Ток PV2", value = "${mqttViewModel.pv2_current.value} А", "A")
            }
            item {
                IndexInverter(title = "Напряжение сети", value = "${mqttViewModel.grid_voltage.value} В", "V")
            }
            item {
                IndexInverter(title = "Ток сети", value = "${mqttViewModel.grid_current.value} А", "A")
            }
            item {
                IndexInverter(title = "Частота сети", value = "${mqttViewModel.grid_frequency.value} Гц", "F")
            }
        }
    }
}


@Composable
fun IndexInverter(title:String, value:String, symbol: String) {
    Box(Modifier.padding(vertical = 10.dp)) {
        Row {
            Box(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Normal, color = Color.Gray,modifier = Modifier.align(Alignment.CenterStart))
            }
            Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = value,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 20.dp)
                )
            }
        }
    }
}

