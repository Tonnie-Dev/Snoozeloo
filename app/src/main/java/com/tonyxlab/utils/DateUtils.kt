package com.tonyxlab.utils


import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime


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