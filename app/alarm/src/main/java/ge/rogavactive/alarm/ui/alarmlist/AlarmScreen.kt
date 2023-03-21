package ge.rogavactive.alarm.ui.alarmlist

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ge.rogavactive.alarm.viewModel.AlarmViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ge.rogavactive.alarm.ui.alarmlist.AlarmListItem

@Composable
fun AlarmScreenComposable(
    onOpenDetailsClick : (Int) -> Unit,
    viewModel: AlarmViewModel = hiltViewModel()
) {
    val alarms = viewModel.alarms.collectAsState()
    LazyColumn (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        items(items = alarms.value){ item ->
            Spacer(modifier = Modifier.height(8.dp))
            AlarmListItem(data = item, onClick = onOpenDetailsClick, onDuplicate = {}, onRemove = {})
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
