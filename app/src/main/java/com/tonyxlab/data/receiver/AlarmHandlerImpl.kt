package com.tonyxlab.data.receiver

import android.content.Intent
import com.tonyxlab.domain.repository.AlarmRepository
import com.tonyxlab.domain.scheduler.AlarmHandler
import javax.inject.Inject


class AlarmHandlerImpl @Inject constructor(private val repository: AlarmRepository): AlarmHandler {
    override fun onAlarmTriggered(intent:Intent?) {
        TODO("Not yet implemented")
    }
}