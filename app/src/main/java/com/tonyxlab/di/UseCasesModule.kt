package com.tonyxlab.di

import com.tonyxlab.domain.usecases.GetSecsToNextAlarmUseCase
import com.tonyxlab.domain.usecases.UpdateActiveDaysUseCase
import com.tonyxlab.domain.usecases.ValidateAlarmUseCase
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

    @Provides
    fun provideValidateAlarmUseCase():ValidateAlarmUseCase{

        return ValidateAlarmUseCase()
    }


    @Provides
    fun provideUpdateActiveDaysUseCase():UpdateActiveDaysUseCase{

        return UpdateActiveDaysUseCase()
    }
}
