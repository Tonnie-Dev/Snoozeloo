package com.tonyxlab.presentation.settings

import android.net.Uri
import androidx.compose.runtime.MutableState
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

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _ringtones = MutableStateFlow<List<Ringtone>>(emptyList())
    val ringtones = _ringtones.asStateFlow()

    init {

        ringtoneFetcher.fetchRingtone()
                .onEach {

                    _ringtones.value = it
                }
                .launchIn(viewModelScope)
    }

    fun play(uri: Uri) {

        ringtoneFetcher.startPlay(uri)
        _isPlaying.value = true
    }

    fun stop() {

        ringtoneFetcher.stopPlay()
        _isPlaying.value = false
    }
}



