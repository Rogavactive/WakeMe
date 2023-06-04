package ge.rogavactive.alarm.ui.alarmdetails

import android.util.LayoutDirection
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.LayoutDirection.Rtl
import java.time.LocalTime

@Composable
fun AlarmNumberClock(
    time: LocalTime,
    onChange: (Int, Int) -> Unit
) {
    val hour = remember { mutableStateOf(time.hour) }
    val minute = remember { mutableStateOf(time.minute) }
    LaunchedEffect(key1 = time.hour, key2 = time.minute) {
        hour.value = time.hour
        minute.value = time.minute
    }
    Box(
        modifier = Modifier
            .height(100.dp)
            .width(100.dp),
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides Rtl) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp, 0.dp)
            ) {

                AlarmNumberComposable(
                    initialNumber = minute.value,
                    numberSize = 60,
                    onChange = {
                        if (minute.value == 59 && it == 0) {
                            hour.value = (hour.value + 1) % 24
                        }
                        if (minute.value == 0 && it == 59) {
                            hour.value = (hour.value + 23) % 24
                        }
                        minute.value = it
                        onChange(hour.value, minute.value)
                    }
                )
                Text(
                    text = ":",
                    fontSize = 28.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 4.dp)
                        .wrapContentWidth()
                        .weight(1f)
                )

                AlarmNumberComposable(
                    initialNumber = hour.value,
                    numberSize = 24,
                    onChange = {
                        hour.value = it
                        onChange(hour.value, minute.value)
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush
                        .verticalGradient(
                            0f to MaterialTheme.colorScheme.background,
                            .35f to Color.Transparent,
                            .65f to Color.Transparent,
                            1f to MaterialTheme.colorScheme.background
                        )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(0.dp, 5.dp, 0.dp, 0.dp)
                .clip(shape = RoundedCornerShape(7.dp))
                .background(Color.Transparent)
                .drawBehind {
                    drawRect(
                        color = Color(0xFF, 0xFF, 0xFF, 0xFF),
                        blendMode = BlendMode.Difference,
                        topLeft = Offset.Zero,
                        size = size
                    )
                }
                .align(Alignment.Center)

        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmNumberComposable(
    initialNumber: Int = 0,
    numberSize: Int = 24,
    onChange: (Int) -> Unit
) {
    val height = 100.dp
    val cellSize = height / 3
    val cellTextSize = LocalDensity.current.run { (cellSize / 1.3f).toSp() }

    // just prepare an offset of 1 hour when format is set to 12hr format
    val expandedSize = numberSize * 10_000_000
    val initialListPoint = expandedSize / 2
    val targetIndex = initialListPoint + initialNumber - 1

    val scrollState = rememberLazyListState(targetIndex)
    val hour by remember { derivedStateOf { (scrollState.firstVisibleItemIndex + 1) % numberSize } }

    if (scrollState.isScrollInProgress) {
        onChange(hour)
    }

    LaunchedEffect(initialNumber) {
        // subtract the offset upon initial scrolling, otherwise it will look like
        // it moved 1 hour past the initial hour when format is set to 12hr format
        scrollState.scrollToItem(targetIndex)
    }

    Box(
        modifier = Modifier
            .height(height)
            .wrapContentWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .wrapContentWidth(),
            state = scrollState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = scrollState)
        ) {
            items(expandedSize, itemContent = {

                // if 12hr format, move 1 hour so instead of displaying 00 -> 11
                // it will display 01 to 12
                val num = (it % numberSize)
                Box(
                    modifier = Modifier
                        .size(cellSize),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = String.format("%02d", num),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.Black,
                            fontSize = cellTextSize
                        )
                    )
                }
            })
        }
    }

}