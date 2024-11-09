package com.tonyxlab.domain.model

import android.net.Uri

data class AlarmItem(
    val id: String,
    val name: String,
    val isActive: Boolean,
    val triggerTime: String,
    val durationToTrigger: String,
    val daysActive: List<DayActive> = emptyList(),
    val ringtone: Uri? = null,
    val volume: Int = 0,
    val vibrate: Boolean = false,
    val wakeUpTime: String? = null
)

enum class DayActive {
    ALARM_ON, ALARM_OFF
}