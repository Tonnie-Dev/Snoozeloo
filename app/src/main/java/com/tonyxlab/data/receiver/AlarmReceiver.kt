package com.tonyxlab.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tonyxlab.domain.scheduler.AlarmHandler
import javax.inject.Inject

class AlarmReceiver @Inject constructor(private val handler: AlarmHandler) : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        handler.onAlarmTriggered(intent)
    }
}