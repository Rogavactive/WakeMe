package ge.rogavactive.domain.sunrisesunset.model

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import java.time.LocalTime

data class SunriseSunsetResult(
    val results: SunriseSunsetData,
    val status: String
)

@SuppressLint("NewApi")
data class SunriseSunsetData(
    val sunrise: LocalTime = LocalTime.of(7,0),
    val sunset: LocalTime = LocalTime.of(19, 0),
    @SerializedName("astronomical_twilight_begin")
    val twilightBegin: LocalTime = LocalTime.of(5,0),
    @SerializedName("astronomical_twilight_end")
    val twilightEnd: LocalTime = LocalTime.of(21, 0)
) {

    companion object {
        @JvmStatic
        fun default(): SunriseSunsetData = SunriseSunsetData(
            sunrise = LocalTime.of(6, 0),
            sunset = LocalTime.of(20, 0),
            twilightBegin = LocalTime.of(5,0),
            twilightEnd = LocalTime.of(21, 0)
        )
    }

}