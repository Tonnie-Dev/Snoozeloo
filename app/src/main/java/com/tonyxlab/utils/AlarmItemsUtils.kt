package com.tonyxlab.utils

import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.model.DayActivityState
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
        daysActive =List(7){i ->
            DayActivityState(day = i, isEnabled = i>=4)
        },
)


fun getDefaultDayAlarmActivityList(): List<DayActivityState> =
    buildList {

        repeat(7) {

            add(DayActivityState(day = it, isEnabled = false))
        }
    }

