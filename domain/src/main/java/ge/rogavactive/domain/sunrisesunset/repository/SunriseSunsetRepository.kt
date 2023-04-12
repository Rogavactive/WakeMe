package ge.rogavactive.domain.sunrisesunset.repository

import ge.rogavactive.domain.sunrisesunset.model.SunriseSunsetData

interface SunriseSunsetRepository {

    suspend fun getSunriseSunsetData(lat: Float, lng: Float) : SunriseSunsetData

}