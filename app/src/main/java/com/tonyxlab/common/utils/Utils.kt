package com.tonyxlab.common.utils

import com.tonyxlab.domain.model.AlarmItem
import java.time.ZoneId

fun AlarmItem.toEpochMillis():Long {

    return time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
}