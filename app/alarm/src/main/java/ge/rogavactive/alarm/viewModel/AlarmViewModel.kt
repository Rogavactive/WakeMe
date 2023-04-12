package ge.rogavactive.alarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.rogavactive.domain.alarm.model.AlarmListItemData
import ge.rogavactive.domain.alarm.usecasse.GetAllAlarmsUseCase
import ge.rogavactive.domain.alarm.usecasse.UpdateAllAlarmsUseCase
import ge.rogavactive.domain.sunrisesunset.GetTodaySunriseSunsetUseCase
import ge.rogavactive.domain.sunrisesunset.model.SunriseSunsetData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val getAllAlarms: GetAllAlarmsUseCase,
    private val updateAlarmsCache: UpdateAllAlarmsUseCase,
    private val sunriseSunsetDataApi: GetTodaySunriseSunsetUseCase
):ViewModel() {

    val alarms: StateFlow<List<AlarmListItemData>>
        get() = getAllAlarms()

    private val _sunriseSunsetData = MutableStateFlow(SunriseSunsetData.default())
    val sunriseSunsetData: StateFlow<SunriseSunsetData>
        get() = _sunriseSunsetData.asStateFlow()

    init {
        // TODO: Handle error here
        viewModelScope.launch {
            updateAlarmsCache()
        }
        getSunsetSunriseData(41.6938f, 44.8015f)

    }

    fun getSunsetSunriseData(lat: Float, lng: Float) {
        // TODO: save latLng and get data in advance periodically.
        viewModelScope.launch {
            _sunriseSunsetData.value = sunriseSunsetDataApi(lat = lat, lng = lng)
        }
    }

}