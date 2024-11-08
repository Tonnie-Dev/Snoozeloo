package com.tonyxlab.data.repository

import android.app.AlarmManager
import android.content.Context
import com.tonyxlab.common.utils.toEpochMillis
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.repository.AlarmScheduler

class AlarmSchedulerImpl(context:Context): AlarmScheduler {

   private val alarmManager = context.getSystemService(AlarmManager::class.java)
    override fun schedule(item: AlarmItem) {
        alarmManager.setAndAllowWhileIdle( AlarmManager.RTC_WAKEUP, item.toEpochMillis())
    }

    override fun cancel(item: AlarmItem) {
        TODO("Not yet implemented")
    }
}