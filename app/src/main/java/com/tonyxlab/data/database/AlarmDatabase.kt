package com.tonyxlab.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tonyxlab.data.database.dao.AlarmDao
import com.tonyxlab.data.database.entity.AlarmEntity


@Database(entities = [AlarmEntity::class], version = 1, exportSchema = false)
abstract class AlarmDatabase: RoomDatabase(){

    abstract fun getAlarmDao():AlarmDao

}