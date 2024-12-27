package com.tonyxlab.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.tonyxlab.data.database.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao : BaseDao<AlarmEntity> {

    @Query("SELECT * FROM alarms_table")
    fun getAlarms(): Flow<List<AlarmEntity>>

    @Query("SELECT * FROM alarms_table WHERE id = :alarmId")
    suspend fun getAlarmById(alarmId: String): AlarmEntity?

}