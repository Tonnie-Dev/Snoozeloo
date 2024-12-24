package com.tonyxlab.di

import com.tonyxlab.domain.usecases.GetSecsToNextAlarmUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {


    @Provides
    fun provideGetSecsToNextAlarmUseCase():GetSecsToNextAlarmUseCase {

        return GetSecsToNextAlarmUseCase()
    }

}
