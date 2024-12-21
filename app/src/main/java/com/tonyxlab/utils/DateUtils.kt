package com.tonyxlab.utils


import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.time.Duration


fun LocalDateTime.fromLocalDateTimeToMillis(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Long {

    return this.toInstant(timeZone)
            .toEpochMilliseconds()
}


fun LocalDateTime.fromLocalToUtcTimeStamp(): Long {

    return this.toInstant(timeZone = TimeZone.currentSystemDefault())
            .toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
            .toInstant(TimeZone.UTC)
            .toEpochMilliseconds()

}

fun Long.fromMillisToLocalDateTime(

): LocalDateTime {
    return Instant.fromEpochMilliseconds(this)
            .toLocalDateTime(timeZone = TimeZone.UTC)
            .toInstant(timeZone = TimeZone.currentSystemDefault())
            .toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
}


fun LocalDateTime.Companion.now(): LocalDateTime {
    val instant = Clock.System.now()
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}

fun LocalDateTime.toAmPmTime(): String {
    val pattern = "hh:mm aa"
    val date = Date(this.fromLocalDateTimeToMillis())
    return SimpleDateFormat(pattern, Locale.getDefault()).format(date).uppercase()
}

fun Long.alarmIn(): String {

    val duration = durationToNextAlarm(this)

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

fun LocalDateTime.getHourString(): String {

    return when (val hourInt = this.hour) {

        in 0..9 -> "0$hourInt"


        else -> "$hourInt"
    }


}



fun String.displayTime():String {

    return runCatching {

       when(val hourInt = toIntOrNull()){

           in 1..9 -> "0$hourInt"
           in 10..12 ->"hourInt"
           in 13 .. 23 -> "0${hourInt?.minus(12)}"

           else -> "12"


       }
    }.getOrNull() ?: ""

}
fun LocalDateTime.getMinuteString(): String {

    val minuteInt = this.minute
    return if (minuteInt in 0..9) "0$minuteInt" else "$minuteInt"
}

fun setTriggerTime(hour: Int, minute: Int): LocalDateTime {

    val now = LocalDateTime.now()
    return LocalDateTime(
            year = now.year,
            month = now.month,
            dayOfMonth = now.dayOfMonth,
            hour = hour,
            minute = minute
    )

}