package com.tonyxlab.domain.scheduler

import android.content.Intent

interface AlarmHandler {

    fun onAlarmTriggered(intent: Intent?)
}