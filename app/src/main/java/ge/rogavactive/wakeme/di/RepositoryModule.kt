package ge.rogavactive.wakeme.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.rogavactive.data.alarm.AlarmRepositoryImpl
import ge.rogavactive.domain.alarm.repository.AlarmRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindAlarmRepository(impl: AlarmRepositoryImpl): AlarmRepository

}