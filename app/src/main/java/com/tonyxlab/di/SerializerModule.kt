package com.tonyxlab.di

import com.tonyxlab.domain.model.DayChipState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SerializerModule {

    @Provides
    @Singleton
    fun provideListSerializer(): KSerializer<List<DayChipState>> {

        return ListSerializer(DayChipState.serializer())
    }
}