package ge.rogavactive.common

import java.util.*

fun Calendar.getCurrentTimeOfDayFormatted() : String {
    val currentHour = get(Calendar.HOUR_OF_DAY)
    val currentMinute = get(Calendar.MINUTE).toString().padStart(2, '0')
    return "${currentHour}:$currentMinute"
}

fun Calendar.getTimeAsAngle() : Float {
    val currentHour = get(Calendar.HOUR_OF_DAY)
    val currentMinute = get(Calendar.MINUTE)
    return ( 15f * currentHour ) + (0.25f * currentMinute)
}