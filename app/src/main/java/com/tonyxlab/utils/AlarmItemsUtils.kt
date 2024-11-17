package com.tonyxlab.utils

import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.model.DayChipState
import kotlinx.datetime.LocalDateTime
import java.util.UUID

fun getRandomAlarmItem(): AlarmItem = AlarmItem(
        id = UUID.randomUUID()
                .toString(),
        isEnabled = false,
        name = "Alarm",
        triggerTime = LocalDateTime.now(),
        durationToNextTrigger = 0L,
        daysActive = populateDaysActiveList(),
)

fun populateDaysActiveList(): List<DayChipState> {

    val daysOfWeek = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
    return buildList {

        (0..6).forEach { i ->

            add(DayChipState(day = daysOfWeek[i], isEnabled = i % 2 == 0))
        }
    }
}
