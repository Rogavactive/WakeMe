package ge.rogavactive.alarm.ui.alarmdetails

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ge.rogavactive.alarm.viewModel.AlarmViewModel
import ge.rogavactive.common.DarkBlue
import ge.rogavactive.common.DarkGray
import ge.rogavactive.common.PrimarySunOrange
import ge.rogavactive.common.getTimeAsFloat
import kotlin.math.*

// TODO: Rework logic so it contains only necessary info for me and nothing more.
//  should not resemble old slider I borrowed logic from
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CircularSlider(
    modifier: Modifier = Modifier,
    padding: Float = 50f,
    stroke: Float = 20f,
    selectedStroke: Float = 40f,
    cap: StrokeCap = StrokeCap.Round,
    touchStroke: Float = 100f,
    thumbColor: Color = Color.Black,
    backgroundColor: Color = Color.LightGray,
    startAngle: Float = 0f,
    startSweepAngle: Float = 0f,
    debug: Boolean = false,
    viewModel: AlarmViewModel = hiltViewModel(),
    onChange: ((Float) -> Unit)? = null,
) {
    var width by remember { mutableStateOf(0) }
    var height by remember { mutableStateOf(0) }
    var angle by remember { mutableStateOf((startAngle + startSweepAngle) % 360f) }
    var last by remember { mutableStateOf(0f) }
    var down by remember { mutableStateOf(false) }
    var radius by remember { mutableStateOf(0f) }
    var center by remember { mutableStateOf(Offset.Zero) }
    var appliedAngle by remember { mutableStateOf(0f) }

    LaunchedEffect(key1 = startSweepAngle) {
        angle = (startAngle + startSweepAngle) % 360f
    }

    val sunriseSunsetData = viewModel.sunriseSunsetData.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = angle) {
        var a = angle
        if (a <= 0f) {
            a += 360
        }
        a = a.coerceIn(0f, 360f)
        if (last < 180f && a == 360f) {
            a = 0f
        }
        last = a
        appliedAngle = a
    }
    LaunchedEffect(key1 = appliedAngle) {
        onChange?.invoke(appliedAngle / 360f)
    }
    Canvas(
        modifier = modifier
            .onGloballyPositioned {
                width = it.size.width
                height = it.size.height
                center = Offset(width / 2f, height / 2f)
                radius = min(width.toFloat(), height.toFloat()) / 2f - padding - stroke / 2f
            }
            .pointerInteropFilter {
                val x = it.x
                val y = it.y
                val offset = Offset(x, y)
                when (it.action) {

                    MotionEvent.ACTION_DOWN -> {
                        val d = distance(offset, center)
                        val a = angle(center, offset)
                        if (d >= radius - touchStroke / 2f && d <= radius + touchStroke / 2f) {
                            down = true
                            angle = a
                        } else {
                            down = false
                        }
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if (down) {
                            angle = angle(center, offset)
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        down = false
                    }
                    else -> return@pointerInteropFilter false
                }
                return@pointerInteropFilter true
            }
    ) {
        drawArc(
            color = backgroundColor,
            startAngle = 0f,
            sweepAngle = 360f,
            topLeft = center - Offset(radius, radius),
            size = Size(radius * 2, radius * 2),
            useCenter = false,
            style = Stroke(
                width = stroke,
                cap = cap
            ),
        )
        //TODO: move this to somewhere else. we need this calculation only number of times sunriseData changes
        val gradientColors = listOf(
            sunriseSunsetData.value.twilightBegin.getTimeAsFloat().startFromTop() to DarkBlue,
            sunriseSunsetData.value.sunrise.getTimeAsFloat().startFromTop() to PrimarySunOrange,
            sunriseSunsetData.value.sunset.getTimeAsFloat().startFromTop() to PrimarySunOrange,
            sunriseSunsetData.value.twilightEnd.getTimeAsFloat().startFromTop() to DarkBlue,
        ).map {
            Pair(if (it.first < 0f) it.first + 1f else it.first, it.second)
        }.toMutableList()
        val gradientColorsSorted = gradientColors
            .toList().sortedBy {
                it.first
            }
        if (gradientColorsSorted.first().second != gradientColorsSorted.last().second) {
            val end = gradientColorsSorted.first()
            val start = gradientColorsSorted.last()
            val fractionToBorder = (1f - start.first) / (1f - start.first + end.first)
            val borderColor = Color(
                red = applyFraction(start.second.red, end.second.red, fractionToBorder),
                green = applyFraction(start.second.green, end.second.green, fractionToBorder),
                blue = applyFraction(start.second.blue, end.second.blue, fractionToBorder),
            )
            gradientColors.add(Pair(0f, borderColor))
            gradientColors.add(Pair(1f, borderColor))
        }
        drawArc(
            brush = Brush.sweepGradient(*gradientColors.sortedBy { it.first }.toTypedArray()),
            startAngle = startAngle - 90f,
            sweepAngle = (appliedAngle + (360f - startAngle)) % 360f,
            topLeft = center - Offset(radius, radius),
            size = Size(radius * 2, radius * 2),
            useCenter = false,
            style = Stroke(
                width = selectedStroke,
                cap = cap
            )
        )
        drawCircle(
            color = thumbColor,
            radius = stroke + 20f,
            center = center + Offset(
                radius * cos((-90 + appliedAngle) * PI / 180f).toFloat(),
                radius * sin((-90 + appliedAngle) * PI / 180f).toFloat()
            )
        )
//        drawLine(
//            color = DarkBlue,
//            start = center,
//            end = center.minus(Offset(200f, 0f))
//        )
        if (debug) {
            drawRect(
                color = Color.Green,
                topLeft = Offset.Zero,
                size = Size(width.toFloat(), height.toFloat()),
                style = Stroke(
                    4f
                )
            )
            drawRect(
                color = Color.Red,
                topLeft = Offset(padding, padding),
                size = Size(width.toFloat() - padding * 2, height.toFloat() - padding * 2),
                style = Stroke(
                    4f
                )
            )
            drawRect(
                color = Color.Blue,
                topLeft = Offset(padding, padding),
                size = Size(width.toFloat() - padding * 2, height.toFloat() - padding * 2),
                style = Stroke(
                    4f
                )
            )
            drawCircle(
                color = Color.Red,
                center = center,
                radius = radius + stroke / 2f,
                style = Stroke(2f)
            )
            drawCircle(
                color = Color.Red,
                center = center,
                radius = radius - stroke / 2f,
                style = Stroke(2f)
            )
        }
    }
}

fun angle(center: Offset, offset: Offset): Float {
    val rad = atan2(center.y - offset.y, center.x - offset.x)
    val deg = Math.toDegrees(rad.toDouble())
    return deg.toFloat() - 90f
}

fun distance(first: Offset, second: Offset): Float {
    return sqrt((first.x - second.x).square() + (first.y - second.y).square())
}

fun Float.square(): Float {
    return this * this
}

fun Float.startFromTop(): Float {
    return this - 0.25f
}

fun applyFraction(first: Float, second: Float, fractionToBorder: Float): Float {
    return first + (abs(second - first) * fractionToBorder)
}

@Preview(heightDp = 200, widthDp = 200, backgroundColor = 0xFFDEDEDE)
@Composable
fun PreviewCircullarSlider() {
    CircularSlider(
        modifier = Modifier.fillMaxSize(),
        startAngle = 0f,
        startSweepAngle = 180f
    )
}