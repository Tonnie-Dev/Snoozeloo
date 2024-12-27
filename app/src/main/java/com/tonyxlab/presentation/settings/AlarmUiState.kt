package com.tonyxlab.presentation.settings

import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.model.DayChipState
import com.tonyxlab.domain.model.Ringtone
import com.tonyxlab.domain.model.SILENT_RINGTONE
import com.tonyxlab.utils.now
import kotlinx.datetime.LocalDateTime

data class AlarmUiState(
    val id: String = "",
    val name: String = "Work",
    val isEnabled: Boolean = false,
    val triggerTime: LocalDateTime = LocalDateTime.now(),
    val durationToNextTrigger: Long = 0L,
    val daysActive: List<DayChipState> =
        List(7) { i ->
            DayChipState(day = i, isEnabled = i <= 4)
        },
    val ringtone: Ringtone = SILENT_RINGTONE,
    val volume: Float = 0f,
    val isHapticsOn: Boolean = false,
    val wakeUpTime: String? = null,
    val isSaveEnabled: Boolean = false,
    val isDialogSaveButtonEnabled: Boolean = false,
    val showAlarmIn: Boolean = false
)



data class SettingsUiState(
    val hour: String = "00",
    val minute: String = "00",
    val alarmName: String = "",
    val durationToNextTrigger: Long = 0L,
    val daysActive: List<DayChipState> = emptyList(),
    val ringtone: Ringtone = SILENT_RINGTONE,
    val volume: Float = 0f,
    val isHapticsOn: Boolean = false,
    val isSaveEnabled: Boolean = false,
    val isDialogSaveButtonEnabled: Boolean = false,
    val isShowAlarmIn: Boolean = false,
    val isError: Boolean = false
)


