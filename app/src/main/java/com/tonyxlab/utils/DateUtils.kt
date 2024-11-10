package com.tonyxlab.utils

import java.time.LocalDateTime
import java.time.ZoneId

fun LocalDateTime.toMillis():Long {

    return atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
}