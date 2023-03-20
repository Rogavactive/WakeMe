package ge.rogavactive.domain.alarm.usecasse

import ge.rogavactive.domain.alarm.repository.AlarmRepository
import javax.inject.Inject

class UpdateAllAlarmsUseCase @Inject constructor(
    private val repository: AlarmRepository
) {

    suspend operator fun invoke() {
        return repository.update()
    }

}
