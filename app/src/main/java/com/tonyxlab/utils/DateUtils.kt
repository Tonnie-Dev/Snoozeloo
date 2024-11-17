package com.tonyxlab.utils


import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.time.Duration


fun LocalDateTime.fromLocalDateTimeToMillis(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Long {

    return this.toInstant(timeZone)
            .toEpochMilliseconds()
}

fun Long.fromMillisToLocalDateTime(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    return Instant.fromEpochMilliseconds(this)
            .toLocalDateTime(timeZone)
}

fun LocalDateTime.Companion.now(): LocalDateTime {
    val instant = Clock.System.now()
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}

fun LocalDateTime.toAmPmTime(): String {
    val pattern = "HH:mm a"
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}

fun Long.alarmIn(): String {


    val duration = durationToNextAlarm(this)


    val dayMillis = 24 * 60 * 60 * 1000
    val hourMillis = 60 * 60 * 1000
    val minMillis = 60 * 1000

    val totalNoOfMinutes = duration.inWholeMinutes

    val daysToGo = duration.inWholeDays
    val hoursToGo = (totalNoOfMinutes % 1440) / 60
    val minutesToGo = totalNoOfMinutes % 60

    return when {
        daysToGo > 0 -> "$daysToGo days, $hoursToGo h, $minutesToGo min"
        hoursToGo > 0 -> "$hoursToGo h, $minutesToGo min"
        else -> "$minutesToGo min"
    }

}


fun durationToNextAlarm(nextAlarmTime: Long): Duration {

    val nowInMillis = LocalDateTime.now()
            .fromLocalDateTimeToMillis()

    val nextTriggerTime = when {
        nextAlarmTime < nowInMillis -> nextAlarmTime.addOneDayToMilliTime()
        else -> nextAlarmTime.minus(nowInMillis)

    }


    val nowAsInstant = Clock.System.now()
    val triggerTimeInstant = Instant.fromEpochMilliseconds(nextTriggerTime)

    return triggerTimeInstant - nowAsInstant
}

fun LocalDateTime.addOneDayToLocalDateTime(): Long {

    val dayInMillis = 24 * 60 * 60 * 1000
    return this.fromLocalDateTimeToMillis()
            .plus(dayInMillis)
}

fun Long.addOneDayToMilliTime(): Long {


    val dayInMillis = 24 * 60 * 60 * 1000
    return this.plus(dayInMillis)
}
