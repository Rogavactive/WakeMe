package ge.rogavactive.alarm.ui.alarmdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ge.rogavactive.common.R
import ge.rogavactive.common.getCurrentTimeOfDayFormatted
import ge.rogavactive.common.getTimeAsAngle
import java.util.*

@Composable
fun AlarmClockComposable() {
    val currentTime = Calendar.getInstance()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val currentTimeAngle = currentTime.getTimeAsAngle()

        CircularSlider(
            modifier = Modifier.fillMaxSize(),
            stroke = 40f,
            startAngle = currentTimeAngle,
            startSweepAngle = 90f
        ) {

        }
        Column {
            Text(text = stringResource(id = R.string.current_time))
            // TODO change time to follow minutes
            Text(text = currentTime.getCurrentTimeOfDayFormatted())
            // 8 hours of sleep
            // target time
        }
    }


}