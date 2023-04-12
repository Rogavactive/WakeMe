package ge.rogavactive.wakeme.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.rogavactive.common.serialization.LocalTimeJsonDeserializer
import ge.rogavactive.data.sunrisesunset.SunriseSunsetApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    companion object {
        const val SUNSET_SUNRISE_BASE_URL = "https://api.sunrise-sunset.org/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .build()

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .registerTypeAdapter(LocalTime::class.java, LocalTimeJsonDeserializer)
            .create()

    @Provides
    @Singleton
    fun provideSunsetSunriseApi(okHttpClient: OkHttpClient, gson: Gson): SunriseSunsetApi =
        Retrofit.Builder()
            .baseUrl(SUNSET_SUNRISE_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(SunriseSunsetApi::class.java)

}