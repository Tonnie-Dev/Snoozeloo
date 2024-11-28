package com.tonyxlab.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity

import com.tonyxlab.data.ringtoneimpl.RingtonePickerImpl
import com.tonyxlab.domain.model.DayChipState
import com.tonyxlab.domain.repository.AlarmRepository
import com.tonyxlab.domain.ringtone.RingtonePicker
import com.tonyxlab.domain.usecases.PickRingtoneUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import javax.inject.Singleton

@Module
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

    @Provides
    fun provideDayChipStateSerializer(): KSerializer<DayChipState> {
        return Json.serializersModule.serializer()
    }



    @Provides

    fun provideAppContext(@ApplicationContext context: Context): Context {
        return context
    }



}
