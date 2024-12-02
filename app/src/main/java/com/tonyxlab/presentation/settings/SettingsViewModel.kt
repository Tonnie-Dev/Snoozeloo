package com.tonyxlab.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyxlab.domain.model.Ringtone
import com.tonyxlab.domain.ringtone.RingtoneFetcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val ringtoneFetcher: RingtoneFetcher
) : ViewModel() {


    private val _ringtones = MutableStateFlow<List<Ringtone>>(emptyList())
    val ringtones = _ringtones.asStateFlow()

    init {

        ringtoneFetcher.fetchRingtone()
                .onEach {

                    _ringtones.value = it
                }
                .launchIn(viewModelScope)
    }
}



