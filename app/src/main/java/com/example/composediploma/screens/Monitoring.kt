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
import kotlin.concurrent.timer


@Preview(showBackground = true)
@Composable
fun Monitoring(){
    Column() {
        var img by remember { mutableStateOf(false) }

//    Box(
//        modifier = Modifier
//        .fillMaxSize(),
//        contentAlignment = Alignment.TopCenter
//    ){
//        Image(
//            painter = painterResource(R.drawable.mainview1),
//            contentDescription = "mainView",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxWidth(),
//        )
//        Text(
//            fontSize = 28.sp,
//            text = "2.45 kW",
////            fontFamily = MyFontsFamily,
//            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colors.onBackground,
//            modifier = Modifier
//                .padding(vertical = 90.dp),
//        )
//        Box(modifier = Modifier.fillMaxWidth()) {
//
//            Text(
//                fontSize = 14.sp,
//                text = "1.21\n kW",
////            fontFamily = MyFontsFamily,
//                fontWeight = FontWeight.Bold,
//                color = Color.White,
////                modifier = Modifier
////                    .align(),
//            )
//            Text(
//                fontSize = 14.sp,
//                text = "0.87\n kW",
////            fontFamily = MyFontsFamily,
//                fontWeight = FontWeight.Bold,
//                color = Color.White,
//                modifier = Modifier
//                    .padding(top = 282.dp, start = 263.dp ),
//            )
//        }
//    }
//Box(Modifier.wrapContentSize().background(Color.Gray)) {

//    Image(
//            painter = painterResource(R.drawable.mainview1),
//            contentDescription = "mainView",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxWidth().alpha(0.5f),
//        )

    ConstraintLayout(
//        modifier = Modifier.fillMaxSize()
    ) {
        val mainImage = createRef()
        val toPanel = createRef()
        val toConsumer = createRef()
        val toGrid = createRef()
        val startToPanel = createGuidelineFromStart(0.37f)
        val topToPanel = createGuidelineFromTop(0.33f)
        val startToConsumer = createGuidelineFromStart(0.43f)
        val topToConsumer = createGuidelineFromTop(0.81f)
        val startToGrid = createGuidelineFromStart(0.67f)
        val topToGrid = createGuidelineFromTop(0.81f)

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
//        Text(
//            fontSize = 28.sp,
//            text = "2.45 kW",
////            fontFamily = MyFontsFamily,
//            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colors.onBackground,
//            modifier = Modifier.constrainAs(toPanel) {
//                top.linkTo(topToPanel)
//                start.linkTo(startToPanel)
//            }
//        )
//        Text(
//            fontSize = 14.sp,
//            text = "1.21\n kW",
////            fontFamily = MyFontsFamily,
//            fontWeight = FontWeight.Bold,
//            color = Color.White,
////                modifier = Modifier
//            modifier = Modifier.constrainAs(toConsumer) {
//                top.linkTo(topToConsumer)
//                start.linkTo(startToConsumer)
//            }
//        )
//        Text(
//            fontSize = 14.sp,
//            text = "0.87\n kW",
////            fontFamily = MyFontsFamily,
//            fontWeight = FontWeight.Bold,
//            color = Color.White,
//            modifier = Modifier.constrainAs(toGrid) {
//                top.linkTo(topToGrid)
//                start.linkTo(startToGrid)
//            }
//        )
    }
//        Button(onClick = { img = !img}) {
//
//        }
}
}


