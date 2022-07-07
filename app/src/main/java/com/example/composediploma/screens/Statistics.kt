package com.example.composediploma.screens

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.composediploma.mqtt.MySqlViewModel
//import com.example.composediploma.screens.DataPoints.dataPoints1
//import com.example.composediploma.screens.DataPoints.dataPoints2
//import com.example.composediploma.screens.DataPoints.dataPoints3
import com.example.composediploma.ui.theme.Shapes
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import me.bytebeats.views.charts.bar.BarChart
import me.bytebeats.views.charts.bar.BarChartData
import me.bytebeats.views.charts.bar.render.bar.SimpleBarDrawer
import me.bytebeats.views.charts.bar.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.bar.render.yaxis.SimpleYAxisDrawer
import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.simpleChartAnimation
import java.text.DecimalFormat
import java.util.*


//@Preview(showBackground = true)
@Composable
fun Statistics(context: Context, sqlViewModel: MySqlViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 20.dp),
        contentAlignment = Alignment.TopCenter
    )
    {
        Column() {
//            DatePickerDemo(context)
            DatePickerview(context,sqlViewModel)

            if(sqlViewModel.listOfDataPointMeteo.value != listOf(DataPoint(0f,0f))){
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(
                        fontSize = 20.sp,
                        text = "Выработка: ${sqlViewModel.energyToday.value} кВт",
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 10.dp, top = 20.dp)
                    )
                }
                LineGraph4(
                    listOf(sqlViewModel.listOfDataPointSes.value, sqlViewModel.listOfDataPointMeteo.value),
                    Modifier,
                    sqlViewModel
                )
            }
        }
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

@Composable
fun LineChartView() {
    LineChart(
        lineChartData = LineChartData(
            points = listOf(
                LineChartData.Point(12F, "Line 1"),
                LineChartData.Point(34f, "Line 2"),
                LineChartData.Point(41f, "Line 3"),
                LineChartData.Point(11f, "Line 4"),
                LineChartData.Point(43f, "Line 5"),
                LineChartData.Point(94f, "Line 6"),
                LineChartData.Point(14f, "Line 7")
            )
        ),
        // Optional properties.
        modifier = Modifier.fillMaxSize(),
        animation = simpleChartAnimation(),
        pointDrawer = FilledCircularPointDrawer(),
        lineDrawer = SolidLineDrawer(),
//        xAxisDrawer = ,
//        yAxisDrawer = SimpleYAxisDrawer(),
        horizontalOffset = 5f
    )
}



@Composable
internal fun LineGraph1(lines: List<List<DataPoint>>) {
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    lines[0],
                    LinePlot.Connection(Color.Yellow, 2.dp),
                    LinePlot.Intersection(Color.Gray, 5.dp),
                    LinePlot.Highlight(Color.Green, 5.dp),
                    LinePlot.AreaUnderLine(Color.LightGray, 0.3f)
                ),
                LinePlot.Line(
                    lines[1],
                    LinePlot.Connection(Color.Gray, 2.dp),
                    LinePlot.Intersection { center, _ ->
                        val px = 2.dp.toPx()
                        val topLeft = Offset(center.x - px, center.y - px)
                        drawRect(Color.Gray, topLeft, Size(px * 2, px * 2))
                    },
                ),
            ),
            selection = LinePlot.Selection(
                highlight = LinePlot.Connection(
                    Color.Green,
                    strokeWidth = 2.dp,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f))
                )
            ),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

object DataPoints {
    val ses_22_05_03 = listOf(

        DataPoint(4f, 0f),
        DataPoint(5f, 24f),
        DataPoint(6f, 117f),
        DataPoint(7f, 292f),
        DataPoint(8f, 585f),
        DataPoint(9f, 1013f),
        DataPoint(10f, 801f),
        DataPoint(11f, 1292f),
        DataPoint(12f, 1561f),
        DataPoint(13f, 1807f),
        DataPoint(14f, 2049f),
        DataPoint(15f, 1857f),
        DataPoint(16f, 1067f),
        DataPoint(17f, 332f),
        DataPoint(18f, 45f),
        DataPoint(19f, 2f),
        DataPoint(20f, 0f),

    )

