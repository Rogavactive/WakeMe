package ge.rogavactive.alarm.ui.alarmdetails

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ge.rogavactive.alarm.viewModel.AlarmViewModel

object AlarmDetailsScreenInfo {
    const val route = "AlarmDetailsScreen"
    const val ARG_ID = "alarmId"
}

@Composable
fun AlarmDetailsScreenComposable(
    id: Int? = -1,
    viewModel: AlarmViewModel = hiltViewModel()
) {
    AlarmClockComposable()
}
