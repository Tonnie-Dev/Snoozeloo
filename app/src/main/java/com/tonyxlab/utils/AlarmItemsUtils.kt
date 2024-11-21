package com.tonyxlab.utils

import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.model.DayChipState
import kotlinx.datetime.LocalDateTime
import java.util.UUID

fun getRandomAlarmItem(): AlarmItem = AlarmItem(
        id = UUID.randomUUID()
                .toString(),
        isEnabled = false,
        name = "Wake Up",
        triggerTime = LocalDateTime(
                year = 2023,
                monthNumber = 7,
                dayOfMonth = 13,
                hour = 11,
                minute = 13,
                second = 57,
                nanosecond = 100
        ),
        durationToNextTrigger = 0L,
        daysActive = populateDaysActiveList(),
)


fun getRandomAlarmItems(count: Int = 10): List<AlarmItem> =
    buildList {

        repeat(count) {

            add(getRandomAlarmItem())
        }
    }

fun populateDaysActiveList(): List<DayChipState> {

    val daysOfWeek = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
    return buildList {

        (0..6).forEach { i ->

            add(DayChipState(day = daysOfWeek[i], isEnabled = i % 2 == 0))
        }
    }
}
