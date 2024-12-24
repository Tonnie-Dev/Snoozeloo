package com.tonyxlab.presentation.home

import com.tonyxlab.domain.model.AlarmItem

data class AlarmState(
    val alarmItem: AlarmItem,
    val remainingSecs: Long,
    val sleepTime: Long
)
