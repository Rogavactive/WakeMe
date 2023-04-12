package ge.rogavactive.common.serialization

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

object LocalTimeJsonDeserializer : JsonDeserializer<LocalTime> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalTime {
        val timeStr = json.asJsonPrimitive.asString
        val time = try {
            LocalDateTime
                .parse(timeStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                .atOffset(ZoneOffset.UTC)
                .atZoneSameInstant(ZoneId.systemDefault())
                .toLocalTime()
        } catch (e: DateTimeParseException) {
            e.printStackTrace()
            LocalTime.MIN
        }
        return time
    }
}