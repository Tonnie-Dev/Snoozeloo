package com.tonyxlab.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.tonyxlab.data.database.AlarmDatabase
import com.tonyxlab.data.database.dao.AlarmDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DATABASE_NAME = "alarm_database"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AlarmDatabase {

        return Room.databaseBuilder(
                context,
                AlarmDatabase::class.java,
                DATABASE_NAME
        )
                .build()
    }

    @Provides
    @Singleton
    fun provideAlarmDao(database: AlarmDatabase): AlarmDao {

        return database.getAlarmDao()
    }
}
