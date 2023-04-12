package ge.rogavactive.domain.sunrisesunset

import ge.rogavactive.domain.sunrisesunset.model.SunriseSunsetData
import ge.rogavactive.domain.sunrisesunset.repository.SunriseSunsetRepository
import javax.inject.Inject

class GetTodaySunriseSunsetUseCase @Inject constructor(
    private val repository: SunriseSunsetRepository
) {

    suspend operator fun invoke(lat: Float, lng: Float): SunriseSunsetData {
        return repository.getSunriseSunsetData(lat = lat, lng = lng)
    }

}