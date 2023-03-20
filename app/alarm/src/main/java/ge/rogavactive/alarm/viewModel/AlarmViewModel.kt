package ge.rogavactive.alarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.rogavactive.domain.alarm.model.AlarmListItemData
import ge.rogavactive.domain.alarm.usecasse.GetAllAlarmsUseCase
import ge.rogavactive.domain.alarm.usecasse.UpdateAllAlarmsUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val getAllAlarms: GetAllAlarmsUseCase,
    private val updateAlarmsCache: UpdateAllAlarmsUseCase
):ViewModel() {

    val alarms: StateFlow<List<AlarmListItemData>>
        get() = getAllAlarms()

    init {
        // TODO: Handle error here
        viewModelScope.launch {
            updateAlarmsCache()
        }

    }

}