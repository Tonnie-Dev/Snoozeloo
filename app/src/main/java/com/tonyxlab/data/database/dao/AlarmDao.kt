package com.tonyxlab.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tonyxlab.data.database.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao : BaseDao<AlarmEntity> {

    @Query("SELECT * FROM alarms_table ORDER BY duration_to_next_trigger ASC")
    fun getAlarms(): Flow<List<AlarmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createAlarm(entity: AlarmEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAlarm(entity: AlarmEntity)

}