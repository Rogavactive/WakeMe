package ge.rogavactive.data.sunrisesunset

import ge.rogavactive.domain.sunrisesunset.model.SunriseSunsetData
import ge.rogavactive.domain.sunrisesunset.repository.SunriseSunsetRepository
import javax.inject.Inject

class SunriseSunsetRepositoryImpl @Inject constructor(
    private val sunriseSunsetApi: SunriseSunsetApi
): SunriseSunsetRepository {

    override suspend fun getSunriseSunsetData(lat: Float, lng: Float): SunriseSunsetData {
        val response = sunriseSunsetApi.getSunsetSunriseData(lat = lat, lng = lng)
        return response.results
    }

}