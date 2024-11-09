package com.tonyxlab.domain.model

import android.net.Uri
import java.time.LocalDateTime

data class AlarmItem(
    val id: String,
    val name: String,
    val isActive: Boolean,
    val triggerTime: LocalDateTime,
    val durationToNextTrigger: Long,
    val daysActive: List<DayActive> = emptyList(),
    val ringtone: Uri? = null,
    val volume: Int = 0,
    val isHapticOn: Boolean = false,
    val wakeUpTime: String? = null
)

enum class DayActive {
    ALARM_ON, ALARM_OFF
}