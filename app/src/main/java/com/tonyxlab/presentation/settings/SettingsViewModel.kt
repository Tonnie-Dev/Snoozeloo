package com.tonyxlab.presentation.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyxlab.domain.usecases.PickRingtoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(

    private val ringtoneUseCase: PickRingtoneUseCase
) : ViewModel() {
    val selectedRingtone: StateFlow<Uri?> = ringtoneUseCase.ringtoneFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
    )

    fun selectRingtone() {
        viewModelScope.launch {
            ringtoneUseCase.selectAndSaveRingtone()
        }
    }
}



