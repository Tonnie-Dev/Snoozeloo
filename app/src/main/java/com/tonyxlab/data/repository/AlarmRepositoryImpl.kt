package com.tonyxlab.data.repository

import com.tonyxlab.data.database.dao.AlarmDao
import com.tonyxlab.data.mappers.toDomainModel
import com.tonyxlab.data.mappers.toEntityModel
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.repository.AlarmRepository
import com.tonyxlab.domain.scheduler.AlarmScheduler
import com.tonyxlab.utils.AppCoroutineDispatchers
import com.tonyxlab.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val dao: AlarmDao,
    private val scheduler: AlarmScheduler,
    private val dispatchers: AppCoroutineDispatchers
) : AlarmRepository {
    override fun getAlarms(): Flow<List<AlarmItem>> {

        return dao.getAlarms()
                .map { alarms ->
                    return@map alarms?.map { it.toDomainModel() } ?: emptyList()
                }
    }

    override suspend fun getAlarmById(alarmId: String): Resource<AlarmItem> =

        withContext(dispatchers.io) {

            return@withContext try {
                dao.getAlarmById(alarmId = alarmId)
                        ?.let {

                            Resource.Success(data = it.toDomainModel())
                        } ?: Resource.Error(KotlinNullPointerException())
            } catch (e: Exception) {

                Resource.Error(e)
            }

            /* return@withContext try {
              val entity = dao.getAlarmById(alarmId = alarmId)
              if (entity != null) {

                  Resource.Success(entity.toDomainModel())
              } else {
                  Resource.Error(KotlinNullPointerException())
              }
          } catch (e: Exception) {
              Resource.Error(e)

          }*/
        }


    override suspend fun createAlarm(alarmItem: AlarmItem): Resource<Boolean> =
        withContext(dispatchers.io) {
            return@withContext try {
                scheduler.schedule(alarmItem)
                val result = dao.insert(alarmItem.toEntityModel())
                Resource.Success(result != -1L)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }

    override suspend fun updateAlarm(alarmItem: AlarmItem): Resource<Boolean> =
        withContext(dispatchers.io) {

            return@withContext try {
                scheduler.cancel(alarmItem)
                // TODO: Figure the Update Sequence
                dao.update(alarmItem.toEntityModel())
                Resource.Success(true)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }


    override suspend fun deleteAlarm(alarmItem: AlarmItem): Resource<Boolean> =
        with(dispatchers.io) {

            return@with try {
                val result = dao.delete(alarmItem.toEntityModel())
                Resource.Success(result != -1)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }
}