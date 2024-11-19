/*
package com.tonyxlab.data.repository

import app.cash.turbine.test
import com.tonyxlab.data.database.dao.AlarmDao
import com.tonyxlab.data.database.entity.AlarmEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.assertEquals

class AlarmRepositoryImplTest {


    private val mockDao = mockk<AlarmDao>()
    private lateinit var repositoryImpl: AlarmRepositoryImpl
    private lateinit var entity: AlarmEntity
    private lateinit var entities: List<AlarmEntity>

    @Before
    fun setUp() {

        repositoryImpl = AlarmRepositoryImpl(mockDao)
        entity = getAlarmEntity(1)
        entities = getAlarmEntities(10)

    }


    @Test
    fun `getAlarms should return a mapped list of AlarmItems`() = runTest {

        coEvery { mockDao.getAlarms() } returns flowOf(entities)

        repositoryImpl.getAlarms().test {

            val result = awaitItem()
            assertEquals(10, entities.size)
        }

    }


    private fun getAlarmEntity(entity: Int): AlarmEntity {

        return AlarmEntity(
                id = UUID.randomUUID()
                        .toString(),
                name = "Alarm ${entity + 1}",
                isEnabled = false,
                triggerTime = LocalDateTime.now(),
                durationToNextTrigger = 6342,
                daysActive = listOf(),
                ringtone = null,
                volume = 2108,
                isHapticsOn = false,
                wakeUpTime = null
        )
    }

    private fun getAlarmEntities(count: Int): List<AlarmEntity> {

        return buildList {

            repeat(count) {

                add(getAlarmEntity(it))
            }
        }
    }

}*/
