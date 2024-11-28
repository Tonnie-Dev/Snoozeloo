package com.tonyxlab.domain.model

import android.net.Uri
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


data class AlarmItem(
    val id: String,
    val name: String,
    val isEnabled: Boolean,
    val triggerTime: LocalDateTime,
    val durationToNextTrigger: Long,
    val daysActive: List<DayChipState> = emptyList(),
    val ringtone: Uri? = null,
    val volume: Int = 0,
    val isHapticsOn: Boolean = false,
    val wakeUpTime: String? = null
)


@Serializable
data class DayChipState(val day:String, val isEnabled: Boolean)

data class Ringtone (val ringtoneName: String, val ringtoneUri:Uri)

