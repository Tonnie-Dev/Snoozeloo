package com.tonyxlab.domain.repository


import android.net.Uri
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    val ringtoneFlow: Flow<Uri?>

    fun getAlarms(): Flow<List<AlarmItem>>
    suspend fun getAlarmById(alarmId:String): Resource<AlarmItem>
    suspend fun createAlarm(alarmItem: AlarmItem): Resource<Boolean>
    suspend fun updateAlarm(alarmItem: AlarmItem): Resource<Boolean>
    suspend fun deleteAlarm(alarmItem: AlarmItem): Resource<Boolean>
    suspend fun saveRingtone(uri: Uri)


}