    val ses_22_05_04 = listOf(

        DataPoint(4f, 0f),
        DataPoint(5f, 58f),
        DataPoint(6f, 52f),
        DataPoint(7f, 257f),
        DataPoint(8f, 317f),
        DataPoint(9f, 444f),
        DataPoint(10f, 1251f),
        DataPoint(11f, 1226f),
        DataPoint(12f, 894f),
        DataPoint(13f, 1076f),
        DataPoint(14f, 1499f),
        DataPoint(15f, 684f),
        DataPoint(16f, 676f),
        DataPoint(17f, 487f),
        DataPoint(18f, 232f),
        DataPoint(19f, 16f),
        DataPoint(20f, 0f),

        )

    val ses_22_05_21 = listOf(

        DataPoint(4f, 16f),
        DataPoint(5f, 91f),
        DataPoint(6f, 167f),
        DataPoint(7f, 192f),
        DataPoint(8f, 547f),
        DataPoint(9f, 1228f),
        DataPoint(10f, 1856f),
        DataPoint(11f, 2074f),
        DataPoint(12f, 2309f),
        DataPoint(13f, 2686f),
        DataPoint(14f, 2082f),
        DataPoint(15f, 2315f),
        DataPoint(16f, 1465f),
        DataPoint(17f, 575f),
        DataPoint(18f, 299f),
        DataPoint(19f, 73f),
        DataPoint(20f, 0f),

        )

    val ses_22_05_22 = listOf(

        DataPoint(4f, 11f),
        DataPoint(5f, 94f),
        DataPoint(6f, 146f),
        DataPoint(7f, 187f),
        DataPoint(8f, 530f),
        DataPoint(9f, 1229f),
        DataPoint(10f, 1900f),
        DataPoint(11f, 2384f),
        DataPoint(12f, 2704f),
        DataPoint(13f, 2833f),
        DataPoint(14f, 2764f),
        DataPoint(15f, 2437f),
        DataPoint(16f, 2099f),
        DataPoint(17f, 1521f),
        DataPoint(18f, 597f),
        DataPoint(19f, 29f),
        DataPoint(20f, 0f),

        )





    val met_22_05_03 = listOf(

        DataPoint(4f, 0f),
        DataPoint(5f, 26f),
        DataPoint(6f, 84f),
        DataPoint(7f, 277f),
        DataPoint(8f, 392f),
        DataPoint(9f, 514f),
        DataPoint(10f, 314f),
        DataPoint(11f, 494f),
        DataPoint(12f, 580f),
        DataPoint(13f, 576f),
        DataPoint(14f, 599f),
        DataPoint(15f, 506f),
        DataPoint(16f, 325f),
        DataPoint(17f, 143f),
        DataPoint(18f, 35f),
        DataPoint(19f, 10f),
        DataPoint(20f, 0f),

        )

    val meteo_22_05_05 = listOf(

        DataPoint(4f, 0f),
        DataPoint(5f, 36f),
        DataPoint(6f, 44f),
        DataPoint(7f, 245f),
        DataPoint(8f, 210f),
        DataPoint(9f, 227f),
        DataPoint(10f, 482f),
        DataPoint(11f, 450f),
        DataPoint(12f, 291f),
        DataPoint(13f,  352f),
        DataPoint(14f, 444f),
        DataPoint(15f, 236f),
        DataPoint(16f, 195f),
        DataPoint(17f, 169f),
        DataPoint(18f, 160f),
        DataPoint(19f, 20f),
        DataPoint(20f, 0f),

        )

    val met_22_05_21 = listOf(

        DataPoint(4f, 14f),
        DataPoint(5f, 55f),
        DataPoint(6f, 160f),
        DataPoint(7f, 392f),
        DataPoint(8f, 524f),
        DataPoint(9f, 647f),
        DataPoint(10f, 740f),
        DataPoint(11f, 741f),
        DataPoint(12f, 725f),
        DataPoint(13f, 771f),
        DataPoint(14f, 594f),
        DataPoint(15f, 641f),
        DataPoint(16f, 398f),
        DataPoint(17f, 185f),
        DataPoint(18f, 133f),
        DataPoint(19f, 47f),
        DataPoint(20f, 5f),

        )

    val meteo_22_05_23 = listOf(

        DataPoint(4f, 9f),
        DataPoint(5f, 33f),
        DataPoint(6f, 141f),
        DataPoint(7f, 393f),
        DataPoint(8f, 535f),
        DataPoint(9f, 656f),
        DataPoint(10f, 751f),
        DataPoint(11f, 811f),
        DataPoint(12f, 837f),
        DataPoint(13f, 818f),
        DataPoint(14f, 759f),
        DataPoint(15f, 651f),
        DataPoint(16f, 545f),
        DataPoint(17f,  404f),
        DataPoint(18f, 205f),
        DataPoint(19f, 41f),
        DataPoint(20f, 12f),

        )
}

