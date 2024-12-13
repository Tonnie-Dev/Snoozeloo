package com.tonyxlab.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.usecases.GetAlarmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val getAlarmsUseCase: GetAlarmsUseCase) : ViewModel() {

    private val _alarms = MutableStateFlow<List<AlarmItem>> (emptyList())
    val alarms = _alarms.asStateFlow()

    init {
        getAlarmsUseCase().onEach {


            _alarms.value = it
        }.launchIn(viewModelScope)
    }
}
