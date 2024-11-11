package com.tonyxlab.di

import com.tonyxlab.utils.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Singleton

    fun provideDispatchers(): AppCoroutineDispatchers {

        return AppCoroutineDispatchers(
                main = Dispatchers.Main,
                io = Dispatchers.IO,
                computation = Dispatchers.Default
        )

    }
}
