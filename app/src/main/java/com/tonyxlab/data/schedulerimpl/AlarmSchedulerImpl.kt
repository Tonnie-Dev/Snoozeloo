package com.tonyxlab.data.schedulerimpl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.tonyxlab.data.receiver.AlarmReceiver
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.scheduler.AlarmScheduler
import com.tonyxlab.utils.Constants.ALARM_ID
import com.tonyxlab.utils.toMillis
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(private val context: Context) : AlarmScheduler {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun schedule(alarmItem: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java)
                .apply {
                    putExtra(ALARM_ID, alarmItem.id)
                }

        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmItem.triggerTime.toMillis(),
                PendingIntent.getBroadcast(
                        context,
                        alarmItem.hashCode(),
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
        )
    }

    override fun cancel(alarmItem: AlarmItem) {

        alarmManager.cancel(
                PendingIntent.getBroadcast(
                        context,
                        alarmItem.hashCode(),
                        Intent(context, AlarmReceiver::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
        )
    }


}