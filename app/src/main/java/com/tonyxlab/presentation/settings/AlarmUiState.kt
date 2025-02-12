package com.tonyxlab.presentation.settings

import com.tonyxlab.domain.model.DayActivityState
import com.tonyxlab.domain.model.Ringtone
import com.tonyxlab.domain.model.SILENT_RINGTONE

data class SettingsUiState(
    val hour: String = "",
    val minute: String = "",
    val alarmName: String = "",
    val durationToNextTrigger: Long = 0L,
    val activeDays:List<DayActivityState> = emptyList(),
    val ringtone: Ringtone = SILENT_RINGTONE,
    val volume: Float = 0f,
    val isHapticsOn: Boolean = false,
    val isSaveEnabled: Boolean = false,
    val isDialogSaveButtonEnabled: Boolean = false,
    val isShowAlarmIn: Boolean = false,
    val isError: Boolean = false
)


