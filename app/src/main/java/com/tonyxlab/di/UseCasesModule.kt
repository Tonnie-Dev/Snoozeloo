package com.tonyxlab.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.tonyxlab.domain.repository.AlarmRepository
import com.tonyxlab.domain.ringtone.RingtonePicker
import com.tonyxlab.domain.usecases.PickRingtoneUseCase
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Singleton
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun providePickRingToneUseCase(repository: AlarmRepository,  picker: RingtonePicker):PickRingtoneUseCase {

        return PickRingtoneUseCase(repository = repository, picker =picker )
    }

    //Write a provide function for SharedPreferences

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context:Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

}
