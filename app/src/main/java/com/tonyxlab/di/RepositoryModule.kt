package com.tonyxlab.di

import com.tonyxlab.data.database.converters.JsonSerializerImpl
import com.tonyxlab.data.receiver.AlarmHandlerImpl
import com.tonyxlab.data.repository.AlarmRepositoryImpl
import com.tonyxlab.data.repository.RingtoneFetcherImpl
import com.tonyxlab.data.schedulerimpl.AlarmSchedulerImpl
import com.tonyxlab.domain.json.JsonSerializer
import com.tonyxlab.domain.model.DayChipState
import com.tonyxlab.domain.repository.AlarmRepository
import com.tonyxlab.domain.ringtone.RingtoneFetcher
import com.tonyxlab.domain.scheduler.AlarmHandler
import com.tonyxlab.domain.scheduler.AlarmScheduler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAlarmRepository(
        alarmRepositoryImpl: AlarmRepositoryImpl
    ): AlarmRepository


    @Binds
    abstract fun provideJsonSerializer(
        jsonSerializerImpl: JsonSerializerImpl<DayChipState>
    ): JsonSerializer

    @Binds
    abstract fun provideAlarmHandler(
        alarmHandlerImpl: AlarmHandlerImpl
    ): AlarmHandler


    @Binds
    abstract fun provideAlarmScheduler(
        alarmHandlerImpl: AlarmSchedulerImpl
    ): AlarmScheduler


    @Binds
    abstract fun bindRingtoneFetcher(ringtoneFetcherImpl: RingtoneFetcherImpl): RingtoneFetcher



}