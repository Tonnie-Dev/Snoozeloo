package com.tonyxlab.presentation.settings

import android.net.Uri
import androidx.compose.material3.TextField
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyxlab.domain.model.Ringtone
import com.tonyxlab.domain.ringtone.RingtoneFetcher
import com.tonyxlab.domain.usecases.GetAlarmByIdUseCase
import com.tonyxlab.utils.Resource
import com.tonyxlab.utils.TextFieldValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val ringtoneFetcher: RingtoneFetcher
) : ViewModel() {

    var hourField = MutableStateFlow(
            TextFieldValue(
                    value = "",
                    onValueChange = this::setHourField,
                    range = 0..23
            )

    )
        private set
    var minuteField = MutableStateFlow(
            TextFieldValue(
                    value = "",
                    onValueChange = this::setMinuteField,
                    range = 0..59
            )

    )
        private set
    private val _alarmState = MutableStateFlow(AlarmItemState())
    private val alarmState = _alarmState.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _ringtones = MutableStateFlow<List<Ringtone>>(emptyList())
    val ringtones = _ringtones.asStateFlow()

    private val _selectedRingtone = MutableStateFlow<Ringtone?>(null)
    private val selectedRingtone = _selectedRingtone.asStateFlow()


    private fun readAlarmInfo(alarmId: String?) {


        alarmId?.let {

            viewModelScope.launch {


                when (val result = getAlarmByIdUseCase(alarmId = alarmId)) {
                    is Resource.Success -> {}
                    is Resource.Error -> Unit

                }
            }

        } ?: run {


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
        hourField.update {
            it.copy(value = value, isError = isFieldError(value, hourField.value))
        }

    }

    private fun setMinuteField(value: String) {
        minuteField.update {
            it.copy(value = value, isError = isFieldError(value, minuteField.value))
        }

    }
    private fun isFieldError(newInput: String, field: TextFieldValue<String>): Boolean {

        return newInput.toIntOrNull()
                ?.let { int -> int !in field.range } ?: true

    }

    override fun onCleared() {
        super.onCleared()
        ringtoneFetcher.release()
    }

    init {

        ringtoneFetcher.fetchRingtone()
                .onEach {

                    _ringtones.value = it
                }
                .launchIn(viewModelScope)
    }
}



