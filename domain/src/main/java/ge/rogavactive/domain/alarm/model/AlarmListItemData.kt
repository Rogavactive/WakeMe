package ge.rogavactive.domain.alarm.model

import java.util.Date

data class AlarmListItemData(
    val id: Int,
    val time: Date, // TODO: change date format
    val message: String
)