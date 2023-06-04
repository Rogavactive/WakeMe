package ge.rogavactive.alarm.ui.alarmdetails

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ge.rogavactive.common.DarkBlue
import ge.rogavactive.common.DarkGray
import ge.rogavactive.common.LightGray
import ge.rogavactive.common.getTimeAsAngle
import java.time.LocalTime
import java.util.*
import kotlin.math.abs

@Composable
fun AlarmClockComposable() {
    val currentTime = Calendar.getInstance()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        val currentTimeAngle = currentTime.getTimeAsAngle()
        val sweepValue = remember { mutableStateOf(0f.toLocalTime()) }
        val lastVibrationAngle = remember { mutableStateOf(-1f) }
        val sweepAngle = remember { mutableStateOf(120f) }

        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager: VibratorManager = LocalContext.current.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            // backward compatibility for Android API < 31,
            // VibratorManager was only added on API level 31 release.
            // noinspection deprecation
            LocalContext.current.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        CircularSlider(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            startAngle = currentTimeAngle,
            startSweepAngle = sweepAngle.value
        ) {
            if (abs(it - lastVibrationAngle.value) > FIVE_MIN_INTERVAL) {
                if (lastVibrationAngle.value != -1f)
                    vibrator.vibrateOnce()
                lastVibrationAngle.value = it
            }
            val newVal = it.toLocalTime()
            if (newVal.hour != sweepValue.value.hour || newVal.minute != sweepValue.value.minute)
                sweepValue.value = newVal
        }
        AlarmNumberClock(time = sweepValue.value) { hour, minute ->
            if (hour != sweepValue.value.hour || minute != sweepValue.value.minute) {
                var newSweepAngle = getTimeAsAngle(hour, minute) - currentTimeAngle
                if (newSweepAngle < 0f) newSweepAngle += 360f
                sweepAngle.value = newSweepAngle
                Log.d("dimadima", "$hour:$minute")
            }
        }
    }


}

fun Vibrator.vibrateOnce() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrate(VibrationEffect.createOneShot(10, 255))
    } else {
        // backward compatibility for Android API < 26
        // noinspection deprecation
        vibrate(10)
    }
}

fun Float.toLocalTime(): LocalTime {
    val totalSeconds = (this * 24 * 60 * 60).toInt()
    val hours = totalSeconds / (60 * 60)
    val minutes = (totalSeconds % (60 * 60)) / 60
    return LocalTime.of(hours, minutes)
}

private const val FIVE_MIN_INTERVAL = 1f / (24f * (60f / 5f))