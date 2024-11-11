package com.tonyxlab.data.repository

import com.tonyxlab.data.database.dao.AlarmDao
import com.tonyxlab.data.mappers.toDomainModel
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.repository.AlarmRepository
import com.tonyxlab.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(private val dao: AlarmDao) : AlarmRepository {
    override fun getAlarms(): Flow<List<AlarmItem>> {

        return dao.getAlarms()
                .map { alarms ->
                    return@map alarms?.map { it.toDomainModel() } ?: emptyList()
                }
    }

    override suspend fun getAlarmById(alarmId:String): Resource<AlarmItem> {

        return
        return dao.getAlarmById(alarmId)
    }

    override suspend fun createAlarm(): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateAlarm(alarmItem: AlarmItem): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAlarm(alarmItem: AlarmItem): Resource<Boolean> {
        TODO("Not yet implemented")
    }
}