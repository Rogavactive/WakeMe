package ge.rogavactive.data.alarm

import ge.rogavactive.domain.alarm.model.AlarmListItemData
import ge.rogavactive.domain.alarm.repository.AlarmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor() : AlarmRepository {

    private val _alarmsList = MutableStateFlow(emptyList<AlarmListItemData>())

    override fun getAll(): StateFlow<List<AlarmListItemData>> = _alarmsList.asStateFlow()

    override suspend fun update() {
        _alarmsList.value =
        MutableList(25) {
            AlarmListItemData(
                id = it,
                time = Date(),
                message = "Alarm $it"
            )
        }
    }
}