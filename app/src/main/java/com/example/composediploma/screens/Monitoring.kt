package com.example.composediploma.screens

import android.util.Log
import androidx.compose.foundation.Image
//import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.composediploma.R
import com.example.composediploma.mqtt.MQTTViewModel
import kotlin.concurrent.timer


@Composable
fun Monitoring(viewModel: MQTTViewModel){
    LazyColumn() {
        item {
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
                val middleToPanelUp = createGuidelineFromBottom(0.56f)
                val middleToPanelDown = createGuidelineFromBottom(0.545f)

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
                        if (viewModel.fgtc.value) R.drawable.mainviewfgtc else{
                            if (viewModel.fatc.value) R.drawable.mainviewfatc else{
                                if (viewModel.fsta.value) R.drawable.mainviewfsta else {
                                    if (viewModel.fstc.value) R.drawable.mainviewfstc else {
                                        R.drawable.mainviewfstc
                                    }
                                }
                            }
                        }),
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
//                top.linkTo(topToPanel)
                        start.linkTo(startToPanel)
                        end.linkTo(endToPanel)
                        bottom.linkTo(middleToPanelDown)
                    }
                ) {
                    Text(
                        fontSize = 30.sp,
                        text = "${viewModel.pv.value}",
//            fontFamily = MyFontsFamily,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
//                modifier = Modifier.align(Alignment.Center)
                    )
                }
                Box(modifier = Modifier
                    .constrainAs(toPanelDown) {
                        top.linkTo(middleToPanelUp)
                        start.linkTo(startToPanel)
                        end.linkTo(endToPanel)
//                bottom.linkTo(bottomToPanel)
                    }
                ) {
                    Text(
                        fontSize = 20.sp,
                        text = "кВт",
//            fontFamily = MyFontsFamily,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
//                modifier = Modifier.align(Alignment.Center)
                    )
                }
/**
                ПОТРЕБИТЕЛЬ
**/
                Box(modifier = Modifier
                    .constrainAs(toConsumerUp) {
//                top.linkTo(topToConsumer)
                        start.linkTo(startToConsumer)
                        end.linkTo(endToConsumer)
                        bottom.linkTo(middleToConsumerDown)

                    }
                ) {
                    Text(
                        fontSize = 16.sp,
                        text = "${viewModel.pv1.value}",
//                text = viewModel.message.value,
//                fontFamily = MyFontsFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
//                modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
                Box(modifier = Modifier
                    .constrainAs(toConsumerDown) {
                        top.linkTo(middleToConsumerUp)
                        start.linkTo(startToConsumer)
                        end.linkTo(endToConsumer)
//                bottom.linkTo(bottomToConsumer)
                    }
                ) {
                    Text(
                        fontSize = 12.sp,
                        text = "кВт",
//                text = viewModel.message.value,
//                fontFamily = MyFontsFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
//                modifier = Modifier.align(Alignment.TopCenter)
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
                        text = if(viewModel.toGrid.value>0)"${viewModel.toGrid.value}" else "",
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
                        text = if(viewModel.toGrid.value>0)"кВт" else "",
//            fontFamily = MyFontsFamily,
                        fontWeight = FontWeight.Bold,
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
                        text = if(viewModel.fromGrid.value>0)"${viewModel.fromGrid.value}" else "",
//            fontFamily = MyFontsFamily,
                        fontWeight = FontWeight.Bold,
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
                        text = if(viewModel.fromGrid.value>0)"кВт" else "",
//            fontFamily = MyFontsFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
            }
        }
        item {
            Row(Modifier.fillMaxWidth()){
                Column(Modifier.padding(start = 22.dp, end = 22.dp, top = 10.dp, bottom = 10.dp)) {
                    IndexInverter(title = "Мощность выходная", value = "${viewModel.pv.value} кВт",
                        "P")
                    IndexInverter(title = "Мощность PV1", value = "${viewModel.pv1.value} кВт",
                        "P")
                    IndexInverter(title = "Напряжение PV1", value = "${viewModel.pv1_voltage.value} В",
                        "V")
                    IndexInverter(title = "Ток PV1", value = "${viewModel.pv1_current.value} А",
                        "A")
//                }
//                Column(Modifier.padding(start = 11.dp, end = 22.dp)) {
                    IndexInverter(title = "Мощность PV2", value = "${viewModel.pv2.value} кВт",
                        "P")
                    IndexInverter(title = "Напряжение PV2", value = "${viewModel.pv2_voltage.value} В",
                        "V")
                    IndexInverter(title = "Ток PV2", value = "${viewModel.pv2_current.value} А",
                        "A")
                    IndexInverter(title = "Напряжение сети", value = "${viewModel.grid_voltage.value} В",
                        "V")
                    IndexInverter(title = "Ток сети", value = "${viewModel.grid_current.value} А",
                        "A")
                    IndexInverter(title = "Частота сети", value = "${viewModel.grid_frequency.value} Гц",
                        "F")

                }
            }
        }
    }
}

@Composable
fun IndexInverter(title:String, value:String, symbol: String) {
    Box(Modifier.padding(vertical = 10.dp)) {
        Row {
//            Box(modifier = Modifier
//                .padding(end = 14.dp)
//                .align(Alignment.CenterVertically))
//            {
//                Text(text = symbol, fontSize = 30.sp)
//            }
//            Column() {
            Box(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Normal, color = Color.Gray,modifier = Modifier.align(Alignment.CenterStart))
            }
            Box(modifier = Modifier.weight(1f)) {
//                Box(modifier = Modifier.align(Alignment.CenterStart)) {
                    Text(
                        text = value,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.align(Alignment.CenterStart).padding(start = 20.dp)
                    )
//                }
            }
        }
    }
}

