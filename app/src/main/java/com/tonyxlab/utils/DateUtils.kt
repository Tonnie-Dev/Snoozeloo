package com.tonyxlab.utils


import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime


fun LocalDateTime.fromLocalDateTimeToUtcMillis():Long {

    return this.toInstant(TimeZone.UTC).toEpochMilliseconds()
}
fun Long.fromUtcMillisToLocalDateTimeDefault(): LocalDateTime {
    return Instant.fromEpochMilliseconds(this)
            .toLocalDateTime(TimeZone.currentSystemDefault())

}