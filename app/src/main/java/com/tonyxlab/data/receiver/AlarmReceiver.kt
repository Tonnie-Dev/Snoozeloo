package com.tonyxlab.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tonyxlab.domain.scheduler.AlarmHandler
import javax.inject.Inject

class AlarmReceiver  : BroadcastReceiver() {
@Inject
 lateinit var handler:AlarmHandler

    override fun onReceive(context: Context?, intent: Intent?) {

        if (::handler.isInitialized){

            handler.onAlarmTriggered(intent)
        }

    }
}