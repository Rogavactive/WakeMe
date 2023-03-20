package ge.rogavactive.domain.alarm.usecasse

import ge.rogavactive.domain.alarm.model.AlarmListItemData
import ge.rogavactive.domain.alarm.repository.AlarmRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAllAlarmsUseCase @Inject constructor(
    private val repository: AlarmRepository
) {

    operator fun invoke(): StateFlow<List<AlarmListItemData>> {
        return repository.getAll()
    }

}