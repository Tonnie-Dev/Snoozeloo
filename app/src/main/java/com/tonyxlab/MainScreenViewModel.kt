package com.tonyxlab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor() : ViewModel() {

    private val _launchApp = MutableStateFlow(false)
    val launchApp = _launchApp.asStateFlow()

    init {

        viewModelScope.launch {

            delay(3000L)
            _launchApp.value = true
        }
    }
}
