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
import com.tonyxlab.domain.model.Ringtone
import com.tonyxlab.domain.model.SILENT_RINGTONE
import com.tonyxlab.domain.ringtone.RingtoneFetcher
import com.tonyxlab.domain.usecases.CreateAlarmUseCase
import com.tonyxlab.domain.usecases.GetAlarmByIdUseCase
import com.tonyxlab.domain.usecases.GetSecsToNextAlarmUseCase
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
    getSecsToNextAlarmUseCase: GetSecsToNextAlarmUseCase,
    savedStateHandle: SavedStateHandle,
    validateAlarmUseCase: ValidateAlarmUseCase
) : ViewModel() {


    private var alarmItem: AlarmItem? = null

    //private val _uiState = MutableStateFlow(AlarmUiState())
    //val uiState = _uiState.asStateFlow()
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

        val secsFlow = getSecsToNextAlarmUseCase(
                futureDate = setTriggerTime(
                        settingsUiState.hour.toInt(),
                        settingsUiState.minute.toInt()
                ),

                )
        combine(ringtonesFlow, hoursFlow, minutesFlow, secsFlow) {

            ringtonesList, hours, minutes, secs ->

            Timber.i("Combine Called")
            val isValid = when (validateAlarmUseCase(hours, minutes)) {


                is Resource.Success -> true

                else -> false
            }

            Timber.i("isValid is: $isValid")
            Timber.i("Hour is: $hours, minute is: $minutes")
            _ringtones.value = ringtonesList
            settingsUiState = settingsUiState.copy(isSaveEnabled = isValid)
            settingsUiState = settingsUiState.copy(durationToNextTrigger = secs)

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
                    isConfirmButtonEnabled = settingsUiState.isDialogSaveButtonEnabled
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
                                daysActive = daysActive,
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
                    }


                    /*_uiState.value = result.data.toAlarmUiState()

                    setHourField(_uiState.value.triggerTime.getHourString())
                    setMinuteField(_uiState.value.triggerTime.getMinuteString())
                    setAlarmName(_uiState.value.name)
                    setRingtone(_uiState.value.ringtone)
                    setVolume(_uiState.value.volume)
                    setHaptics(_uiState.value.isHapticsOn)*/
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

                settingsUiState = settingsUiState.copy(hour = value, isShowAlarmIn = false)
                it.copy(value = value, isError = isFieldError(value, hourFieldValue.value))

            }
        }

    }


    private fun setMinuteField(value: String) {

        if (value.length <= 2) {
            minuteFieldValue.update {

                settingsUiState = settingsUiState.copy(minute = value, isShowAlarmIn = false)
                it.copy(value = value, isError = isFieldError(value, minuteFieldValue.value))
            }
        }


    }

    private fun isFieldError(newInput: String, field: TextFieldValue<String>): Boolean {

        val isError = newInput.toIntOrNull()
                ?.let { int -> int !in field.range } ?: true



        if (isError) {

            settingsUiState = settingsUiState.copy(isSaveEnabled = false, isError = true)
        }

        Timber.i("isFieldError: $isError")

        return isError

    }

    private fun setAlarmName(value: String) {
        if (value.isNotBlank()) {

            nameFieldValue.update {
                it.copy(
                        value = value,
                        isConfirmButtonEnabled = value.isNotBlank()
                )
            }

        }
        settingsUiState = settingsUiState.copy(isSaveEnabled = true, isShowAlarmIn = false)
    }

    private fun setVolume(value: Float) {

        volumeFieldValue.update { it.copy(value = value) }
        settingsUiState = settingsUiState.copy(isSaveEnabled = true, isShowAlarmIn = false)


    }


    private fun setHaptics(value: Boolean) {

        hapticsFieldValue.update { it.copy(value = value) }

        settingsUiState = settingsUiState.copy(isSaveEnabled = true, isShowAlarmIn = false)

    }

    fun setRingtone(value: Ringtone) {

        _selectedRingtone.value = value
        settingsUiState = settingsUiState.copy(isSaveEnabled = true, isShowAlarmIn = false)
    }


    fun onNavigateBackToSettingsScreen() {

        ringtoneFetcher.stopPlay()
        ringtoneFetcher.release()
    }

    fun onDeleteAlarmText() {

        nameFieldValue.update { it.copy(value = "") }
        // _uiState.update { it.copy(isDialogSaveButtonEnabled = false) }

    }


    private fun resetState() {

        settingsUiState = SettingsUiState()
        // _uiState.value = AlarmUiState()
    }

    override fun onCleared() {
        super.onCleared()
        ringtoneFetcher.release()
        resetState()
    }


    fun onSaveButtonClick() {


        val isSave = settingsUiState.isSaveEnabled
        if (isSave) {

            doSave()
            settingsUiState = settingsUiState.copy(isSaveEnabled = false, isShowAlarmIn = true)
        }

    }

    private fun doSave() {

        Timber.i("DoSave...\nhourField:${hourFieldValue.value.value}\nhourUi:${settingsUiState.hour}")

        val alarmItem = AlarmItem(
                id = this.alarmItem?.id ?: UUID.randomUUID()
                        .toString(),
                name = nameFieldValue.value.value,
                isEnabled = hapticsFieldValue.value.value,
                triggerTime = setTriggerTime(
                        hour = hourFieldValue.value.value.toInt(),
                        minute = minuteFieldValue.value.value.toInt()
                ),
                durationToNextTrigger = 0L,
                daysActive = settingsUiState.daysActive,
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

                    Timber.d("Save Error: ${result.exception.message}")
                }

                is Resource.Success -> {


                    Timber.d("Save Success")
                    // _uiState.update { it.copy(isSaveEnabled = false) }
                }
            }

        }

    }


}



