package ge.rogavactive.alarm.ui.alarmdetails

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ge.rogavactive.alarm.viewModel.AlarmViewModel

object AlarmDetailsScreenInfo {
    const val route = "AlarmDetailsScreen"
}

@Composable
fun AlarmDetailsScreenComposable(
    viewModel: AlarmViewModel = hiltViewModel()
) {
    AlarmClockComposable()
}
