package ge.rogavactive.domain.alarm.repository

import ge.rogavactive.domain.alarm.model.AlarmListItemData
import kotlinx.coroutines.flow.StateFlow

interface AlarmRepository {

    fun getAll(): StateFlow<List<AlarmListItemData>>

    suspend fun update()

}