@Composable
internal fun LineGraph2(lines: List<List<DataPoint>>) {
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    lines[1],
                    LinePlot.Connection(Color.Gray, 2.dp),
                    null,
                    LinePlot.Highlight { center ->
                        val color = Color.Gray
                        drawCircle(color, 9.dp.toPx(), center, alpha = 0.3f)
                        drawCircle(color, 6.dp.toPx(), center)
                        drawCircle(Color.White, 3.dp.toPx(), center)
                    },
                ),
                LinePlot.Line(
                    lines[0],
                    LinePlot.Connection(Color.Blue, 3.dp),
                    LinePlot.Intersection(Color.Blue, 6.dp) { center, point ->
                        val x = point.x
                        val rad = if (x % 4f == 0f) 6.dp else 3.dp
                        drawCircle(
                            Color.Blue,
                            rad.toPx(),
                            center,
                        )
                    },
                    LinePlot.Highlight { center ->
                        val color = Color.Blue
                        drawCircle(color, 9.dp.toPx(), center, alpha = 0.3f)
                        drawCircle(color, 6.dp.toPx(), center)
                        drawCircle(Color.White, 3.dp.toPx(), center)
                    },
                    LinePlot.AreaUnderLine(Color.Blue, 0.1f)
                ),
            ), LinePlot.Grid(Color.Gray), paddingRight = 16.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}
@Composable
internal fun LineGraph3(lines: List<List<DataPoint>>) {
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    lines[0],
                    LinePlot.Connection(Color.Blue, 2.dp),
                    LinePlot.Intersection(Color.Blue, 4.dp),
                    LinePlot.Highlight(Color.Red, 6.dp),
                    LinePlot.AreaUnderLine(Color.Blue, 0.1f)
                )
            ), LinePlot.Grid(Color.LightGray.copy(0.5f)),
            xAxis = LinePlot.XAxis(steps = 24) { min, offset, max ->
                for (it in 0 until 24) {
                    val value = it * offset + min
                    androidx.compose.foundation.layout.Column {
                        val isMajor = value % 4 == 0f
                        val radius = if (isMajor) 6f else 3f
                        val color = MaterialTheme.colors.onSurface
                        Canvas(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .height(20.dp),
                            onDraw = {
                                drawCircle(
                                    color = color,
                                    radius * density,
                                    Offset(0f, 10f * density)
                                )
                            })
                        if (isMajor) {
                            Text(
                                text = DecimalFormat("#.#").format(value),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.caption,
                                color = color
                            )
                        }
                    }
                    if (value > max) {
                        break
                    }
                }
            }, paddingRight = 8.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}
