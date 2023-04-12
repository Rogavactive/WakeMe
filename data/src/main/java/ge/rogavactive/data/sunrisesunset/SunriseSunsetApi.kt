package ge.rogavactive.data.sunrisesunset

import ge.rogavactive.domain.sunrisesunset.model.SunriseSunsetResult
import retrofit2.http.GET
import retrofit2.http.Query

interface SunriseSunsetApi {

    @GET("/json")
    suspend fun getSunsetSunriseData(
        @Query("lat") lat: Float,
        @Query("lng") lng: Float,
        @Query("formatted") formatted: Int = 0
    ): SunriseSunsetResult

}