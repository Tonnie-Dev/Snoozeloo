package com.tonyxlab.presentation.settings

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyxlab.domain.model.Ringtone
import com.tonyxlab.domain.model.SILENT_RINGTONE
import com.tonyxlab.domain.ringtone.RingtoneFetcher
import com.tonyxlab.domain.usecases.GetAlarmByIdUseCase
import com.tonyxlab.utils.Resource
import com.tonyxlab.utils.TextFieldValue
import com.tonyxlab.utils.getHourString
import com.tonyxlab.utils.getMinuteString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val ringtoneFetcher: RingtoneFetcher,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _alarmUiState = MutableStateFlow(AlarmUiState())
    private val alarmUiState = _alarmUiState.asStateFlow()

    var hourFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = _alarmUiState.value.triggerTime.getHourString(),
                    onValueChange = this::setHourField,
                    range = 0..23
            )

    )
        private set
    var minuteFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = _alarmUiState.value.triggerTime.getMinuteString(),
                    onValueChange = this::setMinuteField,
                    range = 0..59
            )

    )
        private set

    var nameFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = _alarmUiState.value.name,
                    onValueChange = this::setAlarmName,
                    isConfirmButtonEnabled = _alarmUiState.value.isDialogSaveButtonEnabled
            )
    )
        private set
    var volumeFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = _alarmUiState.value.volume,
                    onValueChange = this::setVolume,
                    isConfirmButtonEnabled = _alarmUiState.value.isDialogSaveButtonEnabled
            )
    )
        private set


    var hapticsFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = _alarmUiState.value.isHapticsOn,
                    onValueChange = this::setHaptics,
                    isConfirmButtonEnabled = _alarmUiState.value.isDialogSaveButtonEnabled
            )
    )
        private set

    var ringtoneFieldValue = MutableStateFlow(
            TextFieldValue(
                    value = _alarmUiState.value.ringtone,
                    onValueChange = this::setRingtone,

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
                    _alarmUiState.value = result.data.toAlarmUiState()

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

    }


    private fun setMinuteField(value: String) {

        if (value.length <= 2) {
            minuteFieldValue.update {
                it.copy(value = value, isError = isFieldError(value, minuteFieldValue.value))
            }
        }

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

    }

    private fun setVolume(value: Float) {

        volumeFieldValue.update { it.copy(value = value) }

    }


    private fun setHaptics(value: Boolean) {

        hapticsFieldValue.update { it.copy(value = value) }

    }

    fun setRingtone(value: Ringtone) {
        Timber.d("ViewModel:setRingtone() called with: $value")
        _selectedRingtone.value = value
        ringtoneFieldValue.update { it.copy(value = value) }
        Timber.d("ViewModel: ringtone updated with: ${ringtoneFieldValue.value.value}")
    }

    private fun isFieldError(newInput: String, field: TextFieldValue<String>): Boolean {

        return newInput.toIntOrNull()
                ?.let { int -> int !in field.range } ?: true

    }

    fun onNavigateBackToSettingsScreen() {

        ringtoneFetcher.stopPlay()
        ringtoneFetcher.release()
    }

    fun onDeleteAlarmText() {

        nameFieldValue.update { it.copy(value = "") }
        _alarmUiState.update { it.copy(isDialogSaveButtonEnabled = false) }

    }

    override fun onCleared() {
        super.onCleared()
        ringtoneFetcher.release()
        Timber.d("ViewModel CLEARED!!")
    }

    init {
        Timber.d("ViewModel: init block - R: ${ringtoneFieldValue.value.value}")
        Timber.d("ViewModel: init block - H: ${hapticsFieldValue.value.value}")
        Timber.d("ViewModel: init block - N: ${nameFieldValue.value.value}")
        readAlarmInfo(savedStateHandle.get<String>("id"))
        ringtoneFetcher.fetchRingtone()
                .onEach {

                    _ringtones.value = it
                }
                .launchIn(viewModelScope)
    }
}



