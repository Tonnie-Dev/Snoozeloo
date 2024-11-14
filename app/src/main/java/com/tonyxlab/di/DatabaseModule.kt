package com.tonyxlab.di

import android.content.Context
import androidx.room.Room
import com.tonyxlab.data.database.AlarmDatabase
import com.tonyxlab.data.database.converters.Converters
import com.tonyxlab.data.database.dao.AlarmDao
import com.tonyxlab.domain.json.JsonSerializer
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
    fun provideDatabase(
        @ApplicationContext context: Context, serializer: JsonSerializer
    ): AlarmDatabase {

        return Room.databaseBuilder(
                context = context,
                klass = AlarmDatabase::class.java,
                name = DATABASE_NAME
        )
                .addTypeConverter(Converters(serializer = serializer))
                .fallbackToDestructiveMigration(false)
                .build()
    }

    @Provides
    @Singleton
    fun provideAlarmDao(database: AlarmDatabase): AlarmDao {

        return database.getAlarmDao()
    }
}