@Composable
internal fun LineGraph4(lines: List<List<DataPoint>>, modifier: Modifier,sqlViewModel: MySqlViewModel) {
    val totalWidth = remember { mutableStateOf(0) }
    Column(Modifier.onGloballyPositioned {
        totalWidth.value = it.size.width
    }) {
        val xOffset = remember { mutableStateOf(0f) }
        val cardWidth = remember { mutableStateOf(0) }
        val visibility = remember { mutableStateOf(false) }
        val points = remember { mutableStateOf(listOf<DataPoint>()) }
        val density = LocalDensity.current

        Box(Modifier.height(150.dp)) {
            if (visibility.value) {
                Surface(
                    modifier = Modifier
                        .width(210.dp)
                        .align(Alignment.BottomCenter)
                        .onGloballyPositioned {
                            cardWidth.value = it.size.width
                        }
                        .graphicsLayer(translationX = xOffset.value),
                    shape = Shapes.medium,
                    color = Color.LightGray
                ) {
                    Column(
                        Modifier
                            .padding(horizontal = 8.dp)
                    ) {
                        val value = points.value
                        if (value.isNotEmpty()) {
                            val x = DecimalFormat("#.#").format(value[0].x)
                            Text(
                                modifier = Modifier.padding(vertical = 8.dp),
                                text = "Значение в $x:00",
                                style = MaterialTheme.typography.subtitle1,
                                color = Color.Gray
                            )
                            ScoreRow("Мощность(Вт)", value[1].y, Color.Blue)
                            ScoreRow("Радиация(Вт/м²)", value[0].y, Color.Yellow)
                        }
                    }
                }

            }
        }
//        val padding = 16.dp
        MaterialTheme(colors = MaterialTheme.colors.copy(surface = Color.White)) {
            LineGraph(
                plot = LinePlot(
                    listOf(
                        LinePlot.Line(
                            lines[1],
                            LinePlot.Connection(Color.Yellow, 2.dp),
                            null,
                            LinePlot.Highlight { center ->
                                val color = Color.Gray
                                drawCircle(color, 9.dp.toPx(), center, alpha = 0.3f)
                                drawCircle(color, 6.dp.toPx(), center)
                                drawCircle(Color.White, 3.dp.toPx(), center)
                            },
                            LinePlot.AreaUnderLine(color = Color.Yellow, alpha = 0.2f)
                        ),
                        LinePlot.Line(
                            lines[0],
                            LinePlot.Connection(),
                            LinePlot.Intersection(),
                            LinePlot.Highlight { center ->
                                val color = Color.Blue
                                drawCircle(color, 9.dp.toPx(), center, alpha = 0.3f)
                                drawCircle(color, 6.dp.toPx(), center)
                                drawCircle(Color.White, 3.dp.toPx(), center)
                            },
                            LinePlot.AreaUnderLine(color = Color.Blue, alpha = 0.01f)
                        ),
                    ),
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray),
//                    .padding(horizontal = padding),
                onSelectionStart = { visibility.value = true },
                onSelectionEnd = { visibility.value = false }
            ) { x, pts ->
                val cWidth = cardWidth.value.toFloat()
                var xCenter = x
//                +  with(density) { padding.toPx() }
                xCenter = when {
                    xCenter + cWidth / 2f > totalWidth.value -> totalWidth.value - cWidth
                    xCenter - cWidth / 2f < 0f -> 0f
                    else -> xCenter - cWidth / 2f
                }
                xOffset.value = xCenter
                points.value = pts
            }
        }
    }
}

@Composable
private fun ScoreRow(title: String, value: Float, color: Color) {
    val formatted = DecimalFormat("#.#").format(value)
    Box(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(modifier = Modifier.align(Alignment.CenterStart)) {
            Image(
                painter = ColorPainter(color), contentDescription = "Line color",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 4.dp)
                    .size(10.dp)
                    .clip(Shapes.medium)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1,
                color = Color.DarkGray
            )
        }
        Text(
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterEnd),
            text = formatted,
            style = MaterialTheme.typography.subtitle2,
            color = Color.DarkGray
        )
    }
}

@Composable
fun DatePickerDemo(context: Context) {
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val now = Calendar.getInstance()
    mYear = now.get(Calendar.YEAR)
    mMonth = now.get(Calendar.MONTH)
    mDay = now.get(Calendar.DAY_OF_MONTH)
    now.time = Date()
    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )

    val day1= Calendar.getInstance()
    day1.set(Calendar.DAY_OF_MONTH, 1)
    datePickerDialog.datePicker.minDate = day1.timeInMillis
    datePickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            datePickerDialog.show()
        }) {
            Text(text = "Click To Open Date Picker")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Selected date: ${date.value}")
    }
}


@Composable
fun DatePickerview(
    context: Context,
    sqlViewModel: MySqlViewModel
//    datePicked : String?,
//    updatedDate : ( date : Long? ) -> Unit,
) {

    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val now = Calendar.getInstance()
    mYear = now.get(Calendar.YEAR)
    mMonth = now.get(Calendar.MONTH)
    mDay = now.get(Calendar.DAY_OF_MONTH)
    now.time = Date()
    val date = remember { mutableStateOf("Выберите дату") }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth/${mMonth-1}/$mYear"
            sqlViewModel.state("$mYear-${if (month<10)"0${month+1}" else month+1}-${if (dayOfMonth<10)"0$dayOfMonth" else dayOfMonth}")
        }, mYear, mMonth, mDay
    )

    val day1= Calendar.getInstance()
    day1.set(Calendar.DAY_OF_YEAR, 90)
    datePickerDialog.datePicker.minDate = day1.timeInMillis
    datePickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
            .padding(10.dp)
            .border(0.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
            .clickable {
                datePickerDialog.show()
            }
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            val (lable, iconView) = createRefs()

            Text(
//                text = datePicked?:"Date Picker",
                text = date.value,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(lable) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(iconView.start)
                        width = Dimension.fillToConstraints
                    }
            )

            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    .constrainAs(iconView) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                tint = MaterialTheme.colors.onSurface
            )

        }

    }
}