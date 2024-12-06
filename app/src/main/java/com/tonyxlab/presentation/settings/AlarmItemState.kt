package com.tonyxlab.presentation.settings

import com.tonyxlab.domain.model.DayChipState
import com.tonyxlab.domain.model.Ringtone
import com.tonyxlab.utils.now
import kotlinx.datetime.LocalDateTime

data class AlarmItemState(
    val id: String = "",
    val name: String = "",
    val isEnabled: Boolean = false,
    val triggerTime: LocalDateTime = LocalDateTime.now(),
    val durationToNextTrigger: Long = 0L,
    val daysActive: List<DayChipState> = listOf(),
    val ringtone: Ringtone? = null,
    val volume: Float = 0f,
    val isHapticsOn: Boolean = false,
    val wakeUpTime: String? = null
)
