package com.tonyxlab.di

import com.tonyxlab.domain.model.DayChipState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.KSerializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SerializerModule {

    @Provides
    @Singleton
    fun provideDayChipStateSerializer(): KSerializer<DayChipState> {
        return DayChipState.serializer()
    }


    /* @Provides
     @Singleton
     fun provideDayChipStateListSerializer(): KSerializer<List<DayChipState>> {

         return ListSerializer(DayChipState.serializer())
     }*/


    /*@Provides
    @Singleton
    fun providerRingtoneSerializer():KSerializer<Ringtone> {

        return Ringtone.serializer()
    }*/

}