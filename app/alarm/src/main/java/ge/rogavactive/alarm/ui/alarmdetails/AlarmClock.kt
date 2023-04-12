package ge.rogavactive.alarm.ui.alarmdetails

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ge.rogavactive.common.R
import ge.rogavactive.common.getCurrentTimeOfDayFormatted
import ge.rogavactive.common.getTimeAsAngle
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.abs

@Composable
fun AlarmClockComposable() {
    val currentTime = Calendar.getInstance()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val currentTimeAngle = currentTime.getTimeAsAngle()
        val sweepValue = remember { mutableStateOf(0f) }
        val lastVibrationAngle = remember { mutableStateOf(-1f) }

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
            modifier = Modifier.fillMaxSize(),
            startAngle = currentTimeAngle,
            startSweepAngle = 120f
        ) {
            if (abs(it - lastVibrationAngle.value) > FIVE_MIN_INTERVAL) {
                if (lastVibrationAngle.value != -1f)
                    vibrator.vibrateOnce()
                lastVibrationAngle.value = it
            }
            sweepValue.value = it
        }
        Column {
            Text(text = stringResource(id = R.string.current_time))
            // TODO change time to follow minutes
            Text(text = currentTime.getCurrentTimeOfDayFormatted())
            // 8 hours of sleep
            // target time
            Text(text = sweepValue.value.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
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
    val seconds = totalSeconds % 60
    return LocalTime.of(hours, minutes, seconds)
}

private const val FIVE_MIN_INTERVAL = 1f / (24f * (60f / 5f))