package com.tonyxlab.presentation.settings

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.model.DayActivityState
import com.tonyxlab.domain.model.Ringtone
import com.tonyxlab.domain.model.SILENT_RINGTONE
import com.tonyxlab.domain.ringtone.RingtoneFetcher
import com.tonyxlab.domain.usecases.CreateAlarmUseCase
import com.tonyxlab.domain.usecases.GetAlarmByIdUseCase
import com.tonyxlab.domain.usecases.GetFutureDateUseCase
import com.tonyxlab.domain.usecases.GetSecsToNextAlarmUseCase
import com.tonyxlab.domain.usecases.UpdateActiveDaysUseCase
import com.tonyxlab.domain.usecases.UpdateAlarmUseCase
import com.tonyxlab.domain.usecases.ValidateAlarmUseCase
import com.tonyxlab.presentation.navigation.NestedScreens
import com.tonyxlab.utils.Resource
import com.tonyxlab.utils.TextFieldValue
import com.tonyxlab.utils.getHourString
import com.tonyxlab.utils.getMinuteString
import com.tonyxlab.utils.setTriggerTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val createAlarmUseCase: CreateAlarmUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val ringtoneFetcher: RingtoneFetcher,
    private val getSecsToNextAlarmUseCase: GetSecsToNextAlarmUseCase,
    private val getFutureDateUseCase: GetFutureDateUseCase,
    private val updateActiveDaysUseCase: UpdateActiveDaysUseCase,
    savedStateHandle: SavedStateHandle,
    validateAlarmUseCase: ValidateAlarmUseCase
) : ViewModel() {

    private var alarmItem: AlarmItem? = null

    var settingsUiState by mutableStateOf(SettingsUiState())
        private set

    private val hoursFlow = snapshotFlow { settingsUiState.hour }
    private val minutesFlow = snapshotFlow { settingsUiState.minute }

    private val _ringtones = MutableStateFlow<List<Ringtone>>(emptyList())
    val ringtones = _ringtones.asStateFlow()

    init {

        val id = savedStateHandle.toRoute<NestedScreens>().id
        readAlarmInfo(id)

        val ringtonesFlow = ringtoneFetcher.fetchRingtone()

        combine(ringtonesFlow, hoursFlow, minutesFlow) {

            ringtonesList, hours, minutes ->

            val isValid = when (validateAlarmUseCase(hours, minutes)) {

                is Resource.Success -> true
                else -> false
            }

            _ringtones.value = ringtonesList
            settingsUiState = settingsUiState.copy(isSaveEnabled = isValid)

            if (isValid) {
                setSecsToNextAlarm(hours, minutes)
                settingsUiState = settingsUiState.copy(isError = false)

            } else {

                settingsUiState = settingsUiState.copy(isError = true)

            }

        }.launchIn(viewModelScope)
    }

    var hourFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = settingsUiState.hour,
                    onValueChange = this::setHourField,
                    range = 0..23
            )
    )
        private set

    var minuteFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = settingsUiState.minute,
                    onValueChange = this::setMinuteField,
                    range = 0..59
            )

    )
        private set

    var nameFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = settingsUiState.alarmName,
                    onValueChange = this::setAlarmName,

                    )
    )
        private set

    var volumeFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = settingsUiState.volume,
                    onValueChange = this::setVolume,

                    )
    )
        private set


    var hapticsFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = settingsUiState.isHapticsOn,
                    onValueChange = this::setHaptics,

                    )
    )
        private set

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _selectedRingtone = MutableStateFlow(SILENT_RINGTONE)
    val selectedRingtone = _selectedRingtone.asStateFlow()

    private fun readAlarmInfo(alarmId: String?) {

        alarmId ?: return

        viewModelScope.launch {

            when (val result = getAlarmByIdUseCase(alarmId = alarmId)) {
                is Resource.Success -> {
                    alarmItem = result.data

                    with(result.data) {
                        settingsUiState = settingsUiState.copy(
                                hour = triggerTime.getHourString(),
                                minute = triggerTime.getMinuteString(),
                                alarmName = name,
                                activeDays = daysActive,
                                ringtone = ringtone,
                                volume = volume,
                                isHapticsOn = isHapticsOn
                        )
                        setHourField(triggerTime.getHourString())
                        setMinuteField(triggerTime.getMinuteString())
                        setAlarmName(name)
                        setRingtone(ringtone)
                        setVolume(volume)
                        setHaptics(isHapticsOn)

                        Timber.i("Settings VW, activeDays: $daysActive")

                    }

                }

                is Resource.Error -> Unit
            }
        }

    }

    fun play(uri: Uri) {

        ringtoneFetcher.startPlay(uri)
        _isPlaying.value = true
    }


    fun stop() {

        ringtoneFetcher.stopPlay()
        _isPlaying.value = false
    }

    private fun setHourField(value: String) {

        if (value.length <= 2) {
            hourFieldValue.update {

                settingsUiState =
                    settingsUiState.copy(
                            hour = value,
                            isShowAlarmIn = false,
                            //isSaveEnabled = true
                    )
                it.copy(value = value, isError = isFieldError(value, hourFieldValue.value))

            }
        }

    }


    private fun setMinuteField(value: String) {

        if (value.length <= 2) {
            minuteFieldValue.update {

                settingsUiState = settingsUiState.copy(
                        minute = value,
                        isShowAlarmIn = false,

                        )
                it.copy(value = value, isError = isFieldError(value, minuteFieldValue.value))
            }
        }


    }

    private fun isFieldError(newInput: String, field: TextFieldValue<String>): Boolean {

        return newInput.toIntOrNull()
                ?.let { int -> int !in field.range } ?: true

    }

    private suspend fun setSecsToNextAlarm(hour: String, minute: String) {

        val futureDate = getFutureDateUseCase(
                alarmTriggerTime = setTriggerTime(hour, minute),
                list = settingsUiState.activeDays
        )
        settingsUiState =
            settingsUiState.copy(durationToNextTrigger = getSecsToNextAlarmUseCase(futureDate).first())

    }

    private fun setAlarmName(value: String) {

        nameFieldValue.update {
            it.copy(
                    value = value,
                    isConfirmButtonEnabled = settingsUiState.alarmName.isNotEmpty()
            )
        }

        setIsSaveEnabled(isSaveEnabled = true, showAlarmIn = false)

    }


    fun setActiveDays(index: Int) {
        val defaultList = List(7) { DayActivityState(day = it, isEnabled = false) }
        settingsUiState.activeDays.ifEmpty {

            settingsUiState = settingsUiState.copy(activeDays = defaultList)

        }

        val daysActive =
            updateActiveDaysUseCase(activeDays = settingsUiState.activeDays, clickedIndex = index)

        settingsUiState = settingsUiState.copy(activeDays = daysActive)

        setIsSaveEnabled(isSaveEnabled = true, showAlarmIn = false)
    }

    private fun setVolume(value: Float) {

        volumeFieldValue.update { it.copy(value = value) }

        setIsSaveEnabled(isSaveEnabled = true, showAlarmIn = false)

    }

    private fun setHaptics(value: Boolean) {

        hapticsFieldValue.update { it.copy(value = value) }

        setIsSaveEnabled(isSaveEnabled = true, showAlarmIn = false)

    }

    fun setRingtone(value: Ringtone) {

        _selectedRingtone.value = value

        setIsSaveEnabled(isSaveEnabled = true, showAlarmIn = false)
    }


    private fun setIsSaveEnabled(isSaveEnabled: Boolean, showAlarmIn: Boolean) {
        val isError = settingsUiState.isError
        if (isError.not()) {
            settingsUiState =
                settingsUiState.copy(isSaveEnabled = isSaveEnabled, isShowAlarmIn = showAlarmIn)

        }
    }

    fun onNavigateBackToSettingsScreen() {

        ringtoneFetcher.stopPlay()
        ringtoneFetcher.release()
    }

    fun onClearAlarmNameText() {

        nameFieldValue.update {

            it.copy(value = "")
        }

    }

    private fun resetState() {

        settingsUiState = SettingsUiState()

    }

    override fun onCleared() {
        super.onCleared()
        ringtoneFetcher.release()
        resetState()
    }


    fun onSaveButtonClick() {

        val isSave = settingsUiState.isSaveEnabled
        if (isSave) {
            setIsSaveEnabled(isSaveEnabled = false, showAlarmIn = true)
            doSave()

        }
    }

    private fun doSave() {


        val alarmItem = AlarmItem(
                id = this.alarmItem?.id ?: UUID.randomUUID()
                        .toString(),
                name = nameFieldValue.value.value,
                isEnabled = hapticsFieldValue.value.value,
                triggerTime = setTriggerTime(
                        hour = hourFieldValue.value.value,
                        minute = minuteFieldValue.value.value
                ),

                daysActive = settingsUiState.activeDays,
                ringtone = _selectedRingtone.value,
                volume = volumeFieldValue.value.value,
                isHapticsOn = hapticsFieldValue.value.value,
                wakeUpTime = null
        )

        viewModelScope.launch {

            val result = if (this@SettingsViewModel.alarmItem != null) {
                updateAlarmUseCase(alarmItem = alarmItem)
            } else {
                createAlarmUseCase(alarmItem = alarmItem)
            }

            when (result) {

                is Resource.Error -> {

                }

                is Resource.Success -> {


                }
            }

        }

    }

}



