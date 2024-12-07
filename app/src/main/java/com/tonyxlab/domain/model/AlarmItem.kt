package com.tonyxlab.domain.model

import android.net.Uri
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


data class AlarmItem(
    val id: String,
    val name: String,
    val isEnabled: Boolean,
    val triggerTime: LocalDateTime,
    val durationToNextTrigger: Long,
    val daysActive: List<DayChipState> = emptyList(),
    val ringtone: Ringtone = SILENT_RINGTONE,
    val volume: Float = 0f,
    val isHapticsOn: Boolean = false,
    val wakeUpTime: String? = null
)


@Serializable
data class DayChipState(val day: String, val isEnabled: Boolean)

@Serializable
data class Ringtone(val ringtoneName: String, @Contextual val ringtoneUri: Uri)

val SILENT_RINGTONE = Ringtone(ringtoneName = "Silent", ringtoneUri = Uri.EMPTY)

