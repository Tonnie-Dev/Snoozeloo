package com.tonyxlab.domain.repository


import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAlarms(): Flow<List<AlarmItem>>
    suspend fun getAlarmById(alarmId:String): Resource<AlarmItem>
    suspend fun createAlarm(): Resource<Boolean>
    suspend fun updateAlarm(alarmItem: AlarmItem): Resource<Boolean>
    suspend fun deleteAlarm(alarmItem: AlarmItem): Resource<Boolean>
}