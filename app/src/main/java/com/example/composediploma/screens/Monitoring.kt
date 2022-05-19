package com.example.composediploma.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
            painter = painterResource(R.drawable.mainviewfsta),
            contentDescription = "mainView",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(mainImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
        )
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
                text = "2.45",
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
                text = "0.27",
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
                text = "0.35",
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
                text = "кВт",
//            fontFamily = MyFontsFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }

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
                text = "3.27",
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
                text = "кВт",
//            fontFamily = MyFontsFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }
    }
}
}


