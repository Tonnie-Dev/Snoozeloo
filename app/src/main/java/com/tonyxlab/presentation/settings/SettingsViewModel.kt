package com.tonyxlab.presentation.settings

import android.net.Uri
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
import com.tonyxlab.domain.usecases.UpdateAlarmUseCase
import com.tonyxlab.presentation.navigation.SettingsScreenObject
import com.tonyxlab.utils.Resource
import com.tonyxlab.utils.TextFieldValue
import com.tonyxlab.utils.getHourString
import com.tonyxlab.utils.getMinuteString
import com.tonyxlab.utils.setTriggerTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var alarmItem: AlarmItem? = null
    private val _uiState = MutableStateFlow(AlarmUiState())
    val uiState = _uiState.asStateFlow()

    var hourFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = _uiState.value.triggerTime.getHourString(),
                    onValueChange = this::setHourField,
                    range = 0..23
            )

    )
        private set
    var minuteFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = _uiState.value.triggerTime.getMinuteString(),
                    onValueChange = this::setMinuteField,
                    range = 0..59
            )

    )
        private set


    var nameFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = _uiState.value.name,
                    onValueChange = this::setAlarmName,
                    isConfirmButtonEnabled = _uiState.value.isDialogSaveButtonEnabled
            )
    )
        private set
    var volumeFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = _uiState.value.volume,
                    onValueChange = this::setVolume,
                    isConfirmButtonEnabled = _uiState.value.isDialogSaveButtonEnabled
            )
    )
        private set


    var hapticsFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = _uiState.value.isHapticsOn,
                    onValueChange = this::setHaptics,
                    isConfirmButtonEnabled = _uiState.value.isDialogSaveButtonEnabled
            )
    )
        private set


    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _ringtones = MutableStateFlow<List<Ringtone>>(emptyList())
    val ringtones = _ringtones.asStateFlow()


    private val _selectedRingtone = MutableStateFlow(SILENT_RINGTONE)
    val selectedRingtone = _selectedRingtone.asStateFlow()


    private fun readAlarmInfo(alarmId: String?) {

        alarmId ?: return


        viewModelScope.launch {


            when (val result = getAlarmByIdUseCase(alarmId = alarmId)) {
                is Resource.Success -> {
                    alarmItem = result.data
                    _uiState.value = result.data.toAlarmUiState()

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
                it.copy(value = value, isError = isFieldError(value, hourFieldValue.value))
            }
        }
        _uiState.update { it.copy(showAlarmIn = true, isSaveEnabled = true) }
    }


    private fun setMinuteField(value: String) {

        if (value.length <= 2) {
            minuteFieldValue.update {
                it.copy(value = value, isError = isFieldError(value, minuteFieldValue.value))
            }
        }

        _uiState.update { it.copy(showAlarmIn = true, isSaveEnabled = true) }
    }

    private fun isFieldError(newInput: String, field: TextFieldValue<String>): Boolean {

        val isError = newInput.toIntOrNull()
                ?.let { int -> int !in field.range } ?: true



        if (isError) {

            _uiState.update { it.copy(isSaveEnabled = false) }
        }

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
        _uiState.update { it.copy(isSaveEnabled = true) }
    }

    private fun setVolume(value: Float) {

        volumeFieldValue.update { it.copy(value = value) }

        _uiState.update { it.copy(isSaveEnabled = true) }

    }


    private fun setHaptics(value: Boolean) {

        hapticsFieldValue.update { it.copy(value = value) }

        _uiState.update { it.copy(isSaveEnabled = true) }

    }

    fun setRingtone(value: Ringtone) {

        _selectedRingtone.value = value
        _uiState.update { it.copy(isSaveEnabled = true) }
    }


    fun onNavigateBackToSettingsScreen() {

        ringtoneFetcher.stopPlay()
        ringtoneFetcher.release()
    }

    fun onDeleteAlarmText() {

        nameFieldValue.update { it.copy(value = "") }
        _uiState.update { it.copy(isDialogSaveButtonEnabled = false) }

    }


    private fun resetState() {

        _uiState.value = AlarmUiState()
    }

    override fun onCleared() {
        super.onCleared()
        ringtoneFetcher.release()
        resetState()
    }


    fun onSaveButtonClick() {

        val isSave = _uiState.value.isSaveEnabled
        if (isSave){

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
                        hour = hourFieldValue.value.value.toInt(),
                        minute = minuteFieldValue.value.value.toInt()
                ),
                durationToNextTrigger = _uiState.value.durationToNextTrigger,
                daysActive = _uiState.value.daysActive,
                ringtone = _selectedRingtone.value,
                volume = volumeFieldValue.value.value,
                isHapticsOn = hapticsFieldValue.value.value,
                wakeUpTime = null
        )

        viewModelScope.launch {

          val result =  if (this@SettingsViewModel.alarmItem != null) {
                updateAlarmUseCase(alarmItem = alarmItem)
            } else {
                createAlarmUseCase(alarmItem = alarmItem)
            }

            when(result){

                is Resource.Error -> {

                    Timber.d("Save Error: ${result.exception.message}")
                }
                is Resource.Success -> {


                    Timber.d("Save Success")
                    _uiState.update { it.copy(isSaveEnabled = false) }
                }
            }

        }

    }

    init {
        val id = savedStateHandle.toRoute<SettingsScreenObject>().id

        Timber.i("Init Block Id is: $id")
        readAlarmInfo(id)
        ringtoneFetcher.fetchRingtone()
                .onEach {

                    _ringtones.value = it
                }
                .launchIn(viewModelScope)
    }
}



