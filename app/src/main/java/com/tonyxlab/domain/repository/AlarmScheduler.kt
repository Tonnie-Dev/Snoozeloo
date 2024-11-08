package com.tonyxlab.domain.repository

import com.tonyxlab.domain.model.AlarmItem

interface AlarmScheduler {
    fun schedule(item:AlarmItem)
    fun cancel(item: AlarmItem)
}