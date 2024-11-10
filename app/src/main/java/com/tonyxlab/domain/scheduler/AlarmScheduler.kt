package com.tonyxlab.domain.scheduler

import com.tonyxlab.domain.model.AlarmItem

interface AlarmScheduler {

    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}